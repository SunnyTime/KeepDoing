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

import com.team7619.keepdoing.Bmob.BmobQueryListener;
import com.team7619.keepdoing.Bmob.BmobUtils;
import com.team7619.keepdoing.R;
import com.team7619.keepdoing.activity.SpaceDetails;
import com.team7619.keepdoing.adapter.FoldingCellListAdapter;
import com.team7619.keepdoing.entity.Item;
import com.team7619.keepdoing.entity.SpaceBean;
import com.team7619.keepdoing.entity.Space_Info;
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
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by ex-dushiguang201 on 2016-06-12.
 */
@EFragment(R.layout.fragment_space)
public class SpaceFragment extends Fragment {
    @ViewById(R.id.space_listview)
    SwipeRefreshRecyclerView theListView;

    private int recordOffset = 0;   //数据偏移量
    private int mTotalNum;    //显示条目的总数量
    private ArrayList<Space_Info> mSpaceBean = new ArrayList<Space_Info>();  //到店日期数据源
    private ListAdapater mAdapter;
    private RecyclerView mRecyclerView;
    private BmobUtils mBmobUtils;

    @AfterViews
    void afterViews() {
        initRecyclerView();
        getListData(recordOffset);
        mBmobUtils = BmobUtils.getBmobUtils();
        mBmobUtils.initBmob(getContext());
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
        //closeLoadingProgress();
        // 不用base中的
        //showLoadingDialog();
        //HouseBaoBeiEntity entity = app.getHaofangtuoApi().getHaiWaiHouseBaoBei(activity.customerId, recordOffset, 10);
        //数据刷新要放在UI线程!
        final ArrayList<Space_Info> items = new ArrayList<Space_Info>();
        BmobQuery<Space_Info> query = new BmobQuery<Space_Info>();
        query.order("publish_time");
        query.findObjects(new FindListener<Space_Info>(){
            @Override
            public void done(List<Space_Info> list, BmobException e) {

                //Log.e("dushiguang","list-----" + list.size());
                if(null == e) {
                    Log.e("dushiguang","list-----" + list.size());
                    for(Space_Info listInfo : list) {
                        items.add(listInfo);
                    }
                    updateDaoDianDateListData(items);
                } else {
                    Log.e("dushiguang","e-----" + e);
                }

            }
        });
        Log.e("dushiguang","items-----" + items);
        //mBmobUtils.QueryBmobDb();
        //updateDaoDianDateListData(entity);

    }

    @UiThread
    void updateDaoDianDateListData (ArrayList<Space_Info> items) {

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
            final Space_Info item = mSpaceBean.get(position);
            if (null != item) {
                holder.name.setText(item.getName());
                holder.publishTime.setText(item.getPublish_time());
                holder.articleTitle.setText(item.getInfo_title());
                holder.articleAbout.setText(item.getInfo_about());

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
