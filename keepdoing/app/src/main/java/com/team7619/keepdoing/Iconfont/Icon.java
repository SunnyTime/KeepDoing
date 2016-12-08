package com.team7619.keepdoing.Iconfont;

import java.io.Serializable;
import com.team7619.keepdoing.Iconfont.TypefaceManager;

/**
 * Created by dushiguang on 16/12/8.
 */
public interface Icon extends Serializable {
    TypefaceManager.IconicTypeface getIconicTypeface();

    int getIconUtfValue();
}
