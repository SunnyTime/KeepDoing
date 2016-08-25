package com.team7619.keepdoing.Bmob;

import android.util.Log;

import com.team7619.keepdoing.entity.Space_Info;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by dushiguang on 16/8/25.
 */
public class BmobQueryListener extends FindListener<Space_Info> {
    @Override
    public void done(List<Space_Info> list, BmobException e) {
        Log.e("dushiguang","e-----" + e);
    }
}
