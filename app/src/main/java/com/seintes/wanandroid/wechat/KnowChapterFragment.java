package com.seintes.wanandroid.wechat;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.seintes.wanandroid.R;
import com.seintes.wanandroid.adapter.ActicleAdapter;
import com.seintes.wanandroid.adapter.KnowledgeAdapter;
import com.seintes.wanandroid.api.Api;
import com.seintes.wanandroid.base.BaseFragment;
import com.seintes.wanandroid.net.ArticleDatas;
import com.seintes.wanandroid.net.ArticleListData;
import com.seintes.wanandroid.net.Data;

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
 * @Description 项目内部的fragment，如
 * @Author xmhaz
 * @Time 2023/12/11 11:09
 */
public class KnowChapterFragment extends BaseFragment {

    public KnowChapterFragment(int id) {
        super();
        this.id=id;
        Log.e(TAG,"创建的id的值为"+id);
    }

    private static final String TAG = "KnowChapterFragment";
    private Retrofit mRetrofit;
    private String mFrom;
    ListView listView;
    int page = 1;
    int id = 294;
    private KnowledgeAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        //获得listview才不为空指针
        listView = (ListView)view.findViewById(R.id.list_view);

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
        getKnowActicle();
    }
    public void getKnowActicle()
    {
        Api api = mRetrofit.create(Api.class);
        api.getKnArticleData(page,id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Data<ArticleListData>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull Data<ArticleListData> articleListData) {
                        List<ArticleDatas> topDatas = reDatas(articleListData);
                        listEmpty = topDatas.isEmpty();
                        for(ArticleDatas i :topDatas)
                        {
                            String desc = changeHtmlString(i.getDesc());
                            String title = changeHtmlString(i.getTitle());
                            i.setDesc(desc);
                            i.setTitle(title);
                            Articledatas.add(i);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        if(page==1)
                        {
                            if(getActivity()!=null)
                            {
                                adapter = new KnowledgeAdapter(getContext(),R.layout.item_acticle,Articledatas);
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


    private void loadMoreData(){
        Log.i("TAG","更新load值");
        page++;
        Log.i("TAG","page值为"+page);
        initRetro();
    }
    @Override
    public void onDestroy() {
        Articledatas.clear();
        page=1;
        super.onDestroy();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG,"KnowChapterFragment创建");
        Log.v(TAG,"此时的cid为"+id);
        Log.v(TAG,"此时的page为"+page);
    }

}
