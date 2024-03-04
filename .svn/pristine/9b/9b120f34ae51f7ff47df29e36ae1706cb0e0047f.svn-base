package com.seintes.wanandroid.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.seintes.wanandroid.wechat.ChapterFragment;
import com.seintes.wanandroid.wechat.KnowChapterFragment;

import java.util.List;

/**
 * @Description 项目中各fragment的适配器
 * @Author xmhaz
 * @Time 2023/12/11 13:51
 */
public class KnowItemAdapter extends FragmentStateAdapter {
    List<KnowChapterFragment> fragments;
    public KnowItemAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<KnowChapterFragment> fragments) {
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
