package com.kbtech.newexovideos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.kbtech.newexovideos.databinding.ActivityMainBinding;
import com.kbtech.newexovideos.fragments.VideoListFragment;
import com.kbtech.newexovideos.fragments.VideoUriFragment;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
//                .add(R.id.frame_layout, new VideoUriFragment(), null)
                .add(R.id.frame_layout, new VideoListFragment(), null)
                .commit();
    }
}