package com.flux.launcher.util.basic;

import androidx.databinding.BindingAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class DataBindingUtil {

    @BindingAdapter("set_icon")
    public static void setImageIcon(android.widget.ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }

}
