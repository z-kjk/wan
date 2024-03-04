package com.seintes.wanandroid.api;

import com.seintes.wanandroid.net.ArticleDatas;
import com.seintes.wanandroid.net.ArticleListData;
import com.seintes.wanandroid.net.Data;
import com.seintes.wanandroid.project.NaviTab;
import com.seintes.wanandroid.project.Tab;
import com.seintes.wanandroid.wechat.Author;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @Description
 * @Author xmhaz
 * @Time 2023/12/1 15:38
 */
public interface Api {

    //置顶列表
    @GET("article/top/json")
    Observable<Data<List<ArticleDatas>>> getTopArticleData();
    //文章列表
    @GET("article/list/{page}/json")
    Observable<Data<ArticleListData>> getHomeArticleData(@Path("page") int page);

    //问答列表
    @GET("wenda/list/{page}/json")
    Call<Data<ArticleListData>> getWendaData(@Path("page") int page);

    //公众号列表
    @GET("wxarticle/chapters/json")
    Observable<Data<List<Author>>> getWxAuthor();

    //查看某个公众号数据
    @GET("wxarticle/list/{id}/{page}/json")
    Observable<Data<ArticleListData>> getWxArticleData(@Path("id")int id,@Path("page") int page);

    //项目列表
    @GET("project/tree/json")
    Observable<Data<List<Author>>> getKnowleageAuthor();

    //项目各类别数据
    @GET("project/list/{page}/json")
    Observable<Data<ArticleListData>> getKnArticleData(@Path("page") int page,@Query("cid") int cid);

    //体系
    @GET("tree/json")
    Observable<Data<List<Tab>>> getProjectDate();

    //体系各类别数据
    @GET("rticle/list/{page}/json")
    Observable<Data<ArticleListData>> getProjectItemData(@Path("page") int page,@Query("cid") int cid);

    //体系各类别各item的数据
    @GET("article/list/{page}/json")
    Observable<Data<ArticleListData>> getProjectData(@Path("page") int page,@Query("cid") int cid);

    //导航
    @GET("navi/json")
    Observable<Data<List<NaviTab>>> getNaviData();
/**
 * 12.15
 * 1.学习##
 *  a.字符串分隔：转义字符需要加{}，才能转义：https://blog.csdn.net/sinat_29962405/article/details/50868524
 *  b.卡片视图
 *  c.图片填充两种布局：内填充才使外填充？https://blog.csdn.net/qq_38900441/article/details/83420480
 *  d.app全屏：设置theme——https://developer.aliyun.com/article/60427
 */
}
