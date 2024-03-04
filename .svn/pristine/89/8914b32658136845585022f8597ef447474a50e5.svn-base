package com.seintes.wanandroid.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.seintes.wanandroid.project.PItemDataFragment;
import com.seintes.wanandroid.wechat.ChapterFragment;

import java.util.List;

/**
 * @Description 二级目录viewpaper与fragment的适配器，如开发环境中android studio相关等四个页面
 * @Author xmhaz
 * @Time 2023/12/14 11:28
 */
public class ProjectItemDataAdapter extends FragmentStateAdapter {
    List<PItemDataFragment> fragments;
    public ProjectItemDataAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<PItemDataFragment> fragments) {
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
