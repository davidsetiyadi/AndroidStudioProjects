package com.neostra.presentation;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;


public class GlideUtils {

    private static RequestOptions options;

    //默认加载
    public static void loadImageView(Context mContext, String path, ImageView mImageView) {

        Glide.with(mContext).load(path).into(mImageView);
    }


    //填充
    public static void loadImageCrop(Context mContext, String path, ImageView mImageView) {

        options = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

        Glide.with(mContext).load(path).apply(options).into(mImageView);


    }

    //清理磁盘缓存
    public static void GuideClearDiskCache(Context mContext) {
        //理磁盘缓存 需要在子线程中执行
        Glide.get(mContext).clearDiskCache();
    }

    //清理内存缓存
    public static void GuideClearMemory(Context mContext) {
        //清理内存缓存  可以在UI主线程中进行
        Glide.get(mContext).clearMemory();
    }
}
