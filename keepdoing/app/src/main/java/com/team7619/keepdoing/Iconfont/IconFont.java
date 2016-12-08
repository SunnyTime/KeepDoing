package com.team7619.keepdoing.Iconfont;


import com.team7619.keepdoing.R;

/**
 * Created by dushiguang on 16/12/8.
 */
public enum IconFont implements Icon {
    IC_MENU_MORE(0xe68c);

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
