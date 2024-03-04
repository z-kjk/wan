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

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.seintes.wanandroid.adapter.KnowItemAdapter;
import com.seintes.wanandroid.api.Api;
import com.seintes.wanandroid.net.Data;
import com.seintes.wanandroid.wechat.Author;
import com.seintes.wanandroid.wechat.KnowChapterFragment;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class KnowledgeFragment extends Fragment {
    private static final String TAG = "KnowledgeFragment";
    private TabLayout wxTab;
    private ViewPager2 wxPager2;

    List<String> titles=new ArrayList<>();
    List<Integer> ids = new ArrayList<>();

    KnowItemAdapter myAdapter = null; //对应的adapter也要改
    FragmentManager fm = null;
    Lifecycle lc = null;

    List<KnowChapterFragment> chFragments = new ArrayList<KnowChapterFragment>();

    private Retrofit mRetrofit;
    private String mFrom;
    static KnowledgeFragment newInstance(String from){
        KnowledgeFragment fragment = new KnowledgeFragment();
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
        if(ids.size()==0)
        {
            initRetro();
        }
        else{
            setFragments(); //第二次，不获取直接配置
        }

    }
    public void initRetro()
    {
        mRetrofit= new Retrofit.Builder()
                .baseUrl("https://wanandroid.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        getKnowleageAuthor();
    }
    //获得各个公众号作者
    public void getKnowleageAuthor()
    {
        Api api = mRetrofit.create(Api.class);
        api.getKnowleageAuthor()
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
                            String title = changeHtmlString(i.getName());
                            titles.add(title);
                            ids.add(i.getId());
                            Log.e("TAG", "名字为"+i.getName());
                            Log.e("TAG", "id为"+i.getId());
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
        if(chFragments.size()==0)
        {
            for (int id:ids)
            {
                chFragments.add(new KnowChapterFragment(id));
                Log.i(TAG,"新增项目中的fragment");
            }
        }
        //实例化适配器
        myAdapter=new KnowItemAdapter(fm,lc,chFragments);
        //设置适配器
        wxPager2.setAdapter(myAdapter);
        //TabLayout和Viewpager2进行关联
        new TabLayoutMediator(wxTab, wxPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(titles.get(position));
            }
        }).attach();

    }
    public String changeHtmlString(String str)
    {
        Document doc = Jsoup.parse(str);
        String text = doc.text();
        return text;
    }
}