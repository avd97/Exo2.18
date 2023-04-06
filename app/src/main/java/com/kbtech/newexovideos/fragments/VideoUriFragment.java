package com.kbtech.newexovideos.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.kbtech.newexovideos.R;
import com.kbtech.newexovideos.databinding.FragmentVideoUriBinding;


public class VideoUriFragment extends Fragment {

    FragmentVideoUriBinding mBinding;

    ExoPlayer player;

    public VideoUriFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_video_uri, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    public void initializePlayer() {

        try {

            player = new ExoPlayer.Builder(requireContext()).build();
            mBinding.playerView.setPlayer(player);

            MediaItem mediaItem1 = MediaItem.fromUri("https://s3.ap-south-1.amazonaws.com/knackbedevbucket/ORI_778dd18ec26b569d.mp4");
            MediaItem mediaItem2 = MediaItem.fromUri("https://s3.ap-south-1.amazonaws.com/knackbedevbucket/ORI_48b5e95ab2b5352e.mp4");
//        MediaItem mediaItem = MediaItem.fromUri("https://twitter.com/i/status/1643873905113722880");

            player.setMediaItem(mediaItem1);
//        player.addMediaItem(mediaItem1);
//        player.addMediaItem(mediaItem2);
            player.prepare();
//        Toast.makeText(requireContext(), "HAS PREV ? "+player.hasPreviousMediaItem(), Toast.LENGTH_SHORT).show();
//        Toast.makeText(requireContext(), "HAS NEXT ? "+player.hasNextMediaItem(), Toast.LENGTH_SHORT).show();
            player.setRepeatMode(Player.REPEAT_MODE_OFF);
            player.play();

//            if (!player.isPlaying()) {
//                player.release();
//            }
        } catch (IllegalStateException dilSe) {
            Log.e("Video Fragment", "Illegal State: " + dilSe.getMessage());
        }
        catch (Exception e) {
            Log.e("Video Fragment", "Exception: " + e.getMessage());
        }
    }


    @Override
    public void onStart() {
        super.onStart();

        initializePlayer();
    }


    @Override
    public void onPause() {
        super.onPause();

        if (player == null) {
//            player.pause();
            initializePlayer();
        }
    }
}