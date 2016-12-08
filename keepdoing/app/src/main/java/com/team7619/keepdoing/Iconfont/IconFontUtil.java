package com.team7619.keepdoing.Iconfont;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.TextView;

import com.team7619.keepdoing.Utils.UiUtils;

/**
 * Created by dushiguang on 16/12/8.
 */
public class IconFontUtil {

    public static void setIcon(Context context, TextView textView, Icon... icons) {
        setIcon(context, textView, null, icons);
    }

    public static void setIcon(Context context, TextView textView, String color, Icon... icons) {
        setIcon(context,textView,0, color, icons);
    }

    public static void setIcon(Context context, TextView textView, int fontSize, Icon... icons) {
        setIcon(context, textView, fontSize, null, icons);
    }

    public static void setIcon(Context context, TextView textView, int fontSize, String color, Icon... icons) {
        textView.setText("");
        addIcon(context, textView, color, fontSize, icons);
    }

    private static void addIcon(Context context, TextView textView, String color, int fontSize, Icon... icons){
        if(icons == null) {
            return;
        }

        for(Icon icon : icons) {
            textView.setTypeface(icon.getIconicTypeface().getmTypeface(context));
            String mIconUtfStr = new String(Character.toChars(icon.getIconUtfValue()));
            Spannable sp = new SpannableString(mIconUtfStr);

            if(!TextUtils.isEmpty(color)) {
                sp.setSpan(new ForegroundColorSpan(parseColor(color)), 0, mIconUtfStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            if(fontSize > 0) {
                int fontSizePx = (int) UiUtils.Sp2px(context, (float)fontSize);
                sp.setSpan(new AbsoluteSizeSpan(fontSizePx), 0, mIconUtfStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            textView.append(sp);
        }
    }

    private static int parseColor(String str) {
        String color = "";
        if(str.length() == 9) {
            color = str.substring(3, str.length());
        } else if(str.length() == 7) {
            color = str.substring(1, str.length());
        } else {
            Log.e("dushiguang","颜色输入有误");
            return Color.GREEN;
        }

        int r = Integer.parseInt(color.substring(0, 2), 16);
        int g = Integer.parseInt(color.substring(2, 4), 16);
        int b = Integer.parseInt(color.substring(4, 6), 16);
        return Color.rgb(r, g, b);
    }
}
