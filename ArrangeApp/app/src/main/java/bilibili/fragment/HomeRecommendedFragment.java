package bilibili.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;

import com.arrange.R;
import com.bilibili.entity.recommend.RecommendBannerInfo;
import com.bilibili.entity.recommend.RecommendInfo;
import com.bilibili.utils.ConstantUtil;
import com.bilibili.utils.SnackbarUtil;

import java.util.ArrayList;
import java.util.List;

import bilibili.base.RxLazyFragment;
import bilibili.network.RetrofitHelper;
import bilibili.section.HomeRecommendActivityCenterSection;
import bilibili.section.HomeRecommendBannerSection;
import bilibili.section.HomeRecommendPicSection;
import bilibili.section.HomeRecommendTopicSection;
import bilibili.section.HomeRecommendedSection;
import bilibili.widget.CustomEmptyView;
import bilibili.widget.banner.BannerEntity;
import bilibili.widget.sectioned.SectionedRecyclerViewAdapter;
import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kan212 on 17/9/19.
 */

public class HomeRecommendedFragment extends RxLazyFragment {

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycle)
    RecyclerView mRecyclerView;
    @BindView(R.id.empty_layout)
    CustomEmptyView mCustomEmptyView;

    private boolean mIsRefreshing = false;
    private SectionedRecyclerViewAdapter mSectionedAdapter;
    private List<BannerEntity> banners = new ArrayList<>();
    private List<RecommendInfo.ResultBean> results = new ArrayList<>();
    private List<RecommendBannerInfo.DataBean> recommendBanners = new ArrayList<>();


    public static HomeRecommendedFragment newInstance() {
        return new HomeRecommendedFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home_recommended;
    }

    @Override
    public void finishCreateView(Bundle state) {
        isPrepared = true;
        lazyLoad();
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        initRefreshLayout();
        initRecyclerView();
        isPrepared = false;
    }

    @Override
    protected void initRecyclerView() {
        mSectionedAdapter = new SectionedRecyclerViewAdapter();
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (mSectionedAdapter.getSectionItemViewType(position)) {
                    case SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER:
                        return 2;
                    case SectionedRecyclerViewAdapter.VIEW_TYPE_FOOTER:
                        return 2;
                    default:
                        return 1;
                }
            }
        });
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mSectionedAdapter);
        setRecycleNoScroll();
    }

    private void setRecycleNoScroll() {
        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mIsRefreshing;
            }
        });
    }

    public void initEmptyView() {
        mSwipeRefreshLayout.setRefreshing(false);
        mCustomEmptyView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        mCustomEmptyView.setEmptyImage(R.drawable.img_tips_error_load_error);
        mCustomEmptyView.setEmptyText("加载失败~(≧▽≦)~啦啦啦.");
        SnackbarUtil.showMessage(mRecyclerView, "数据加载失败,请重新加载或者检查网络是否链接");
    }


    public void hideEmptyView() {
        mCustomEmptyView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }


    @Override
    protected void initRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                mIsRefreshing = true;
                loadData();
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                    clearData();
                    loadData();
            }
        });
    }

    @Override
    protected void loadData() {
        RetrofitHelper.getBiliAppAPI()
                .getRecommendedBannerInfo()
                .compose(this.<RecommendBannerInfo>bindToLifecycle())
                .map(new Function<RecommendBannerInfo, List<RecommendBannerInfo.DataBean> >() {
                    @Override
                    public List<RecommendBannerInfo.DataBean> apply(@NonNull RecommendBannerInfo recommendBannerInfo) throws Exception {
                        return recommendBannerInfo.getData();
                    }
                })
                .flatMap(new Function<List<RecommendBannerInfo.DataBean>, ObservableSource<RecommendInfo>>() {
                    @Override
                    public ObservableSource<RecommendInfo> apply(@NonNull List<RecommendBannerInfo.DataBean> dataBeen) throws Exception {
                        recommendBanners.addAll(dataBeen);
                        return RetrofitHelper.getBiliAppAPI().getRecommendedInfo();
                    }
                })
                .compose(this.<RecommendInfo>bindToLifecycle())
                .map(new Function<RecommendInfo, List<RecommendInfo.ResultBean> >() {
                    @Override
                    public List<RecommendInfo.ResultBean> apply(@NonNull RecommendInfo recommendInfo) throws Exception {
                        return recommendInfo.getResult();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<RecommendInfo.ResultBean>>() {
                    @Override
                    public void accept(List<RecommendInfo.ResultBean> resultBeen) throws Exception {
                        results.addAll(resultBeen);
                        finishTask();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        initEmptyView();
                    }
                });
    }

    @Override
    protected void finishTask() {
        mSwipeRefreshLayout.setRefreshing(false);
        mIsRefreshing = false;
        hideEmptyView();
        convertBanner();
        mSectionedAdapter.addSection(new HomeRecommendBannerSection(banners));
        int size = results.size();
        for (int i = 0; i < size; i++) {
            String type = results.get(i).getType();
            if (!TextUtils.isEmpty(type)) {
                switch (type) {
                    case ConstantUtil.TYPE_TOPIC:
                        //话题
                        mSectionedAdapter.addSection(new HomeRecommendTopicSection(getActivity(),
                                results.get(i).getBody().get(0).getCover(),
                                results.get(i).getBody().get(0).getTitle(),
                                results.get(i).getBody().get(0).getParam()));
                        break;
                    case ConstantUtil.TYPE_ACTIVITY_CENTER:
                        //活动中心
                        mSectionedAdapter.addSection(new HomeRecommendActivityCenterSection(
                                getActivity(),
                                results.get(i).getBody()));
                        break;
                    default:
                        mSectionedAdapter.addSection(new HomeRecommendedSection(
                                getActivity(),
                                results.get(i).getHead().getTitle(),
                                results.get(i).getType(),
                                results.get(1).getHead().getCount(),
                                results.get(i).getBody()));
                        break;
                }
            }
            String style = results.get(i).getHead().getStyle();
            if (style.equals(ConstantUtil.STYLE_PIC)) {
                mSectionedAdapter.addSection(new HomeRecommendPicSection(getActivity(),
                        results.get(i).getBody().get(0).getCover(),
                        results.get(i).getBody().get(0).getParam()));
            }
        }
        mSectionedAdapter.notifyDataSetChanged();
    }

    /**
     * 设置轮播banners
     */
    private void convertBanner() {
        Observable.fromIterable(recommendBanners)
                .compose(this.<RecommendBannerInfo.DataBean>bindToLifecycle())
                .forEach(new Consumer<RecommendBannerInfo.DataBean>() {
                    @Override
                    public void accept(RecommendBannerInfo.DataBean dataBean) throws Exception {
                        banners.add(new BannerEntity(dataBean.getValue(),
                                dataBean.getTitle(), dataBean.getImage()));
                    }
                });
    }

    private void clearData() {
        banners.clear();
        recommendBanners.clear();
        results.clear();
        mIsRefreshing = true;
        mSectionedAdapter.removeAllSections();
    }
}
