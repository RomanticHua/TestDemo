package com.example.a10616.testdemo.video;

import android.content.Context;
import android.util.AttributeSet;

import com.example.a10616.testdemo.R;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

public class CustomVideoPlayer extends StandardGSYVideoPlayer {
    public CustomVideoPlayer(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public CustomVideoPlayer(Context context) {
        super(context);
    }

    public CustomVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutId() {
        return R.layout.video_layout_custom_my;
    }
}
