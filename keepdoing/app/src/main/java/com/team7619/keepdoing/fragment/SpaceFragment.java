package com.team7619.keepdoing.fragment;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.team7619.keepdoing.R;
import com.team7619.keepdoing.activity.SpaceDetails;
import com.team7619.keepdoing.adapter.FoldingCellListAdapter;
import com.team7619.keepdoing.entity.Item;
import com.team7619.keepdoing.entity.SpaceBean;
import com.team7619.keepdoing.myviews.CircleImage.RoundImageView;
import com.team7619.keepdoing.myviews.SwipeRefreshRecyclerView.ItemDivider;
import com.team7619.keepdoing.myviews.SwipeRefreshRecyclerView.SwipeRefreshRecyclerView;
import com.team7619.keepdoing.myviews.foldingcell.FoldingCell;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

/**
 * Created by ex-dushiguang201 on 2016-06-12.
 */
@EFragment(R.layout.fragment_space)
public class SpaceFragment extends Fragment {
    @ViewById(R.id.space_listview)
    SwipeRefreshRecyclerView theListView;

    private int recordOffset = 0;   //数据偏移量
    private int mTotalNum;    //显示条目的总数量
    private ArrayList<SpaceBean> mSpaceBean = new ArrayList<SpaceBean>();  //到店日期数据源
    private ListAdapater mAdapter;
    private RecyclerView mRecyclerView;

    @AfterViews
    void afterViews() {
        initRecyclerView();
        getListData(recordOffset);
        mRecyclerView = theListView.getRecyclerView();
        mRecyclerView.addItemDecoration(new ItemDivider(getContext(), LinearLayoutManager.HORIZONTAL, 1, getResources().getColor(R.color.mainbg)));
    }

    public void initRecyclerView() {
        theListView.setRefreshable(true);
        theListView.setIsLoadMore(true);
        //theListView.setProgressViewOffset(false, 0, UIUtil.dip2px(getActivity(), 50));
        theListView.setRefreshing(true);
        theListView.setSwipeRefreshListener(new SwipeRefreshRecyclerView.SwipeRefreshListener() {
            @Override
            public void onRefresh() {
                mSpaceBean.clear();
                recordOffset = 0;
                getListData(recordOffset);
            }

            @Override
            public void onLoadMore() {
                recordOffset = mSpaceBean.size();
                if (mTotalNum > recordOffset) {
                    getListData(recordOffset);
                } else {
                    resetSwipeRefreshRecyclerView();
                    Log.v("dushiguang",",没有数据了");
                }
            }
        });
    }

    /**
     * 获取数据
     * @param recordOffset
     */
    @Background
    public void getListData(int recordOffset) {
        //closeLoadingProgress();         //不用base中的
        //showLoadingDialog();
        //HouseBaoBeiEntity entity = app.getHaofangtuoApi().getHaiWaiHouseBaoBei(activity.customerId, recordOffset, 10);
        //数据刷新要放在UI线程!

        ArrayList<SpaceBean> items = SpaceBean.getTestingList();
        //updateDaoDianDateListData(entity);
        updateDaoDianDateListData(items);
    }

    @UiThread
    void updateDaoDianDateListData (ArrayList<SpaceBean> items) {

        if (recordOffset == 0) {
            theListView.scrollToPosition(0); //列表显示置顶
            theListView.setmIsRefreshing(true); //禁止滑动事件等新数据绑完在放开滑动事件
            mSpaceBean.clear();
        }
        mSpaceBean.addAll(items);
        hiddLoadingDialog();
        refreshListView();
    }

    @UiThread
    void hiddLoadingDialog() {
        theListView.setRefreshing(false);
    }

    @UiThread
    void refreshListView() {
        if (null == mSpaceBean || 0 == mSpaceBean.size()) {
            theListView.setVisibility(View.GONE);
            //mErrorPage.setVisibility(View.VISIBLE);
        } else {
            //mErrorPage.setVisibility(View.GONE);
            theListView.setVisibility(View.VISIBLE);
            if (mAdapter == null) {
                mAdapter = new ListAdapater(getActivity());
                //mAdapter.setOnItemClickListener(this);
            } else {
                mAdapter.notifyDataSetChanged();
            }
            theListView.setAdapter(mAdapter);
            resetSwipeRefreshRecyclerView();
        }
    }

    @UiThread
    void resetSwipeRefreshRecyclerView() {
        if (mTotalNum <= mSpaceBean.size()) {
            theListView.setIsLoadMore(false);
        } else if (mTotalNum > mSpaceBean.size()) {
            theListView.setIsLoadMore(true);
        } else if (recordOffset == 0) {
            theListView.setIsLoadMore(true);
        }
        theListView.onCompleted();
    }

    public class ListAdapater extends RecyclerView.Adapter<ListItemViewHolder> {
        private Context context;
        private ItemClickListener listener;

        public ListAdapater(Context context) {
            this.context = context;
        }

        @Override
        public ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(context).inflate(R.layout.cell_title_layout, parent, false);
            return new ListItemViewHolder(v, listener);
        }

        @Override
        public void onBindViewHolder(ListItemViewHolder holder, final int position) {
            final SpaceBean item = mSpaceBean.get(position);
            if (null != item) {
                holder.name.setText(item.getName());
                holder.publishTime.setText(item.getPublishTime());
                holder.articleTitle.setText(item.getArticleTitle());
                holder.articleAbout.setText(item.getArticleAbout());

                holder.itemLly.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        SpaceDetails.jumpToSpaceDetails(getContext());
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return mSpaceBean.size();
        }
    }


    public class ListItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        RoundImageView titleIcon;
        TextView name;
        TextView publishTime;
        TextView articleTitle;
        TextView articleAbout;
        TextView articleTitleCon;
        TextView joinNum;
        TextView articleContent;
        LinearLayout itemLly;
        ItemClickListener listener;

        public ListItemViewHolder(View itemView, ItemClickListener listener) {
            super(itemView);
            titleIcon = (RoundImageView) itemView.findViewById(R.id.title_icon);
            name = (TextView) itemView.findViewById(R.id.name);
            publishTime = (TextView) itemView.findViewById(R.id.publish_time);
            articleTitle = (TextView) itemView.findViewById(R.id.article_title);
            articleAbout = (TextView) itemView.findViewById(R.id.article_about);
            /*articleTitleCon = (TextView) itemView.findViewById(R.id.article_title_con);
            joinNum = (TextView) itemView.findViewById(R.id.join_num);
            articleContent = (TextView) itemView.findViewById(R.id.article_content);*/
            itemLly= (LinearLayout)itemView.findViewById(R.id.item_lly);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(listener != null) {
                listener.onItemClickListener(itemView, getAdapterPosition());
            }
        }
    }
}
