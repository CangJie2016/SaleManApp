package com.li.estatesalemanapp.util;

import android.widget.ImageView;

import com.cangjie.basetool.utils.DebugLog;
import com.li.estatesalemanapp.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class ImageLoaderHelper {
    private static DisplayImageOptions baseDisplayImageOptions;
    private static ImageLoader imageLoader;

    public static void displayRoundAvatar(String url, ImageView imageView, int rpx) {
        DisplayImageOptions options = new DisplayImageOptions.Builder().displayer(new RoundedBitmapDisplayer(rpx)).build();
        getImageLoader().displayImage(url, imageView, options);

    }

    public static void displayImage(String url, ImageView imageView) {
    	getImageLoader().displayImage(url, imageView, getBaseDisplayImageOptions());
    }
    
    public static void displayImage(String url, ImageView imageView,ImageLoadingListener listener) {
    	getImageLoader().displayImage(url, imageView, getBaseDisplayImageOptions(),listener);
    }
    
    public static void displayImage(ImageLoader loader, String url, ImageView imageView, ImageLoadingListener listener) {
    	loader.displayImage(url, imageView, getBaseDisplayImageOptions(),listener);
    }

    public static DisplayImageOptions getBaseDisplayImageOptions() {
        if (baseDisplayImageOptions == null) {
            baseDisplayImageOptions = new DisplayImageOptions.Builder()
            						.cacheInMemory(true)
            						.cacheOnDisk(true)
            						.displayer(new SimpleBitmapDisplayer())
            						.showImageOnLoading(R.mipmap.ic_launcher)
            						.showImageForEmptyUri(R.mipmap.ic_launcher)
            						.showImageOnFail(R.mipmap.ic_launcher)
            						.build();
        }
        return baseDisplayImageOptions;
    }


    public static ImageLoader getImageLoader() {
        if (imageLoader == null) {
            imageLoader = ImageLoader.getInstance();
        }
        return imageLoader;
    }

    public static String translateLoaclImageDirFormat(String path) {
        String s = "file://" + path;
        DebugLog.i(s);
        return s;
    }

    public static String translateResouceFormat(int resouceId) {
        String s = "drawable://" + resouceId;

        return s;
    }
}
