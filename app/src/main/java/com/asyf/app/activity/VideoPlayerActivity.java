package com.asyf.app.activity;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.asyf.app.R;
import com.asyf.app.common.Logger;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

public class VideoPlayerActivity extends AppCompatActivity {
    public static final String url = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        JZVideoPlayerStandard jzVideoPlayerStandard = findViewById(R.id.videoplayer);
        //url = "http://192.168.0.130:8080/userfiles/0a99aaaa14f04258b2d9224169e79729/files/book/2018/04/EB0D01505EFEDC6797C416686A8CA7C0.flv";
        jzVideoPlayerStandard.setUp(url, JZVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "test");
        //String imageUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523507435903&di=6976d9937a2e31b6278a4723feb53db5&imgtype=0&src=http%3A%2F%2Fe.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F9f510fb30f2442a7cd0600dbdd43ad4bd1130247.jpg";
        //jzVideoPlayerStandard.thumbImageView.setImageURI(Uri.parse(imageUrl));
        //播放视频
        jzVideoPlayerStandard.startVideo();
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        Logger.e("sss", "清空视频缓存");
        JZVideoPlayer.clearSavedProgress(this, url);
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }
}
