package com.team7619.keepdoing;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.team7619.keepdoing.widget.CircularProgress;
import com.team7619.keepdoing.widget.CustomerProgressDialog;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;

/**
 * Created by dushiguang on 16/12/6.
 */
@EFragment
public class BaseFragment extends Fragment {
    private CustomerProgressDialog dialog;
    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @UiThread
    public void showprogress() {
        if(getActivity().isFinishing()) {
            return;
        }
        if(null == dialog) {
            dialog = new CustomerProgressDialog(getContext());
            dialog.show();
        }
    }

    @UiThread
    public void closeProgress() {
        if(null != dialog) {
            dialog.closeProgress();
            dialog.dismiss();
        }
        dialog = null;
    }

}
