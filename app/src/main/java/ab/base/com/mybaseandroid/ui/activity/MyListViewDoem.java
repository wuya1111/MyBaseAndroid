package ab.base.com.mybaseandroid.ui.activity;

import android.base.com.mybaseandroid.R;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.simple.common.recyclerview.FamiliarRecyclerView;
import com.simple.common.recyclerview.FamiliarRecyclerViewOnScrollListener;

import java.util.ArrayList;
import java.util.List;

import ab.base.com.mybaseandroid.ui.base.BaseActivity;
import ab.base.com.mybaseandroid.ui.view.ZpHradTitleBar;

/**
 * Created by Administrator on 2016/1/21 0021.
 */
public class MyListViewDoem extends BaseActivity{

    private FamiliarRecyclerView mRecyclerView;
    private List<String> mDatas;
    private boolean isLoadingMore = false;
//    private RecyclerAdapter<String> adapter;

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    private View mFooterLoadMoreView;
    private ProgressBar mPbLoadMoreProgressBar;
    private TextView mTvLoadMoreText;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private MyAdapters adapter;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.my_listview_doem);
        mRecyclerView = getViewById(R.id.mRecyclerView);


        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.mSwipeRefreshLayout);
        mFooterLoadMoreView = View.inflate(this, R.layout.footer_view_load_more, null);
        mFooterLoadMoreView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mPbLoadMoreProgressBar = (ProgressBar) mFooterLoadMoreView.findViewById(R.id.pb_progressBar);
        mTvLoadMoreText = (TextView)mFooterLoadMoreView.findViewById(R.id.tv_text);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void initData() {
        mDatas = new ArrayList<>();
        mDatas.addAll(getDatas());

        mRecyclerView.addFooterView(mFooterLoadMoreView);





//        class MyViewHolder extends RecyclerView.ViewHolder {
//            TextView mTvTxt;
//            View itemView;
//
//            public MyViewHolder(View itemView) {
//                super(itemView);
//                this.itemView=itemView;
//                mTvTxt = (TextView)itemView.findViewById(R.id.tv_txt);
//            }
//
//            public <T extends View>T getView(int viewID){
//                return (T)itemView;
//            }
////        public <T extends View>T getView(int viewID){
////            View item = mViews.get(viewID);
////            if(item==null){
////                item=mConvertView.findViewById(viewID);
////            }
////            return (T)item;
////        }
//        }


        adapter = new MyAdapters();


//        adapter = new RecyclerAdapter<String>(R.layout.item_view_linear,mDatas){
//            @Override
//            protected void onBindData(RecyclerViewHolder viewHolder, int position, String item) {
//                viewHolder.setText(R.id.tv_txt,item);
////                ImageView imageview=viewHolder.findViewById(R.id.iamgeview);
//            }
//
//            @Override
//            protected void setupItemClickListener(RecyclerViewHolder viewHolder, int position) {
//                super.setupItemClickListener(viewHolder, position);
//            }
//
//            @Override
//            public int getItemViewType(int position) {
//                return super.getItemViewType(position);
//            }
//
//            @Override
//            protected int getItemLayout(int type) {
//                return super.getItemLayout(type);
//            }
//
//        };


        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnScrollListener(new FamiliarRecyclerViewOnScrollListener(mRecyclerView.getLayoutManager()) {
            @Override
            public void onScrolledToTop() {
            }
            @Override
            public void onScrolledToBottom() {
                if (isLoadingMore) {
                    return;
                }
                isLoadingMore = true;
                mPbLoadMoreProgressBar.setVisibility(View.VISIBLE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int startPos = mDatas.size() - 1;
                        List<String> newDatas = getDatas();
                        mDatas.addAll(newDatas);

                        adapter.notifyItemRangeChanged(startPos, mDatas.size());
//                        mRecyclerView.setAdapter(adapter);
//                        adapter.notifyDataSetChanged();
                        mPbLoadMoreProgressBar.setVisibility(View.GONE);

                        mTvLoadMoreText.setText("松开加载更多");
                        if (mDatas.size() >= 80) {
                            mRecyclerView.removeFooterView(mFooterLoadMoreView);
                        }
                        isLoadingMore = false;

                    }
                }, 1000);

            }
        });




        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDatas.clear();
                        mDatas.addAll(getDatas());
                        adapter.notifyDataSetChanged();
                        mRecyclerView.addFooterView(mFooterLoadMoreView);
                        mPbLoadMoreProgressBar.setVisibility(View.GONE);
                        mTvLoadMoreText.setText("松开加载更多");
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);

            }
        });
    }
    class MyAdapters extends RecyclerView.Adapter<MyViewHolder>{
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(MyListViewDoem.this).inflate(R.layout.item_view_linear, parent, false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            TextView mTvTxt= holder.getView(R.id.tv_txt);
//                mTvTxt.setText(mDatas.get(position));
//
////            holder.mTvTxt.setText(mDatas.get(position));
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }
//            @Override
//            public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                return new MyViewHolder(LayoutInflater.from(MyListViewDoem.this).inflate(R.layout.item_view_linear, parent, false));
//            }

//            @Override
//            public void onBindViewHolder(MyViewHolder holder, int position) {
//                TextView mTvTxt= holder.getView(R.id.tv_txt);
//                mTvTxt.setText(mDatas.get(position));
//
////            holder.mTvTxt.setText(mDatas.get(position));
//            }

    }
    @Override
    protected void titlebBar() {
        mTitlebar=getViewById(R.id.titlebar);
        mTitlebar.setDelegate(new ZpHradTitleBar.BGATitlebarDelegate() {
            @Override
            public void onClickLeftCtv() {
                super.onClickLeftCtv();
                finish();
            }
        });
    }


    private List<String> getDatas() {
        List<String> tempDatas = new ArrayList<>();
        int curMaxData =  mDatas.size();
        for (int i = 0; i < 10; i++) {
            tempDatas.add("item:" + (curMaxData + i));
        }

        return tempDatas;
    }
}