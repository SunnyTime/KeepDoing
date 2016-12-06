package com.team7619.keepdoing;

import android.content.Context;
import android.support.v4.app.Fragment;

import org.androidannotations.annotations.EFragment;

/**
 * Created by dushiguang on 16/12/6.
 */
@EFragment
public class BaseFragment extends Fragment {
    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
