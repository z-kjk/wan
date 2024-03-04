package com.seintes.wanandroid;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.seintes.wanandroid.activity.WebTextActivity;
import com.seintes.wanandroid.adapter.ActicleAdapter;
import com.seintes.wanandroid.api.Api;
import com.seintes.wanandroid.net.ArticleDatas;
import com.seintes.wanandroid.net.ArticleListData;
import com.seintes.wanandroid.net.Data;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

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


public class QuestionFragment extends Fragment {

    private static final String TAG = "QuestionFragment";
    private Retrofit mRetrofit;
    private String mFrom;
    ListView listView;


    static QuestionFragment newInstance(String from){
        QuestionFragment fragment = new QuestionFragment();
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
        View view = inflater.inflate(R.layout.fragment_home,null);

        listView = (ListView)view.findViewById(R.id.list_view);
        initRetro();
        return view;
    }

    public void initRetro()
    {
        mRetrofit= new Retrofit.Builder()
                .baseUrl("https://wanandroid.com/")
                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        getWendadata();
    }
    public void getWendadata()
    {
        Api api = mRetrofit.create(Api.class);
        Call<Data<ArticleListData>> call = api.getWendaData(1);
        call.enqueue(new Callback<Data<ArticleListData>>() {
            //请求成功时回调
            @Override
            public void onResponse(Call<Data<ArticleListData>> call, Response<Data<ArticleListData>> response) {
                Data<ArticleListData> body = response.body();
                if (body == null) return;
                Log.i("TAG","body获得成功");
                ArticleListData info = body.getData();
                if (info == null) return;
                Log.i("TAG","info获得成功");
                List<ArticleDatas> Articledatas = info.getDatas();
                for(ArticleDatas i :Articledatas)
                {
                    String desc = i.getDesc();
                    Document doc = Jsoup.parse(desc);
                    String text = doc.text();
                    i.setDesc(text);
                }
                ActicleAdapter adapter=null;
                if(getActivity()!=null)
                {
                    adapter = new ActicleAdapter(getActivity(),R.layout.item_acticle,Articledatas);
                }
                listView.setAdapter(adapter);
                Log.i("TAG", "执行到显示数据");
                //第六步：listview 的点击事件
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        ArticleDatas article= Articledatas.get(position) ;
                        Intent inject = new Intent(getActivity(), WebTextActivity.class);
                        inject.putExtra("extra_url",article.getLink());
                        startActivity(inject);
                    }
                });
            }
            //请求失败时回调
            @Override
            public void onFailure(Call<Data<ArticleListData>> call, Throwable throwable) {
                Log.i("TAG","连接失败");
            }
        });
    }
}