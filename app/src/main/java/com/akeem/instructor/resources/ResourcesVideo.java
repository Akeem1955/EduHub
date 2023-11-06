package com.akeem.instructor.resources;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.akeem.eduhub.R;
import com.akeem.eduhub.databinding.ActivityResourcesVideoBinding;

public class ResourcesVideo extends AppCompatActivity {
    private ExoPlayer player;
    private VideoViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources_video);
        ActivityResourcesVideoBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_resources_video);
        model = new ViewModelProvider(this).get(VideoViewModel.class);

        View view = getWindow().getDecorView();
        int decor = View.SYSTEM_UI_FLAG_FULLSCREEN;
        view.setSystemUiVisibility(decor);
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_FULLSCREEN);
        player = new ExoPlayer.Builder(this).build();
        PlayerView playerView = binding.playerControlView;
        playerView.setOnClickListener(v -> view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_FULLSCREEN));
        playerView.setPlayer(player);
        //playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
        // player.setVideoScalingMode(C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);

        Uri video = getIntent().getData();
        // Build the media item.
        MediaItem mediaItem = MediaItem.fromUri(video);
// Set the media item to be played.
        player.setMediaItem(mediaItem);
        player.seekTo(model.getCurrent_duration());
// Prepare the player.
        player.prepare();
    }

    @Override
    protected void onPause() {
        super.onPause();
        player.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        model.setCurrent_duration(player.getCurrentPosition());
    }
}