package com.example.afinal;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

public class Postedtask extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
ViewPagerAdapter viewPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postedtask);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(this);
        tabLayout.addOnTabSelectedListener(new TabLaout.OnTabSelectedListner{


            @Override
            public void onTabSelected; (TabLayout.Tab tab){
                viewPager2.setCurrentItem(tab.getPosition());
        }
            @Override
            public void onTabUnSelected (TabLayout.Tab tab){
        }
            @Override
            public void onTabReSelected (TabLayout.Tab tab){
        }
        });
viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
    @Override
    public void onPageScrolled(int position) {
        super.onPageSelected(position);
        tabLayout.getTabAt(position).select();
    }

    });
}
}