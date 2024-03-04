package com.seintes.wanandroid.adapter;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.seintes.wanandroid.R;
import com.seintes.wanandroid.net.ArticleDatas;

import java.util.List;

/**
 * @Description 项目中的适配器
 * @Author xmhaz
 * @Time 2023/12/11 10:50
 */
public class KnowledgeAdapter extends ActicleAdapter{
    public KnowledgeAdapter(@NonNull Context context, int resource, @NonNull List<ArticleDatas> objects) {
        super(context, resource, objects);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ArticleDatas article=getItem(position);//得到当前项的Article实例
        View view = super.getView(position,convertView,parent);
        //补充
        TextView desc=view.findViewById(R.id.tvDesc);
        ImageView img = view.findViewById(R.id.ivProject);
        // 设置要显示的图片和文字
        desc.setText(article.getDesc());

        return view;
    }
}
