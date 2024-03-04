package com.seintes.wanandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.flexbox.FlexboxLayout;
import com.seintes.wanandroid.R;
import com.seintes.wanandroid.activity.ProjectItemActivity;
import com.seintes.wanandroid.activity.WebTextActivity;
import com.seintes.wanandroid.net.ArticleDatas;
import com.seintes.wanandroid.project.NaviTab;
import com.seintes.wanandroid.project.SimpleActicle;
import com.seintes.wanandroid.project.Tab;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * @Description 导航下二级标签的适配器，eg常用网址的开发者网站等
 * @Author xmhaz
 * @Time 2023/12/14 15:50
 */
public class NaviItemAdapter extends ArrayAdapter<NaviTab> {
    public NaviItemAdapter(@NonNull Context context, int resource, @NonNull List<NaviTab> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        NaviTab tabItem = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_project, parent, false);
        TextView title =view.findViewById(R.id.treeTitle);
        title.setText(tabItem.getName());
        FlexboxLayout fbl = view.findViewById(R.id.fbl);
        fbl.removeAllViews();
        //文章目录
        List<SimpleActicle> articles = tabItem.getArticles();
        for (int i = 0; i < articles.size(); i++) {
            SimpleActicle item = articles.get(i);
            TextView childView = (TextView)LayoutInflater.from(fbl.getContext()).inflate(R.layout.item_project_child,fbl,false);
            childView.setText(item.getTitle());
            fbl.addView(childView); //动态添加
            //跳转到网页显示
            String url = item.getLink();
            childView.setOnClickListener(v -> {
                Intent inject = new Intent(getContext(), WebTextActivity.class);
                inject.putExtra("extra_url",url);
                getContext().startActivity(inject);
            });
        }
        return view;
    }
}
