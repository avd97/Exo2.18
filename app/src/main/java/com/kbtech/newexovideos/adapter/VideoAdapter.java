package com.kbtech.newexovideos.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.media2.exoplayer.external.upstream.DefaultDataSourceFactory;
import androidx.media2.exoplayer.external.upstream.DefaultHttpDataSourceFactory;
import androidx.media2.exoplayer.external.upstream.FileDataSourceFactory;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSource;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import com.kbtech.newexovideos.R;

import java.util.ArrayList;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoHolder> {

    Context context;
    List<String> mediaUri = new ArrayList<>();

    private int currentPlayingPosition = -1; // to keep track of the currently playing video position
    private static SimpleCache simpleCache;


    @SuppressLint("NotifyDataSetChanged")
    public VideoAdapter(Context context, List<String> mediaUri) {
        this.context = context;
        this.mediaUri = mediaUri;

//        Cache cache = new SimpleCache(context.getCacheDir(), new LeastRecentlyUsedCacheEvictor(50 * 1024 * 1024));
//        simpleCache = new SimpleCache(context.getCacheDir(), new LeastRecentlyUsedCacheEvictor(50 * 1024 * 1024));
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_video_view, parent, false);
        return new VideoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoHolder holder, int position) {
        String videoUrl = mediaUri.get(position);
        holder.bind(context, videoUrl);

        // Autoplay the video when it comes into view
        if (position == currentPlayingPosition) {
            holder.startPlayer();
        } else {
            holder.stopPlayer();
        }
    }

    public void setCurrentPlayingPosition(int currentPlayingPosition) {
        this.currentPlayingPosition = currentPlayingPosition;
    }

    @Override
    public int getItemCount() {
        return mediaUri.size();
    }

    public static class VideoHolder extends RecyclerView.ViewHolder {
        ExoPlayer player;
        StyledPlayerView styledPlayerView;

        public VideoHolder(@NonNull View iv) {
            super(iv);

            styledPlayerView = iv.findViewById(R.id.player_view);
        }

        public void bind(Context context, String videoUrl) {
            player = new ExoPlayer.Builder(context).build();
            styledPlayerView.setPlayer(player);

            MediaItem mediaItem = MediaItem.fromUri(Uri.parse(videoUrl));

            DataSource.Factory dataSourceFactory = new DefaultDataSource.Factory(context);
//            cacheDataSourceFactory = new CacheDataSourceFactory(
//                    VideoAdapter.simpleCache,
//                    dataSourceFactory,
//                    new FileDataSourceFactory(),
//                    new CacheDataSinkFactory(VideoAdapter.simpleCache, 5 * 1024 * 1024),
//                    CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR);

            player.setMediaItem(mediaItem);
            player.setMediaSource(new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(mediaItem));

            player.addListener(new Player.Listener() {
                @Override
                public void onEvents(Player player, Player.Events events) {
                    Player.Listener.super.onEvents(player, events);
                }

                @Override
                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                    Player.Listener.super.onPlayerStateChanged(playWhenReady, playbackState);
                    if (playbackState == Player.STATE_READY && playWhenReady) {
                        // Autoplay started
                    }
                }
            });

            player.setPlayWhenReady(true);

            player.prepare();

            player.setRepeatMode(Player.REPEAT_MODE_OFF);
        }

        public void startPlayer() {
            if (player != null) {
                player.setPlayWhenReady(true);
            }
        }

        public void stopPlayer() {
            if (player != null) {
                player.setPlayWhenReady(false);
            }
        }
    }
}
