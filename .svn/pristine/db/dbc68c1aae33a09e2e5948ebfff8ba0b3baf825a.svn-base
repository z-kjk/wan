package com.seintes.wanandroid.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.seintes.wanandroid.project.ProjectPageFragment;
import com.seintes.wanandroid.wechat.KnowChapterFragment;

import java.util.List;

/**
 * @Description 体系/导航的适配器
 * @Author xmhaz
 * @Time 2023/12/12 13:57
 */
public class ProjectPageAdapter extends FragmentStateAdapter {
    List<ProjectPageFragment> fragments;
    public ProjectPageAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<ProjectPageFragment> fragments) {
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
