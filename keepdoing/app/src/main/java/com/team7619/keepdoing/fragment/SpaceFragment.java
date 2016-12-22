package com.team7619.keepdoing.fragment;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.team7619.keepdoing.BaseFragment;
import com.team7619.keepdoing.MainActivity;
import com.team7619.keepdoing.R;
import com.team7619.keepdoing.activity.SpaceDetailsActivity;
import com.team7619.keepdoing.adapter.LocalImgAdapter;
import com.team7619.keepdoing.entity.Space_Info;
import com.team7619.keepdoing.myviews.CircleImage.RoundImageView;
import com.team7619.keepdoing.myviews.SwipeRefreshRecyclerView.ItemDivider;
import com.team7619.keepdoing.myviews.SwipeRefreshRecyclerView.SwipeRefreshRecyclerView;
import com.team7619.keepdoing.widget.banner.LMBanners;
import com.team7619.keepdoing.widget.banner.ParallaxTransformer;
import com.team7619.keepdoing.widget.banner.utils.ScreenUtils;

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
public class SpaceFragment extends BaseFragment {
    @ViewById(R.id.space_listview)
    SwipeRefreshRecyclerView theListView;

    @ViewById(R.id.banner)
    LMBanners mbanners;

    private int recordOffset = 0;   //数据偏移量
    private int mTotalNum;    //显示条目的总数量
    private ArrayList<Space_Info> mSpaceBean = new ArrayList<Space_Info>();
    //本地图片
    private ArrayList<Integer> localImages = new ArrayList<Integer>();
    //网络图片
    private List<String> networkImages = new ArrayList<>();

    private ListAdapater mAdapter;
    private RecyclerView mRecyclerView;

    private int[] mImagesSrc = {
            R.mipmap.img1,
            R.mipmap.img2,
            R.mipmap.img3,
            R.mipmap.img4,
            R.mipmap.img5
    };

    @AfterViews
    void afterViews() {
        initRecyclerView();
        initBanner();
        getListData(recordOffset);
        mRecyclerView = theListView.getRecyclerView();
        mRecyclerView.addItemDecoration(new ItemDivider(getContext(), LinearLayoutManager.HORIZONTAL, 1, getResources().getColor(R.color.mainbg)));
    }

    private void initBanner() {
        addLocalImg();
        mbanners.setLayoutParams(new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ScreenUtils.dip2px(getContext(), 200)));
        //本地用法
        mbanners.setAdapter(new LocalImgAdapter(getContext()),localImages);
        //网络图片
//        mLBanners.setAdapter(new UrlImgAdapter(MainActivity.this), networkImages);
        //参数设置
        mbanners.setAutoPlay(true);//自动播放
        mbanners.setVertical(false);//是否可以垂直
        mbanners.setScrollDurtion(222);//两页切换时间
        mbanners.setCanLoop(true);//循环播放
        mbanners.setSelectIndicatorRes(R.drawable.page_indicator_select);//选中的原点
        mbanners.setUnSelectUnIndicatorRes(R.drawable.page_indicator_unselect);//未选中的原点
        mbanners.setIndicatorWidth(5);//默认为5dp
//        mLBanners.setHoriZontalTransitionEffect(TransitionEffect.Default);//选中喜欢的样式
        mbanners.setHoriZontalCustomTransformer(new ParallaxTransformer(R.id.id_image));//自定义样式
        mbanners.setDurtion(2000);//切换时间
        mbanners.hideIndicatorLayout();//隐藏原点
        mbanners.showIndicatorLayout();//显示原点
        mbanners.setIndicatorPosition(LMBanners.IndicaTorPosition.BOTTOM_MID);//设置原点显示位置
    }

    private void addLocalImg() {
        localImages.add(R.mipmap.img1);
        localImages.add(R.mipmap.img2);
        localImages.add(R.mipmap.img3);
        localImages.add(R.mipmap.img4);
        localImages.add(R.mipmap.img5);
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
        //HouseBaoBeiEntity entity = App.getHaofangtuoApi().getHaiWaiHouseBaoBei(activity.customerId, recordOffset, 10);
        //数据刷新要放在UI线程!
        showprogress();
        final ArrayList<Space_Info> items = new ArrayList<Space_Info>();
        BmobQuery<Space_Info> query = new BmobQuery<Space_Info>();
        query.order("updatedAt");
        query.findObjects(new FindListener<Space_Info>(){
            @Override
            public void done(List<Space_Info> list, BmobException e) {
                if(null == e) {
                    for(Space_Info listInfo : list) {
                        items.add(listInfo);
                    }
                    updateDaoDianDateListData(items);
                } else {
                    Log.e("dushiguang","e-----" + e);
                }
                closeProgress();
            }
        });

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

    @Override
    public void onPause() {
        super.onPause();
        mbanners.stopImageTimerTask();
    }

    @Override
    public void onResume() {
        super.onResume();
        mbanners.startImageTimerTask();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mbanners.clearImageTimerTask();
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
                holder.articleTitle.setText(item.getInfo_title());
                holder.publishTime.setText(item.getUpdatedAt());
                holder.articleAbout.setText(item.getInfo_about());

                holder.itemLly.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        SpaceDetailsActivity.jumpToSpaceDetails(getContext(), item.getObjectId());
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
        TextView publishTime;
        TextView articleTitle;
        TextView articleAbout;
        LinearLayout itemLly;
        ItemClickListener listener;

        public ListItemViewHolder(View itemView, ItemClickListener listener) {
            super(itemView);
            titleIcon = (RoundImageView) itemView.findViewById(R.id.title_icon);
            articleTitle = (TextView) itemView.findViewById(R.id.article_title_tv);
            publishTime = (TextView) itemView.findViewById(R.id.publish_time);
            articleAbout = (TextView) itemView.findViewById(R.id.article_about_tv);
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
