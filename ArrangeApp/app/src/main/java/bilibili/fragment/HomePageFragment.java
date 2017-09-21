package bilibili.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.arrange.R;

import java.util.ArrayList;

import bilibili.BiliBiliActivity;
import bilibili.adapter.HomePagerAdapter;
import bilibili.app.GameCentreActivity;
import bilibili.app.OffLineDownloadActivity;
import bilibili.app.TotalStationSearchActivity;
import bilibili.base.RxLazyFragment;
import bilibili.widget.CircleImageView;
import bilibili.widget.materialsearch.MaterialSearchView;
import bilibili.widget.tablayout.SlidingTabLayout;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by kan212 on 17/9/19.
 */

public class HomePageFragment extends RxLazyFragment {

    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.sliding_tabs)
    SlidingTabLayout mSlidingTab;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.search_view)
    MaterialSearchView mSearchView;
    @BindView(R.id.toolbar_user_avatar)
    CircleImageView mCircleImageView;
    
    public static HomePageFragment newInstance() {
        return new HomePageFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home_pager;
    }

    @Override
    public void finishCreateView(Bundle state) {
        setHasOptionsMenu(true);
        initToolBar();
        initSearchView();
        initViewPager();
    }

    private void initToolBar() {
        mToolbar.setTitle("");
        ((BiliBiliActivity) getActivity()).setSupportActionBar(mToolbar);
        mCircleImageView.setImageResource(R.drawable.ic_hotbitmapgg_avatar);
    }

    private void initSearchView() {
        mSearchView.setVoiceSearch(false);
        mSearchView.setCursorDrawable(R.drawable.custom_cursor);
        mSearchView.setEllipsize(true);
        mSearchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
        mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                TotalStationSearchActivity.launch(getActivity(), query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void initViewPager() {
        HomePagerAdapter mHomeAdapter = new HomePagerAdapter(getChildFragmentManager(), getApplicationContext());
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.setAdapter(mHomeAdapter);
        mSlidingTab.setViewPager(mViewPager);
        mViewPager.setCurrentItem(0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.id_action_game:
                //游戏中心
                startActivity(new Intent(getActivity(), GameCentreActivity.class));
                break;
            case R.id.id_action_download:
                //离线缓存
                startActivity(new Intent(getActivity(), OffLineDownloadActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_main, menu);
        // 设置SearchViewItemMenu
        MenuItem item = menu.findItem(R.id.id_action_search);
        mSearchView.setMenuItem(item);
    }

    @OnClick(R.id.navigation_layout)
    void toggleDrawer() {
        Activity activity = getActivity();
        if (activity instanceof BiliBiliActivity) {
            ((BiliBiliActivity) activity).toggleDrawer();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == Activity.RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    mSearchView.setQuery(searchWrd, false);
                }
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public boolean isOpenSearchView() {
        return mSearchView.isSearchOpen();
    }


    public void closeSearchView() {
        mSearchView.closeSearch();
    }
}
