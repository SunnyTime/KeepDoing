package com.team7619.keepdoing.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.team7619.keepdoing.R;
import com.team7619.keepdoing.widget.banner.LMBanners;


/**
 * Created by luomin on 16/7/12.
 */
public class LocalImgAdapter implements com.team7619.keepdoing.widget.banner.adapter.LBaseAdapter<Integer> {
    private Context mContext;

    public LocalImgAdapter(Context context) {
        mContext=context;
    }




    @Override
    public View getView(final LMBanners lBanners, Context context, int position, Integer data) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.id_image);
        imageView.setImageResource(data);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }
}
