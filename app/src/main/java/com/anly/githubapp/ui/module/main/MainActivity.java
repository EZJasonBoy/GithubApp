package com.anly.githubapp.ui.module.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.anly.githubapp.GithubApplication;
import com.anly.githubapp.R;
import com.anly.githubapp.common.config.MainMenuConfig;
import com.anly.githubapp.di.HasComponent;
import com.anly.githubapp.di.component.DaggerMainComponent;
import com.anly.githubapp.di.component.MainComponent;
import com.anly.githubapp.di.module.ActivityModule;
import com.anly.githubapp.di.module.RepoModule;
import com.anly.githubapp.ui.base.BaseActivity;
import com.anly.githubapp.ui.module.main.adapter.MainFragmentPagerAdapter;
import com.gigamole.navigationtabbar.ntb.NavigationTabBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements HasComponent<MainComponent>{

    private MainFragmentPagerAdapter mAdapter;

    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.nav_bar)
    NavigationTabBar mNavBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initViews();
        loadData();
    }

    private void initViews() {
        mAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);

        mNavBar.setModels(MainMenuConfig.getNavModels());
        mNavBar.setViewPager(mViewPager);
    }

    private void loadData() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new TrendingFragment());
        fragments.add(new MostStarFragment());
        fragments.add(new SearchFragment());

        mAdapter.setList(fragments);
    }

    @Override
    public MainComponent getComponent() {
        return DaggerMainComponent.builder()
                .applicationComponent(GithubApplication.get(this).getComponent())
                .activityModule(new ActivityModule(this))
                .repoModule(new RepoModule())
                .build();
    }
}
