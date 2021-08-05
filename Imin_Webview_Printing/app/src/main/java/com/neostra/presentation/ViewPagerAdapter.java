package com.neostra.presentation;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * @author xghsir
 * Created by 肖根华 on 2019/8/14.
 */

public class ViewPagerAdapter extends PagerAdapter {

    private ArrayList<String> mData;
    private Context mContext;

    public ViewPagerAdapter(Context context, ArrayList<String> data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        View view = View.inflate(container.getContext(), R.layout.wallpaper_view_pager_item, null);
        ImageView imageView = view.findViewById(R.id.iv_icon);
        GlideUtils.loadImageView(mContext, mData.get(position), imageView);
        container.addView(view);//添加到父控件
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}

