package com.android.vismay.newsforce.LoginAndSignup;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.vismay.newsforce.R;
import com.android.vismay.newsforce.Utilities.PrefManager;

public class InitialScreens extends AppCompatActivity {

    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private ImageView[] dots;
    private int[] layouts;
    private Button btnSkip;
    private ImageButton btnNext;
    private PrefManager prefManager;
    private ImageView dot1,dot2,dot3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_screens);

        prefManager = new PrefManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        }

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_initial_screens);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        btnSkip = (Button) findViewById(R.id.btn_skip);
        btnNext = (ImageButton) findViewById(R.id.btn_next);
        dot1=findViewById(R.id.first_intro_screen);
        dot2=findViewById(R.id.second_intro_screen);
        dot3=findViewById(R.id.third_intro_screen);

        layouts = new int[]{
                R.layout.fragment_one,
                R.layout.fragment_two,
                R.layout.fragment_three
        };

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchHomeScreen();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page if true launch LoginActivity
                int current = getItem(+1);
                if (current < layouts.length) {
                    if(current==1){
                        dot1.setImageResource(R.drawable.circle_background);
                        dot2.setImageResource(R.drawable.dotselected);
                        dot3.setImageResource(R.drawable.dotselected);
                    }
                    else if(current==2){
                        dot3.setImageResource(R.drawable.dotselected);
                        dot1.setImageResource(R.drawable.dotselected);
                        dot2.setImageResource(R.drawable.circle_background);
                    }
                    else{
                        dot1.setImageResource(R.drawable.dotselected);
                        dot2.setImageResource(R.drawable.dotselected);
                        dot3.setImageResource(R.drawable.circle_background);
                    }

                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else {
                    launchHomeScreen();
                }
            }
        });
    }
    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {

            if(position==0){
                dot1.setImageResource(R.drawable.circle_background);
                dot2.setImageResource(R.drawable.dotselected);
                dot3.setImageResource(R.drawable.dotselected);
            }
            else if(position==1){
                dot3.setImageResource(R.drawable.dotselected);
                dot1.setImageResource(R.drawable.dotselected);
                dot2.setImageResource(R.drawable.circle_background);
            }
            else{
                dot1.setImageResource(R.drawable.dotselected);
                dot2.setImageResource(R.drawable.dotselected);
                dot3.setImageResource(R.drawable.circle_background);
            }
            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.length - 1) {
                // last page. make button text to GOT IT
                btnSkip.setVisibility(View.GONE);
            } else {
                // still pages are left
                btnSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };
    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(InitialScreens.this, LoginActivity.class));
        finish();
    }
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}

