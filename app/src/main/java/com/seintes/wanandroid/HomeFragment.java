package com.seintes.wanandroid;

import android.content.Intent;
import android.os.Bundle;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.seintes.wanandroid.activity.WebTextActivity;
import com.seintes.wanandroid.adapter.ActicleAdapter;
import com.seintes.wanandroid.api.Api;
import com.seintes.wanandroid.base.BaseFragment;
import com.seintes.wanandroid.net.ArticleDatas;
import com.seintes.wanandroid.net.ArticleListData;
import com.seintes.wanandroid.net.Data;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Path;


public class HomeFragment extends BaseFragment {
    private static final String TAG = "HomeFragment";
    private Retrofit mRetrofit;
    private String mFrom;
    ListView listView;

    static HomeFragment newInstance(String from){
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("from",from);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        listView = (ListView)view.findViewById(R.id.list_view);

        initRetro("top",page);
        return view;
    }

    //置顶数据
    public void initRetro(String str,int page)
    {
        mRetrofit= new Retrofit.Builder()
                .baseUrl("https://wanandroid.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        if(str.equals("top"))
        {
            getTopArticledata();
        }
        else {
            getHomeArticleData(page);   //先置顶数据，再后面的文章数据
        }
    }

    public void getTopArticledata()
    {
        Api api = mRetrofit.create(Api.class);
        api.getTopArticleData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Data<List<ArticleDatas>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Data<List<ArticleDatas>> articleListData) {
                        List<ArticleDatas>  topDatas = articleListData.getData();
                        listEmpty = topDatas.isEmpty();
                        for(ArticleDatas i :topDatas)
                        {
                            i.setTop(true);
                            //将html转换为普通文字
                            String desc = changeHtmlString(i.getDesc());
                            i.setDesc(desc);
                            String text = changeHtmlString(i.getTitle());
                            i.setTitle(text);
                            Articledatas.add(i);
                        }
                        ArticleDatas test = topDatas.get(0);
                        Log.i("TAG", test.toString());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        /*获取置顶数据后，再增加文章数据*/
                        initRetro("home",page);
                    }
                });
    }
    public void getHomeArticleData(int p)
    {
        Api api = mRetrofit.create(Api.class);
        api.getHomeArticleData(p)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Data<ArticleListData>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Data<ArticleListData> articleListData) {
                        List<ArticleDatas>  topDatas = reDatas(articleListData);
                        for(ArticleDatas i :topDatas)
                        {
                            String text = changeHtmlString(i.getTitle());
                            i.setTitle(text);
                            Articledatas.add(i);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        if(page == 0){
                            //仅第一次更新adapter
                            if(getActivity()!=null)
                            {
                                adapter = new ActicleAdapter(getActivity(),R.layout.item_acticle,Articledatas);
                            }
                            else
                            {
                                Log.i(TAG,"加载activity失败");
                            }
                            listView.setAdapter(adapter);
                            setAdapterClick();
                        }
                        else
                        {
                            if(setOverText())
                            {
                                adapter.notifyDataSetChanged();
                                loadMoreButton.setText("查看更多...");  //恢复按钮文字
                            }
                        }
                    }

        });
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
        // 记录当前滑动状态
        int itemsLastIndex = adapter.getCount()-1;  //数据集最后一项的索引
        int lastIndex = itemsLastIndex + 1;
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                && visibleLastIndex == lastIndex) {
            //滑到顶部
            loadMoreButton.setText("正在加载中...");   //设置按钮文字
            loadMoreData();

            Log.e("TAG","onScrollStateChange执行");
        }

    }
    //更新数据
    private void loadMoreData(){
        Log.i("TAG","更新load值");
        page++;
        Log.i("TAG","page值为"+page);
        initRetro("home",page);
    }

    @Override
    public void onDestroy() {
        Articledatas.clear();
        page=0;
        super.onDestroy();
    }
}