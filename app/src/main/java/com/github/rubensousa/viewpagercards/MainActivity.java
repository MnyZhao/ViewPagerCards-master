package com.github.rubensousa.viewpagercards;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import static com.github.rubensousa.viewpagercards.R.id.viewPager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener {

    private Button mButton;
    private ViewPager mViewPager;

    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    private CardFragmentPagerAdapter mFragmentCardAdapter;
    private ShadowTransformer mFragmentCardShadowTransformer;

    private boolean mShowingFragments = false;
   /* private me.relex.circleindicator.CircleIndicator indicator;*/
    private Indicator indicators;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (ViewPager) findViewById(viewPager);
       /* indicator= (me.relex.circleindicator.CircleIndicator) findViewById(R.id.indictor);*/
        indicators= (Indicator) findViewById(R.id.indictor);
        mButton = (Button) findViewById(R.id.cardTypeBtn);
        ((CheckBox) findViewById(R.id.checkBox)).setOnCheckedChangeListener(this);
        mButton.setOnClickListener(this);
        //设置ViewPager的监听器
        mViewPager.setOnPageChangeListener(new MyPagerListner());
        mCardAdapter = new CardPagerAdapter();
        mCardAdapter.addCardItem(new CardItem(R.string.title_1, R.string.text_1));
        mCardAdapter.addCardItem(new CardItem(R.string.title_2, R.string.text_1));
        mCardAdapter.addCardItem(new CardItem(R.string.title_3, R.string.text_1));
        mCardAdapter.addCardItem(new CardItem(R.string.title_4, R.string.text_1));
        mFragmentCardAdapter = new CardFragmentPagerAdapter(getSupportFragmentManager(),
                dpToPixels(2, this));

        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
        mFragmentCardShadowTransformer = new ShadowTransformer(mViewPager, mFragmentCardAdapter);

        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);
        /*indicator.setViewPager(mViewPager);
        mCardAdapter.registerDataSetObserver(indicator.getDataSetObserver());*/

    }

    @Override
    public void onClick(View view) {
        if (!mShowingFragments) {
            mButton.setText("Views");
            mViewPager.setAdapter(mFragmentCardAdapter);
            mViewPager.setPageTransformer(false, mFragmentCardShadowTransformer);
        } else {
            mButton.setText("Fragments");
            mViewPager.setAdapter(mCardAdapter);
            mViewPager.setPageTransformer(false, mCardShadowTransformer);
        }

        mShowingFragments = !mShowingFragments;
    }

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        mCardShadowTransformer.enableScaling(b);
        mFragmentCardShadowTransformer.enableScaling(b);
    }
    //创建ViewPager的监听事件
    class MyPagerListner implements ViewPager.OnPageChangeListener{
        //在ViewPager滑动时回调
        //参数1:item的位置  参数2：偏移的百分比，这个百分比用于接近于1  参数3：偏移量
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            indicators.setoffset(position,positionOffset);
        }
        //在ViewPager选中时回调
        @Override
        public void onPageSelected(int position) {
        }
        //在ViewPager滑动状态改变时回调
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
