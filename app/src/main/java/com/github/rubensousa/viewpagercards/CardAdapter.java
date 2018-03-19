package com.github.rubensousa.viewpagercards;


import android.support.v7.widget.CardView;

public interface CardAdapter {
    /*控制间隔*/
    int MAX_ELEVATION_FACTOR = 8;

    float getBaseElevation();

    CardView getCardViewAt(int position);

    int getCount();
}
