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
import com.seintes.wanandroid.net.ArticleDatas;
import com.seintes.wanandroid.project.Tab;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * @Description 体系界面中一级目录Item与列表的适配器，如开发环境框
 * @Author xmhaz
 * @Time 2023/12/12 17:16
 */
public class ProjectItemAdapter extends ArrayAdapter<Tab> {
    public ProjectItemAdapter(@NonNull Context context, int resource, @NonNull List<Tab> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Tab tabItem = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_project, parent, false);
        TextView title =view.findViewById(R.id.treeTitle);
        title.setText(tabItem.getName());
        FlexboxLayout fbl = view.findViewById(R.id.fbl);
        fbl.removeAllViews();
        //子目录
        List<Tab> tabs = tabItem.getChildren();
        for (int i = 0; i < tabs.size(); i++) {
            Tab tab = tabs.get(i);
            TextView childView = (TextView)LayoutInflater.from(fbl.getContext()).inflate(R.layout.item_project_child,fbl,false);
            childView.setText(tab.getName());
            fbl.addView(childView); //动态添加
            //子类标签点击跳转
            int Iposition = i;
            childView.setOnClickListener(v -> {
                Intent inject = new Intent(getContext(), ProjectItemActivity.class);
                inject.putExtra("extra_tab",tabItem);   //传递自定义类
                inject.putExtra("frag_order",Iposition);
                getContext().startActivity(inject);
            });
        }
        // 设置item的点击事件处理逻辑
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onItemClick: list被点击");
                Intent inject = new Intent(getContext(), ProjectItemActivity.class);
                inject.putExtra("extra_tab",tabItem);   //传递自定义类
                getContext().startActivity(inject);
            }
        });
        return view;
    }

}
