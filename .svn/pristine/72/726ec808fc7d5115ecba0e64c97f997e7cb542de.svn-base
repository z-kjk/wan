package com.seintes.wanandroid.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.seintes.wanandroid.R;
import com.seintes.wanandroid.adapter.ProjectItemDataAdapter;
import com.seintes.wanandroid.adapter.WechatAdapter;
import com.seintes.wanandroid.api.Api;
import com.seintes.wanandroid.net.Data;
import com.seintes.wanandroid.project.PItemDataFragment;
import com.seintes.wanandroid.project.Tab;
import com.seintes.wanandroid.wechat.Author;
import com.seintes.wanandroid.wechat.ChapterFragment;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Description 点击item跳转的各体系导航页面
 * @Author xmhaz
 * @Time 2023/12/14 9:35
 */
public class ProjectItemActivity extends AppCompatActivity {
    private static final String TAG = "ProjectItemActivity";
    private TabLayout wxTab;
    private ViewPager2 wxPager2;

    List<String> titles=new ArrayList<>();
    List<Integer> ids = new ArrayList<>();

    ProjectItemDataAdapter myAdapter = null;

    List<PItemDataFragment> chFragments = new ArrayList<>();

    int order = 0;  //默认的显示界面

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_wechat);
        wxTab = findViewById(R.id.wx_tab);
        wxTab.setTabMode(TabLayout.MODE_SCROLLABLE);    //设置可滑动
        wxPager2 = findViewById(R.id.wx_pager2);
        wxPager2.setSaveEnabled(false); //设置不存储
        Tab ParentTab = (Tab)getIntent().getSerializableExtra("extra_tab");
        order = getIntent().getIntExtra("frag_order",0);    //获得显示的界面序号
        for(Tab i:ParentTab.getChildren())
        {
            titles.add(i.getName());
            ids.add(i.getId());
            chFragments.add(new PItemDataFragment(i.getId()));
        }
        //实例化适配器
        myAdapter=new ProjectItemDataAdapter(getSupportFragmentManager(),getLifecycle(),chFragments);
        //设置适配器
        wxPager2.setAdapter(myAdapter);
        wxPager2.setCurrentItem(order);
        //TabLayout和Viewpager2进行关联
        new TabLayoutMediator(wxTab, wxPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(titles.get(position));
            }
        }).attach();

    }

}
