package com.seintes.wanandroid.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.seintes.wanandroid.R;
import com.seintes.wanandroid.net.ArticleDatas;
import com.seintes.wanandroid.net.Tags;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

/**
 * @Description 文章item适配器,添加了标签显示
 * @Author xmhaz
 * @Time 2023/12/4 9:50
 */
public class ActicleAdapter extends ChapterAdapter {
    public ActicleAdapter(@NonNull Context context, int resource, @NonNull List<ArticleDatas> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ArticleDatas article=getItem(position);//得到当前项的Article实例
        View view = super.getView(position,convertView,parent);
        TextView author =view.findViewById(R.id.tvAuthor);
            //补充
        TextView top =view.findViewById(R.id.tvTagTop);
        TextView news =view.findViewById(R.id.tvTagNew);
        TextView qua =view.findViewById(R.id.tvTagQa);
        TextView desc=view.findViewById(R.id.tvDesc);
        // 设置要显示的图片和文字
        author.setText(getShowAuthor(article));
            //测试
        desc.setText(article.getDesc());
            //标签页
        news.setVisibility(article.isFresh() ? View.VISIBLE : View.GONE);
        qua.setVisibility(isQua(article) ? View.VISIBLE : View.GONE);
        top.setVisibility(article.isTop() ? View.VISIBLE : View.GONE);
        return view;
    }
    //没有作者就写分享作者
    public String getShowAuthor(ArticleDatas article)
    {
        String author = article.getAuthor();
        if(author.equals(""))
        {
//            Log.i("TAG","输出"+article.getShareUser());
            return article.getShareUser();
        }
        else{
            return author;
        }

    }
    //判断是否问答
    public Boolean isQua(ArticleDatas article)
    {
        List<Tags> tag = new ArrayList<>();
        tag = article.getTags();
        if(tag.isEmpty())
            return false;
        for(Tags i : tag)
        {
            if(i.getName().equals("问答"))
                return true;
        }
        return false;
    }
}
