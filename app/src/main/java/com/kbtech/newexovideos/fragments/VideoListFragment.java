package com.kbtech.newexovideos.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.kbtech.newexovideos.R;
import com.kbtech.newexovideos.adapter.VideoAdapter;
import com.kbtech.newexovideos.databinding.FragmentVideoListBinding;

import java.util.ArrayList;
import java.util.List;


public class VideoListFragment extends Fragment {

    FragmentVideoListBinding mBinding;

    List<String> mediaUri = new ArrayList<>();

    VideoAdapter videoAdapter;
    LinearLayoutManager mLayoutManager;

    int firstItemPos = 0;
    int lastItemPos = -1;

    public VideoListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_video_list, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mediaUri.add("https://s3.ap-south-1.amazonaws.com/knackbedevbucket/ORI_778dd18ec26b569d.mp4");
        mediaUri.add("https://s3.ap-south-1.amazonaws.com/knackbedevbucket/ORI_48b5e95ab2b5352e.mp4");
        mediaUri.add("https://s3.ap-south-1.amazonaws.com/knackbedevbucket/ORI_ad4efa84fd0906db.mp4");
        mediaUri.add("https://s3.ap-south-1.amazonaws.com/knackbedevbucket/ORI_241bf8771b605619.mp4");
        mediaUri.add("https://s3.ap-south-1.amazonaws.com/knackbedevbucket/ORI_b55d27da0575b0f5.mp4");
        mediaUri.add("https://s3.ap-south-1.amazonaws.com/knackbedevbucket/ORI_f2a09820b7d53ca2.mp4");

        mLayoutManager = new LinearLayoutManager(requireContext());

        mBinding.recView.setHasFixedSize(true);
        mBinding.recView.setLayoutManager(mLayoutManager);
        videoAdapter = new VideoAdapter(requireContext(), mediaUri);
        mBinding.recView.setAdapter(videoAdapter);

        mBinding.recView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        Log.i("VDO_LIST", "onScrollStateChanged SCROLL_STATE_IDLE: "+newState);
                        int playPosition = mLayoutManager.findFirstVisibleItemPosition();
                        if (playPosition == -1) {//no visible item
                            return;
                        }

                        int firstCompletelyVisibleItemPosition = mLayoutManager.findFirstCompletelyVisibleItemPosition();
                        int lastCompletelyVisibleItemPosition  = mLayoutManager.findLastCompletelyVisibleItemPosition();

                        for (int i = firstCompletelyVisibleItemPosition; i <= lastCompletelyVisibleItemPosition; i++) {
                            View viewByPosition = mLayoutManager.findViewByPosition(firstCompletelyVisibleItemPosition);
                            if (viewByPosition != null) {
                                StyledPlayerView videoView = (StyledPlayerView) viewByPosition.findViewById(R.id.player_view);
                                if (videoView != null && videoView.isActivated()) {
                                    // playVideo(false);
                                    videoAdapter.setVideoPause(lastCompletelyVisibleItemPosition,false);
                                    return;//current active player is visible,do nothing
                                }
                            }
                        }
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        Log.i("VDO_LIST", "onScrollStateChanged SCROLL_STATE_DRAGGING: "+newState);
                        break;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });
    }
}