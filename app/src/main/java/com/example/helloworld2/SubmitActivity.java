package com.example.helloworld2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class SubmitActivity extends AppCompatActivity {
    private String nameAge;
    private String occupation;
    private String description;

    private FragmentManager manager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Update String Instance Variables
        nameAge = getIntent().getStringExtra(Constants.INPUT_FIRSTNAME) + ", " + getIntent().getStringExtra(Constants.AGE);
        occupation = getIntent().getStringExtra(Constants.INPUT_OCCUPATION);
        description = getIntent().getStringExtra(Constants.INPUT_DESCRIPTION);

        //Layout
        setContentView(R.layout.activity_submit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        manager = getSupportFragmentManager();
    }
    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {
        //Create new profileFragment
        ProfileFragment profileFragment= new ProfileFragment();
        Operation operation = new Operation(nameAge, occupation, description);
        profileFragment.setOperation(operation);

        //Add fragments to adapter
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(profileFragment, getResources().getString(R.string.profile));
        adapter.addFragment(new MatchesFragment(), getResources().getString(R.string.matches));
        adapter.addFragment(new SettingsFragment(), getResources().getString(R.string.settings));
        viewPager.setAdapter(adapter);
    }
    public class Operation {
        public String nameAge;
        public String occupation;
        public String description;

        public Operation(String nameAge, String occupation, String description) {
            this.nameAge = nameAge;
            this.occupation = occupation;
            this.description = description;
        }
    }
    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        public Adapter(FragmentManager manager) {
            super(manager);
        }
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }
        @Override
        public int getCount() {
            return mFragmentList.size();
        }
        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
