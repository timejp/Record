package com.team3.fastcampus.record.InDiary;

/**
 * Created by yoonjoonghyun on 2017. 3. 25..
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;
import com.team3.fastcampus.record.Diary.Model.Diary;
import com.team3.fastcampus.record.R;

import java.util.List;

/**
 * InDiary를 보여주기 위한 메인뷰
 */
public class InDiaryViewFragment extends Fragment implements InDiaryListViewFragment.InDiaryListCallback {

    private View view;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    // Fragment
    private static InDiaryListViewFragment inDiaryListViewFragment;
    private static InDiaryMapViewFragment inDiaryMapViewFragment;

    // Adaper
    private InDiaryViewPagerAdapter inDiaryViewPagerAdapter;

    private Diary diary;

    public InDiaryViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view != null) {
            return view;
        }

        view = inflater.inflate(R.layout.fragment_in_diary_view, container, false);

        initView();

        initAdapter();

        initListener();

        initFragment();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initValue();
    }

    private void initValue() {
        inDiaryListViewFragment.init();
        inDiaryMapViewFragment.init();
    }

    private void initView() {
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
    }

    private void initAdapter() {
        inDiaryViewPagerAdapter = new InDiaryViewPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(inDiaryViewPagerAdapter);
    }

    private void initListener() {
        // 1. Pager가 변경 되었을 때 Tab를 바꿔주는 리스너
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        // 2. Tab이 변경 되었을 때 페이지를 바꿔주는 리스너
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    }

    private void initFragment() {
        inDiaryListViewFragment = new InDiaryListViewFragment(this);
        inDiaryMapViewFragment = new InDiaryMapViewFragment();
    }

    public void setDiary(Diary diary) {
        this.diary = diary;
    }

    @Override
    public Diary getDiary() {
        return diary;
    }

    @Override
    public void setLocations(List<LatLng> locations) {
        inDiaryMapViewFragment.set(locations);
    }

    @Override
    public void pinPosition(int position) {
        inDiaryMapViewFragment.pin(position);
    }

    class InDiaryViewPagerAdapter extends FragmentStatePagerAdapter {

        public static final int TAB_COUNT = 2;

        public InDiaryViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0: // Show List
                    fragment = inDiaryListViewFragment;
                    break;
                case 1: // Show Map
                    fragment = inDiaryMapViewFragment;
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return TAB_COUNT;
        }
    }
}
