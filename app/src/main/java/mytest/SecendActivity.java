package mytest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.rubensousa.viewpagercards.CardFragmentPagerAdapter;
import com.github.rubensousa.viewpagercards.Indicator;
import com.github.rubensousa.viewpagercards.MainActivity;
import com.github.rubensousa.viewpagercards.R;
import com.github.rubensousa.viewpagercards.ShadowTransformer;

import static com.github.rubensousa.viewpagercards.MainActivity.dpToPixels;
import static com.github.rubensousa.viewpagercards.R.id.viewPager;

public class SecendActivity extends AppCompatActivity {
    private ViewPager mViewPager;

    private CardFragmentPagerAdapter mFragmentCardAdapter;
    private ShadowTransformer mFragmentCardShadowTransformer;

    /* private me.relex.circleindicator.CircleIndicator indicator;*/
    private Indicator indicators;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secend);
        mViewPager = (ViewPager) findViewById(viewPager);
       /* indicator= (me.relex.circleindicator.CircleIndicator) findViewById(R.id.indictor);*/
        indicators = (Indicator) findViewById(R.id.indictor);
        indicators.setNumber(5);
        //设置ViewPager的监听器
        mViewPager.setOnPageChangeListener(new MyPagerListner());
        mFragmentCardAdapter = new CardFragmentPagerAdapter(getSupportFragmentManager(),
                dpToPixels(2, this));

        mFragmentCardShadowTransformer = new ShadowTransformer(mViewPager, mFragmentCardAdapter);
        mViewPager.setAdapter(mFragmentCardAdapter);
        mViewPager.setPageTransformer(false, mFragmentCardShadowTransformer);
        findViewById(R.id.click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click();
            }
        });
    }

    //创建ViewPager的监听事件
    class MyPagerListner implements ViewPager.OnPageChangeListener {
        //在ViewPager滑动时回调
        //参数1:item的位置  参数2：偏移的百分比，这个百分比用于接近于1  参数3：偏移量
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            indicators.setoffset(position, positionOffset);
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

    public void click() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
