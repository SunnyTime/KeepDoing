package com.team7619.keepdoing.fragment;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.team7619.keepdoing.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

/**
 * Created by ex-dushiguang201 on 2016-06-12.
 */
@EFragment(R.layout.fragment_write)
public class WriteFragment extends Fragment {

    @AfterViews
    void afterViews() {
        Log.e("dushiguag","-------");
    }
}
