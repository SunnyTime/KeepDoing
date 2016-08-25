package com.team7619.keepdoing.Bmob;

import android.content.Context;
import android.util.Log;

import com.team7619.keepdoing.Key.keys;
import com.team7619.keepdoing.entity.Space_Info;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by dushiguang on 16/8/25.
 */
public class BmobUtils {
    BmobQueryListener mBmobFindListener;
    private Context context;
    private static BmobUtils mBmobUtils;

    public static BmobUtils getBmobUtils() {
        if(null == mBmobUtils) {
            mBmobUtils = new BmobUtils();
        }
        return mBmobUtils;
    }

    public void initBmob(Context context) {
        this.context = context;
        Bmob.initialize(context, keys.BmobApplicationID);
    }

    /**
     * 查找Bmob数据
     */
    public List<Space_Info> QueryBmobDb(){
        final List<Space_Info> al = new ArrayList<>();
        BmobQuery<Space_Info> query = new BmobQuery<Space_Info>();
        query.order("publish_time");
        query.findObjects(new FindListener<Space_Info>(){
            @Override
            public void done(List<Space_Info> list, BmobException e) {
                Log.e("dushiguang","e-----" + e);
                Log.e("dushiguang","list-----" + list.size());
                for(Space_Info listInfo : list) {
                    al.add(listInfo);
                }
            }
        });
        Log.e("dushiguang","al-----" + al.size());
        return al;
    }
}
