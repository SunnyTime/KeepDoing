package com.team7619.keepdoing.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;

import com.team7619.keepdoing.R;

/**
 * Created by dushiguang on 16/12/7.
 */
public class CustomerProgressDialog extends Dialog {
    public CircularProgress progress;

    public CustomerProgressDialog(Context context) {
        super(context);
        View view  = View.inflate(context, R.layout.view_loading, null);
        progress = (CircularProgress)view.findViewById(R.id.progress_view);
        progress.setIndeterminate(true);
        progress.startAnimation();
        setContentView(view);
        getWindow().getAttributes().gravity = Gravity.CENTER;
    }

    public void closeProgress() {
        if(null != progress) {
            progress.stopAnimation();
        }
        progress = null;
    }
}
