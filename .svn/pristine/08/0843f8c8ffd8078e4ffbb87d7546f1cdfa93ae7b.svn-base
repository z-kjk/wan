package com.seintes.wanandroid.adapter;

import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.seintes.wanandroid.QuestionFragment;
import com.seintes.wanandroid.WechatFragment;
import com.seintes.wanandroid.wechat.ChapterFragment;

import java.util.List;

/**
 * @Description 公众号作者的适配器
 * @Author xmhaz
 * @Time 2023/12/6 16:02
 */
public class WechatAdapter extends FragmentStateAdapter {

    List<ChapterFragment> fragments;
    public WechatAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<ChapterFragment> fragments) {
        super(fragmentManager, lifecycle);
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }

}
