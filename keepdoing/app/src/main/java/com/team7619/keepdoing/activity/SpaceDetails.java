package com.team7619.keepdoing.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.team7619.keepdoing.BaseActivity;
import com.team7619.keepdoing.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by ex-dushiguang201 on 2016-06-13.
 */
@EActivity(R.layout.activity_space_details)
public class SpaceDetails extends BaseActivity {
    @ViewById(R.id.title_back_tv)
    TextView btBack;

    @AfterViews
    void afterViews() {

    }

    @Click(R.id.title_back_tv)
    void btBackClick() {
        finish();
    }

    public static void jumpToSpaceDetails(Context context) {
        Intent intent = new Intent(context, SpaceDetails_.class);
        context.startActivity(intent);
    }
}
