package com.seintes.wanandroid.project;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.seintes.wanandroid.R;
import com.seintes.wanandroid.adapter.ChapterAdapter;
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
 * @Description 适配体系切换栏的数据
 * @Author xmhaz
 * @Time 2023/12/14 11:20
 */
public class PItemDataFragment extends BaseFragment {

    private static final String TAG = "PItemDataFragment";
    private Retrofit mRetrofit;
    private String mFrom;

    ListView listView;
    int page = 0;
    int id = 60;
    private ChapterAdapter adapter;

    public PItemDataFragment(int id)
    {
        super();
        this.id=id;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

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
        getProjectData();
    }
    public void getProjectData()
    {
        Api api = mRetrofit.create(Api.class);
        api.getProjectData(page,id)
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
                        if(page==0)
                        {
                            if(getActivity()!=null)
                            {
                                adapter = new ChapterAdapter(getContext(),R.layout.item_acticle,Articledatas);
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
        }
    }
    private void loadMoreData(){
        page++;
        initRetro();
    }

    @Override
    public void onDestroy() {
        Articledatas.clear();
        page=0;
        super.onDestroy();
    }

}
