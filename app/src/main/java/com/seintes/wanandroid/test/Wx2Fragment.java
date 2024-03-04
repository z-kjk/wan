package com.seintes.wanandroid.test;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.seintes.wanandroid.R;
import com.seintes.wanandroid.WechatFragment;
import com.seintes.wanandroid.adapter.WechatAdapter;
import com.seintes.wanandroid.api.Api;
import com.seintes.wanandroid.net.Data;
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
 * @Description 测试viewpaper版本的fragment
 * @Author xmhaz
 * @Time 2023/12/8 11:30
 */
public class Wx2Fragment extends Fragment {
    private static final String TAG = "Wx2Fragment";
    private TabLayout wxTab;
    private ViewPager vp;
    List<String> titles=new ArrayList<>();
    List<Integer> ids = new ArrayList<>();
    List<ChapterFragment> chFragments = new ArrayList<>();

    private Retrofit mRetrofit;
    private String mFrom;
    public static Wx2Fragment newInstance(String from){
        Wx2Fragment fragment = new Wx2Fragment();
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
        Log.v(TAG,"WechatFragment创建");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wechat,null);

        wxTab = view.findViewById(R.id.wx_tab);
        wxTab.setTabMode(TabLayout.MODE_SCROLLABLE);    //设置可滑动

        initRetro();

        return view;
    }
    public void initRetro()
    {
        mRetrofit= new Retrofit.Builder()
                .baseUrl("https://wanandroid.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        getWxAuthor();
    }
    //获得各个公众号作者
    public void getWxAuthor()
    {
        Api api = mRetrofit.create(Api.class);
        api.getWxAuthor()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Data<List<Author>>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull Data<List<Author>> authorListData) {
                        List<Author>  authorList = authorListData.getData();
                        for(Author i :authorList)
                        {
                            titles.add(i.getName());
                            ids.add(i.getId());
                        }
                        Author test = authorList.get(0);
                        Log.i("TAG", test.toString());
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        setFragments();
                    }
                });
    }
    public void setFragments()
    {

        //循环id列表添加数据
        for (int id:ids)
        {
            chFragments.add(new ChapterFragment(id));
        }
        vp.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return chFragments.get(position);
            }

            @Override
            public int getCount() {
                return chFragments.size();
            }
        });
    }
    @Override
    public void onDestroy() {
        Log.v(TAG,"WechatFragment销毁");
        Log.i(TAG,"fragment个数"+chFragments.size());
        super.onDestroy();
    }
}
