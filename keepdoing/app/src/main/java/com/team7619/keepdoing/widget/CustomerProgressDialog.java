package com.team7619.keepdoing.widget;

import android.app.Dialog;
import android.content.Context;

/**
 * Created by dushiguang on 16/12/7.
 */
public class CustomerProgressDialog extends Dialog {
    public CustomerProgressDialog(Context context) {
        super(context);
    }

    public CustomerProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CustomerProgressDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
