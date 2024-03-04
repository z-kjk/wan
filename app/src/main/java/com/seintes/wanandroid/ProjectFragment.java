package com.seintes.wanandroid;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.seintes.wanandroid.adapter.ProjectPageAdapter;
import com.seintes.wanandroid.project.ProjectPageFragment;
import java.util.ArrayList;
import java.util.List;


public class ProjectFragment extends Fragment {
    private static final String TAG = "ProjectFragment";
    private TabLayout wxTab;
    private ViewPager2 wxPager2;

    List<String> titles=new ArrayList<>();

    ProjectPageAdapter myAdapter = null;
    FragmentManager fm = null;
    Lifecycle lc = null;

    List<ProjectPageFragment> chFragments = new ArrayList<ProjectPageFragment>();

    private String mFrom;
    static ProjectFragment newInstance(String from){
        ProjectFragment fragment = new ProjectFragment();
        Bundle bundle = new Bundle();
        bundle.putString("from",from);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            mFrom = getArguments().getString("from");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wechat,null);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        wxTab = view.findViewById(R.id.wx_tab);
        wxTab.setTabMode(TabLayout.MODE_SCROLLABLE);    //设置可滑动
        wxPager2 = view.findViewById(R.id.wx_pager2);
        wxPager2.setSaveEnabled(false); //设置不存储

        fm = getChildFragmentManager();
        lc =  getLifecycle();
        titles.add("体系");
        titles.add("导航");
        for (String name:titles)
        {
            chFragments.add(new ProjectPageFragment(name));
            Log.i(TAG,"体系/导航切片");
        }
        //实例化适配器
        myAdapter=new ProjectPageAdapter(fm,lc,chFragments);
        //设置适配器
        wxPager2.setAdapter(myAdapter);
        new TabLayoutMediator(wxTab, wxPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(titles.get(position));
            }
        }).attach();
    }

    @Override
    public void onDestroy() {
        titles.clear();
        chFragments.clear();
        super.onDestroy();
    }

}