package com.seintes.wanandroid.project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.seintes.wanandroid.R;
import com.seintes.wanandroid.activity.ProjectItemActivity;
import com.seintes.wanandroid.activity.WebTextActivity;
import com.seintes.wanandroid.adapter.ActicleAdapter;
import com.seintes.wanandroid.adapter.ChapterAdapter;
import com.seintes.wanandroid.adapter.NaviItemAdapter;
import com.seintes.wanandroid.adapter.ProjectItemAdapter;
import com.seintes.wanandroid.api.Api;
import com.seintes.wanandroid.net.ArticleDatas;
import com.seintes.wanandroid.net.ArticleListData;
import com.seintes.wanandroid.net.Data;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Description 体系页面下体系/导航的显示切片
 * @Author xmhaz
 * @Time 2023/12/12 13:56
 */
public class ProjectPageFragment extends Fragment {
    private static final String TAG = "ProjectPageFragment";
    private String type;
    private Retrofit mRetrofit;
    ListView listView;
    private List<Tab> ProjectDatas = new ArrayList<>();
    private List<NaviTab> NaviDatas = new ArrayList<>();    //导航数据
    private ProjectItemAdapter adapter;
    private NaviItemAdapter naviadapter;


    public ProjectPageFragment(String type)
    {
        this.type=type;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,null);
        return view;
    }

    @Override
    public void onViewCreated(@androidx.annotation.NonNull View view, @Nullable Bundle savedInstanceState) {
        listView = view.findViewById(R.id.list_view);
        initRetro();
    }
    public void initRetro()
    {
        mRetrofit= new Retrofit.Builder()
                .baseUrl("https://wanandroid.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        switch (type)
        {
            case "体系":
                getProjectdata();
                break;
            case "导航":
                getNavidata();
                break;
        }
    }
    /*体系页面*/
    public void getProjectdata()
    {
        Api api = mRetrofit.create(Api.class);
        api.getProjectDate()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Data<List<Tab>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Data<List<Tab>> ProListData) {
                        List<Tab>  proItemDatas = getProData(ProListData);
                        for(Tab i :proItemDatas)
                        {
//                            Log.i(TAG, "onNext:"+i.toString());
                            ProjectDatas.add(i);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        if(getActivity()!=null)
                        {
                            //布局文件与adapter中一致
                            adapter = new ProjectItemAdapter(getActivity(),R.layout.item_project,ProjectDatas);
                        }
                        else
                        {
                            Log.i(TAG,"加载activity失败");
                        }
                        listView.setAdapter(adapter);
                    }

                });
    }
    //导航的数据
    public void getNavidata()
    {
        Api api = mRetrofit.create(Api.class);
        api.getNaviData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Data<List<NaviTab>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Data<List<NaviTab>> NaviListData) {
                        List<NaviTab> naviItemDatas = getNaviData(NaviListData);
                        for(NaviTab i :naviItemDatas)
                        {
//                            Log.i(TAG, "onNext:"+i.toString());
                            NaviDatas.add(i);
                        }
                        NaviTab test = NaviDatas.get(0);
//                        Log.i(TAG, "onNext:第一个名称"+test.getName());
//                        Log.i(TAG, "onNext:第一个文章个数为"+test.getArticles().size());
//                        Log.i(TAG, "第一个文章的最后一篇数据"+test.getArticles().get(16).toString());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        if(getActivity()!=null)
                        {
                            naviadapter = new NaviItemAdapter(getActivity(),R.layout.item_project,NaviDatas);
                        }
                        else
                        {
                            Log.i(TAG,"加载activity失败");
                        }
                        listView.setAdapter(naviadapter);
                    }

                });
    }
    //返回的就是父集
    public List<Tab> getProData(Data<List<Tab>> ProListData)
    {
        if (ProListData == null) return null;
        Log.i("TAG", "body获得");
        List<Tab> info = ProListData.getData();
        if (info == null) return null;
        Log.i("TAG", "info获得");
        return info;
    }
    //一级（大标签）
    public List<NaviTab> getNaviData(Data<List<NaviTab>> NaviListData)
    {
        if (NaviListData == null) return null;
        Log.i("TAG", "body获得");
        List<NaviTab> info = NaviListData.getData();
        if (info == null) return null;
        Log.i("TAG", "info获得");
        return info;
    }
}
