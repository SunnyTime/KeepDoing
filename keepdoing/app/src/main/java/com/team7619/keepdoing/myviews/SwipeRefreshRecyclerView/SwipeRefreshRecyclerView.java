package com.team7619.keepdoing.myviews.SwipeRefreshRecyclerView;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.team7619.keepdoing.R;


/**
 * 下拉刷新RecyclerView, 默认线性布局
 *
 * @author GUXIA318
 * @date 2015-10-12 9:48
 */
public class SwipeRefreshRecyclerView extends SwipeRefreshLayout {

    private Context mContext;
    private RecyclerView mRecyclerView;
    private AdapterWrapper mInnerAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshListener mSwipeRefreshListener;

    private FrameLayout mContentLayout;

    /**是否有底部加载更多*/
    private boolean hasFooter = true;

    /**是否加载更多中*/
    private boolean isLoadingMore = false;
    private int mLastVisibleItem = 0;
    private View mEmptyView;
    private boolean mIsRefreshing = false;

    public void setOnEmptyClickListener(final OnEmptyClickListener onEmptyClickListener) {
        if (mEmptyView != null) {
            mEmptyView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onEmptyClickListener != null) {
                        onEmptyClickListener.onEmptyClicked();
                    }
                }
            });
        }
    }

    public interface OnEmptyClickListener {
        void onEmptyClicked();
    }

    public interface SwipeRefreshListener extends OnRefreshListener {
        void onLoadMore();
    }

    public SwipeRefreshRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public SwipeRefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        this.setColorSchemeResources(android.R.color.holo_green_dark, android.R.color.holo_blue_dark, android.R.color.holo_orange_dark);

        //创建recyclerView和emptyview的容器
        mContentLayout = new FrameLayout(context);
        addView(mContentLayout, new SwipeRefreshLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        mEmptyView = OnCreateEmptyView();
        if (null != mEmptyView) {
            mEmptyView.setVisibility(GONE);
            mContentLayout.addView(mEmptyView, new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }

        mRecyclerView = new RecyclerView(context);
        // 默认RecyclerView为线性
        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // 设置Item增加、移除默认动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnScrollListener(mScrollListener);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setOnTouchListener(
                new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (mIsRefreshing) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
        );

        mContentLayout.addView(mRecyclerView, new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    protected View OnCreateEmptyView() {
        return LayoutInflater.from(mContext).inflate(R.layout.view_have_no_data_empty, null);
    }

    /**
     * 展示空白页
     *
     * @param isShowEmptyView
     */
    public void showEmptyView(boolean isShowEmptyView) {
        if (null != mEmptyView) {
            mEmptyView.setVisibility(isShowEmptyView ? View.VISIBLE : View.GONE);
        }
        mRecyclerView.setVisibility(isShowEmptyView ? View.GONE : View.VISIBLE);
    }

    /**
     * 设置是否可下拉刷新
     *
     * @param refreshable
     */
    public void setRefreshable(boolean refreshable) {
        this.setEnabled(refreshable);
    }

    /**
     * 设置底部是否有加载更多
     *
     * @param isLoadMore
     */
    public void setIsLoadMore(boolean isLoadMore) {
        this.hasFooter = isLoadMore;
    }

    /**
     * 设置下拉刷新、上拉加载更多监听器
     *
     * @param listener
     */
    public void setSwipeRefreshListener(SwipeRefreshListener listener) {
        this.mSwipeRefreshListener = listener;
        this.setOnRefreshListener(listener);
    }

    /**
     * 设置RecyclerView的布局管理器，不设置默认为线性
     *
     * @param manager
     */
    public void setRecyclerLayoutManager(RecyclerView.LayoutManager manager) {
        this.mLayoutManager = manager;
        mRecyclerView.setLayoutManager(manager);
    }

    /**
     * 设置RecyclerView的适配器
     *
     * @param adapter
     */
    public void setAdapter(RecyclerView.Adapter adapter) {
        mInnerAdapter = new AdapterWrapper(mContext, adapter);
        mRecyclerView.setAdapter(mInnerAdapter);
    }

    /**
     * 设置RecyclerView添加、删除Item的动画
     *
     * @param animator
     */
    public void setItemAnimator(RecyclerView.ItemAnimator animator) {
        mRecyclerView.setItemAnimator(animator);
    }

    public void onCompleted() {
        setRefreshing(false);
        isLoadingMore = false;
        mInnerAdapter.notifyDataSetChanged();
        mIsRefreshing = false;//解除禁止滑动
    }

    public void setIsLoadingMore(boolean isLoadingMore) {
        this.isLoadingMore = isLoadingMore;
    }

    public boolean isLoadingMore() {
        return isLoadingMore;
    }

    public void notifyDataSetChanged() {
        mInnerAdapter.notifyDataSetChanged();
    }

    //删除条目
    public void notifyItemRemoved(int position) {
        if (mInnerAdapter.getItemViewType(position) != AdapterWrapper.TYPE_FOOTER) {
            mInnerAdapter.notifyItemRemoved(position);
        }
    }

    //移动条目
    public void notifyItemMoveToTop(int position) {
        if (mInnerAdapter.getItemViewType(position) != AdapterWrapper.TYPE_FOOTER) {
            mInnerAdapter.notifyItemMoved(position, 0);
        }
    }

    private RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE && !isRefreshing() && !isLoadingMore
                    && hasFooter && mLastVisibleItem + 1 == mInnerAdapter.getItemCount()) {
                isLoadingMore = true;
                if (mSwipeRefreshListener != null) {
                    mSwipeRefreshListener.onLoadMore();
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int firstVisibleItemPosition = 0;
            if (mLayoutManager instanceof LinearLayoutManager) {
                mLastVisibleItem = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
                firstVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findFirstCompletelyVisibleItemPosition();
            } else if (mLayoutManager instanceof GridLayoutManager) {
                mLastVisibleItem = ((GridLayoutManager) mLayoutManager).findLastVisibleItemPosition();
                firstVisibleItemPosition = ((GridLayoutManager) mLayoutManager).findFirstCompletelyVisibleItemPosition();
            } else if (mLayoutManager instanceof StaggeredGridLayoutManager) {
                //因为StaggeredGridLayoutManager的特殊性可能导致最后显示的item存在多个，所以这里取到的是一个数组
                //得到这个数组后再取到数组中position值最大的那个就是最后显示的position值了
                int[] lastPositions = new int[((StaggeredGridLayoutManager) mLayoutManager).getSpanCount()];
                ((StaggeredGridLayoutManager) mLayoutManager).findLastVisibleItemPositions(lastPositions);
                mLastVisibleItem = findMax(lastPositions);
                firstVisibleItemPosition = ((StaggeredGridLayoutManager) mLayoutManager).findFirstVisibleItemPositions(lastPositions)[0];
            }

            // 解决RecyclerView和SwipeRefreshLayout共存的bug
            setEnabled(firstVisibleItemPosition == 0);
        }
    };

    private int findMax(int[] positions) {
        int max = positions[0];
        for (int value : positions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    private class AdapterWrapper extends RecyclerView.Adapter {

        private static final int TYPE_ITEM = 0;
        private static final int TYPE_FOOTER = 110;
        private RecyclerView.Adapter mAdapter;
        private Context mContext;

        public AdapterWrapper(Context context, RecyclerView.Adapter wrappedAdapter) {
            this.mContext = context;
            this.mAdapter = wrappedAdapter;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder holder = null;
            switch (viewType) {

                case TYPE_FOOTER:
                    View footer = LayoutInflater.from(mContext).inflate(R.layout.view_footer, null);
                    footer.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                    holder = new FooterViewHolder(footer);
                    break;
                default:
                    holder = mAdapter.onCreateViewHolder(parent, viewType);
                    break;
            }
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (!hasFooter || position + 1 != getItemCount()) {
                mAdapter.onBindViewHolder(holder, position);
            }
        }

        @Override
        public int getItemCount() {
            if (mAdapter != null) {
                return hasFooter ? mAdapter.getItemCount() + 1 : mAdapter.getItemCount();
            } else
                return 0;
        }

        @Override
        public int getItemViewType(int position) {
            if (hasFooter && position + 1 == getItemCount()) {
                return TYPE_FOOTER;
            } else {
                return mAdapter.getItemViewType(position);
            }
        }

        private class FooterViewHolder extends RecyclerView.ViewHolder {

            public FooterViewHolder(View itemView) {
                super(itemView);
            }
        }

    }

    //局部刷新item
    public void notifyItemChanged(int position) {
        mInnerAdapter.notifyItemChanged(position);
    }

    public void scrollToPosition(int position) {
        mRecyclerView.scrollToPosition(position);
    }

    public boolean ismIsRefreshing() {
        return mIsRefreshing;
    }

    public void setmIsRefreshing(boolean mIsRefreshing) {
        this.mIsRefreshing = mIsRefreshing;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }
}
