package com.seintes.wanandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.seintes.wanandroid.test.Wx2Fragment;

/**
 * @Description 导航栏各资源的配置
 * @Author xmhaz
 * @Time 2023/11/30 11:14
 */
public class NavDataGenerator {
    public static final int []mTabRes = new int[]{
            R.drawable.ic_nav_home,
            R.drawable.ic_nav_knowledge,
            R.drawable.ic_nav_project,
            R.drawable.ic_nav_question,
//            R.drawable.ic_nav_question
            R.drawable.ic_nav_wechat
    };
    public static final int []mTabResPress = new int[]{
            R.drawable.ic_nav_home_selectd,
            R.drawable.ic_nav_knowledge_selectd,
            R.drawable.ic_nav_project_selectd,
            R.drawable.ic_nav_question_selectd,
//            R.drawable.ic_nav_question_selectd
            R.drawable.ic_nav_wechat_selectd
    };
    public static final String []mTabTitle = new String[]{
            "首页",
            "项目",
            "体系",
            "问答",
            "公众号"
    };
    public static Fragment[] getFragments(String from){
        Fragment fragment[] = new Fragment[5];
        fragment[0]=HomeFragment.newInstance(from);
        fragment[1]=KnowledgeFragment.newInstance(from);
        fragment[2]=ProjectFragment.newInstance(from);
//        fragment[2]=ProjectFragment.newInstance(from);
        fragment[3]=QuestionFragment.newInstance(from);
        fragment[4]=WechatFragment.newInstance(from);
        return fragment;
    }
    /**
     * 获取Tab 显示的内容
     */
    public static View getTabView(Context context, int position){
        View view = LayoutInflater.from(context).inflate(R.layout.bottom_tab_layout_ac,null);
        ImageView tabIcon = (ImageView) view.findViewById(R.id.tab_content_image);
        tabIcon.setImageResource(NavDataGenerator.mTabRes[position]);
        TextView tabText = (TextView) view.findViewById(R.id.tab_content_text);
        tabText.setText(mTabTitle[position]);
        return view;
    }
}
