package com.polidea.adapterssample;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import com.polidea.adapterssample.R;
import com.polidea.adapterssample.grid.GridFragment;
import com.polidea.adapterssample.linear.LinearFragment;
import com.polidea.adapterssample.staggered.StaggeredFragment;
import java.util.ArrayList;
import java.util.List;

public class SampleActivity extends AppCompatActivity {

    private TabLayout tabLayout;

    private ViewPager viewPager;

    private TabsAdapter tabsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.linear));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.grid));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.staggered));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tabsAdapter = new TabsAdapter(this);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setAdapter(tabsAdapter);
    }

    public static class TabsAdapter extends FragmentPagerAdapter {

        private final Context context;

        private List<Class> fragmentClassList = new ArrayList<Class>() {{
            add(LinearFragment.class);
            add(GridFragment.class);
            add(StaggeredFragment.class);
        }};

        public TabsAdapter(AppCompatActivity activity) {
            super(activity.getSupportFragmentManager());
            context = activity;
        }

        @Override
        public int getCount() {
            return fragmentClassList.size();
        }

        @Override
        public Fragment getItem(int position) {
            return Fragment.instantiate(context, fragmentClassList.get(position).getName(), null);
        }
    }
}
