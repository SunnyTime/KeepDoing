package com.team7619.keepdoing.entity;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by dushiguang on 16/12/13.
 */
public class _User extends BmobUser {
    BmobFile userpic;
    Integer totalarticle;

    public BmobFile getUserpic() {
        return userpic;
    }

    public void setUserpic(BmobFile userpic) {
        this.userpic = userpic;
    }

    public Integer getTotalarticle() {
        return totalarticle;
    }

    public void setTotalarticle(Integer totalarticle) {
        this.totalarticle = totalarticle;
    }
}
