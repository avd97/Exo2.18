package com.kbtech.newexovideos.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.kbtech.newexovideos.R;

import java.util.ArrayList;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoHolder> {

    Context context;
    List<String> mediaUri = new ArrayList<>();

    @SuppressLint("NotifyDataSetChanged")
    public VideoAdapter(Context context, List<String> mediaUri) {
        this.context = context;
        this.mediaUri = mediaUri;
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
            player.setMediaItem(mediaItem);

            player.prepare();

            player.setRepeatMode(Player.REPEAT_MODE_OFF);
            player.play();
        }
    }
}
