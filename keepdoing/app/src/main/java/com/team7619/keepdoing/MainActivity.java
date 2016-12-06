package com.team7619.keepdoing;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;

import com.team7619.keepdoing.activity.WriteActivity;
import com.team7619.keepdoing.fragment.MessageFragment_;
import com.team7619.keepdoing.fragment.PersonCenterFragment_;
import com.team7619.keepdoing.fragment.SpaceFragment_;
import com.team7619.keepdoing.myviews.BottomNavigation.BottomNavigationItem;
import com.team7619.keepdoing.myviews.BottomNavigation.BottomNavigationView;
import com.team7619.keepdoing.myviews.BottomNavigation.OnBottomNavigationItemClickListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import java.util.List;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    public BottomNavigationView bottomNavigationView;
    private FragmentManager mFragmentManager;
    private SpaceFragment_ spaceFragment;
    private MessageFragment_ messageFragment;
    private PersonCenterFragment_ personCenterFragment;

    public static final int MAINACTIVITYKEY = 0x01;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    void afterViews() {
        mFragmentManager = getSupportFragmentManager();
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        int[] image = {R.drawable.ic_space_24, R.drawable.ic_messgae_24,
                R.drawable.ic_user_24,R.drawable.ic_write_24};
        int[] color = {ContextCompat.getColor(this, R.color.mainColor), ContextCompat.getColor(this, R.color.mainColor),
                ContextCompat.getColor(this, R.color.mainColor),ContextCompat.getColor(this, R.color.mainColor)};

        if (bottomNavigationView != null) {
            bottomNavigationView.isWithText(false);
            bottomNavigationView.isColoredBackground(true);
            bottomNavigationView.setItemActiveColorWithoutColoredBackground(ContextCompat.getColor(this, R.color.mainColor));
            bottomNavigationView.setFont(Typeface.createFromAsset(getApplicationContext().getAssets(), "Noh_normal.ttf"));
        }

        BottomNavigationItem spaceItem = new BottomNavigationItem
                ("Space", color[0], image[0]);
        BottomNavigationItem messageItem = new BottomNavigationItem
                ("Message", color[1], image[1]);
        BottomNavigationItem uesrItem = new BottomNavigationItem
                ("User  ", color[2], image[2]);
        BottomNavigationItem writeItem = new BottomNavigationItem
                ("Write", color[3], image[3]);

        bottomNavigationView.addTab(spaceItem);
        bottomNavigationView.addTab(messageItem);
        bottomNavigationView.addTab(uesrItem);
        bottomNavigationView.addTab(writeItem);

        bottomNavigationView.setOnBottomNavigationItemClickListener(new OnBottomNavigationItemClickListener() {
            @Override
            public void onNavigationItemClick(int index) {
                setTabSelection(index);
            }
        });

        setTabSelection(0);
    }

    private void setTabSelection(int index) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        hideFragments(transaction);
        switch(index) {
            case 0:
                if(null == spaceFragment) {
                    spaceFragment = new SpaceFragment_();
                    transaction.add(R.id.content,spaceFragment);
                } else {
                    transaction.show(spaceFragment);
                }
                break;
            case 1:
                if(null == messageFragment) {
                    messageFragment = new MessageFragment_();
                    transaction.add(R.id.content,messageFragment);
                } else {
                    transaction.show(messageFragment);
                }
                break;
            case 2:
                if(null == personCenterFragment) {
                    personCenterFragment = new PersonCenterFragment_();
                    transaction.add(R.id.content,personCenterFragment);
                } else {
                    transaction.show(personCenterFragment);
                }
                break;
            case 3:
                WriteActivity.jumpToWriteActivity(this);
                break;

        }
        transaction.commitAllowingStateLoss();
    }

    private void hideFragments(FragmentTransaction transaction) {
        List<Fragment> fragments = mFragmentManager.getFragments();
        if (fragments != null && !fragments.isEmpty()) {
            for (Fragment f : fragments) {
                if (f != null && !f.isHidden()) {
                    transaction.hide(f);
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0x01 && resultCode == 1) {
            setTabSelection(0);
            spaceFragment.getListData(0);
            bottomNavigationView.selectTab(0);
        }
    }
}
