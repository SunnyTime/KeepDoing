package com.team7619.keepdoing.Iconfont;


import com.team7619.keepdoing.R;

/**
 * Created by dushiguang on 16/12/8.
 */
public enum IconFont implements Icon {
    IC_MENU_MORE(0xe68c), IC_BACK(0xe6ed), IC_EDITOR_USER_INFO(0xe720);

    IconFont(int i) {
        mIconUtfValue = i;
    }

    private final int mIconUtfValue;
    @Override
    public TypefaceManager.IconicTypeface getIconicTypeface() {
        return TypefaceManager.IconicTypeface.getInstance(R.raw.keepdoing);
    }

    @Override
    public int getIconUtfValue() {
        return mIconUtfValue;
    }
}
