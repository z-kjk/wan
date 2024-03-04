package com.seintes.wanandroid.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.seintes.wanandroid.R;
import com.seintes.wanandroid.activity.WebTextActivity;
import com.seintes.wanandroid.adapter.ActicleAdapter;
import com.seintes.wanandroid.net.ArticleDatas;
import com.seintes.wanandroid.net.ArticleListData;
import com.seintes.wanandroid.net.Data;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Description 带滚动加载的文章显示的基类fragment
 * @Author xmhaz
 * @Time 2023/12/7 14:17
 */
public class BaseFragment extends Fragment implements AbsListView.OnScrollListener{
    private Retrofit mRetrofit;
    ListView listView;

    //页数索引
    public int page = 0;

    //滚动列表相关变量
    public int visibleLastIndex = 0;   //最后的可视项索引
    public int visibleItemCount;       // 当前窗口可见项总数
    public int datasize;          //模拟数据集的条数
    public View loadMoreView;
    public Button loadMoreButton;
    public Handler handler = new Handler();
    public ActicleAdapter adapter;

    public List<ArticleDatas> Articledatas= new ArrayList<>();  //文章列表在此

    public boolean listEmpty=false;   //获得的数据是否为空

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //底是fragment_home
        View view = inflater.inflate(R.layout.fragment_home,null);
        loadMoreView = getLayoutInflater().inflate(R.layout.loadmore, null);
        loadMoreButton = (Button)loadMoreView.findViewById(R.id.loadMoreButton);
        loadMoreButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                loadMoreButton.setText("正在加载中...");   //设置按钮文字
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadMoreData();
                    }
                },2000);

            }
        });

        listView = (ListView)view.findViewById(R.id.list_view);

        listView.addFooterView(loadMoreView);

        return view;
    }
    //获得数据
    public void initRetro()
    {

    }
    public List<ArticleDatas> reDatas(Data<ArticleListData> articleListData)
    {
        if (articleListData == null) return null;
        Log.i("TAG", "body获得");
        ArticleListData info = articleListData.getData();
        if (info == null) return null;
        Log.i("TAG", "info获得");
        List<ArticleDatas>  Datas = info.getDatas();
        return Datas;
    }

    public void setAdapterClick()
    {
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

        /*设置监听*/
        //TODO 为什么放在这里？
        listView.setOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // 滑动过程

        this.visibleItemCount = visibleItemCount;
        visibleLastIndex = firstVisibleItem + visibleItemCount - 1;

        Log.e("========================= ","========================");
        Log.e("firstVisibleItem = ",firstVisibleItem+"");
        Log.e("visibleItemCount = ",visibleItemCount+"");
        Log.e("totalItemCount = ",totalItemCount+"");
        Log.e("========================= ","========================");

        datasize = Articledatas.size();

        Log.i("TAG","数组中的个数为"+datasize);
        //如果所有的记录选项等于数据集的条数，则移除列表底部视图
//        if(totalItemCount == datasize+1){
//            listView.removeFooterView(loadMoreView);
//        }
        /*个数较少的显示加载完成*/
        if(datasize<visibleItemCount)
        {
            loadMoreButton.setText("已全部加载");   //设置按钮文字
        }

    }
    //更新数据
    private void loadMoreData(){
    }
    public String changeHtmlString(String str)
    {
        Document doc = Jsoup.parse(str);
        String text = doc.text();
        return text;
    }
    //设置全部加载完全的说明
    public boolean setOverText()
    {
        if(listEmpty)
        {
            loadMoreButton.setText("已全部加载");  //恢复按钮文字
            return false;
        }
        return true;    //没设置进if判断
    }
}
