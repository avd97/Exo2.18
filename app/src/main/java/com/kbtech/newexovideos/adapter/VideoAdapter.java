package com.kbtech.newexovideos.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

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
//        currentPlayingPosition = holder.getAbsoluteAdapterPosition();
        currentPlayingPosition = holder.getLayoutPosition();
        Log.i("Video_Adapter", "onBindViewHolder adapterPos: "+currentPlayingPosition);



        // Autoplay the video when it comes into view

//        holder.styledPlayerView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//            @Override
//            public boolean onPreDraw() {
//
//                Rect rect = new Rect();
//                Log.i("Video_Adapter", "onBindViewHolder rect: "+rect.top);
//                Log.i("Video_Adapter", "onBindViewHolder rect: "+rect.bottom);
//                holder.styledPlayerView.getLocalVisibleRect(rect);
//                boolean isVisible = rect.bottom > 0 && rect.top < holder.styledPlayerView.getHeight();
//
//                // Play or pause the player accordingly
//                if (isVisible && !holder.isPlaying) {
//                    holder.startPlayer();
//                } else if (!isVisible && holder.isPlaying) {
//                    holder.stopPlayer();
//                }
//
//                holder.styledPlayerView.getViewTreeObserver().removeOnPreDrawListener(this);
//
//                return true;
//            }
//        });

//        if (position == currentPlayingPosition) {
//            holder.bind(context, videoUrl);
//            holder.startPlayer();
//        } else {
//            holder.stopPlayer();
//        }
    }

    public void setVideoPause(int lastplayPosition, boolean isplay) {
//        initializePlayer();

//        for (int i = 0; i < mPostList.size(); i++) {
//            if (lastplayPosition == i) {
//                if (mPostList.get(lastplayPosition).getPostItems().size() > 0) {
//                    if (mPostList.get(lastplayPosition).getPostItems().get(0).getMediaType().equalsIgnoreCase("video")) {
//                        if (isplay) {
//                            player.setPlayWhenReady(true);
//                        } else {
//                            player.setPlayWhenReady(false);
////                            notifyItemChanged(lastplayPosition);
//                        }
//                    }
//                }
//            }
//        }
    }

//    private void initializePlayer() {
//            player = ExoPlayerFactory.newSimpleInstance(mContext);
//        }
//    }

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

        boolean isPlaying = false;

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

            player.setPlayWhenReady(false);

            player.prepare();

            player.setRepeatMode(Player.REPEAT_MODE_OFF);
        }

        public void startPlayer() {
            if (player != null) {
                player.setPlayWhenReady(true);
                isPlaying = true;
            }
        }

        public void stopPlayer() {
            if (player != null) {
                player.pause();
                isPlaying = false;
            }
        }
    }
}
