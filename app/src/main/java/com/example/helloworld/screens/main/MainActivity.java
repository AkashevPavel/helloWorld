package com.example.helloworld;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton flButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = findViewById(R.id.tabs);
        final ViewPager viewPager = findViewById(R.id.viewpager);
        flButton = findViewById(R.id.fl_button);

        viewPager.setAdapter(new BudgetPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText(R.string.expenses);
        tabLayout.getTabAt(1).setText(R.string.income);

        flButton.setOnClickListener(v -> {
            final int activeFragmentIndex = viewPager.getCurrentItem();
            Fragment activeFragment = getSupportFragmentManager().getFragments().get(activeFragmentIndex);
            Intent intent = new Intent (MainActivity.this, AddItemActivity.class);
            activeFragment.startActivityForResult(intent, BudgetFragment.REQUEST_CODE);
        });


    }


    static class BudgetPagerAdapter extends FragmentPagerAdapter {

        public BudgetPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            if(position < 2) return BudgetFragment.newInstance(position);
            return null;

        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}