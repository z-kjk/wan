package com.seintes.wanandroid.net;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Description 文章列表数据，链接https://www.wanandroid.com/article/list/0/json
 * @Author xmhaz
 * @Time 2023/12/1 14:58
 */
public class ArticleDatas {
    private String author;
    private String chapterName;
    private boolean collect;
    private int courseId;
    private boolean fresh;
    private int id;
    private String link;
    private String niceDate;
    //时间
    private long publishTime;   //时间戳
    private String datePublishTime; //日期格式字符串

    private String superChapterName;
    private String title;
    private int visible;

    //首页补充变量
    private String desc;
    private List<Tags> tags;
    private String shareUser;
    private boolean top = false;

    //项目新增
    private String envelopePic; //图片网址


    //日期转换
    public static String timeStamp2Date(long time) {
        String format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(time));
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        String date = timeStamp2Date(publishTime);
        this.publishTime = publishTime;
        setDatePublishTime(date);
    }

    public String getDatePublishTime() {
        return datePublishTime;
    }

    public void setDatePublishTime(String datePublishTime) {
        this.datePublishTime = datePublishTime;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public boolean isCollect() {
        return collect;
    }

    public void setCollect(boolean collect) {
        this.collect = collect;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public boolean isFresh() {
        return fresh;
    }

    public void setFresh(boolean fresh) {
        this.fresh = fresh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNiceDate() {
        return niceDate;
    }

    public void setNiceDate(String niceDate) {
        this.niceDate = niceDate;
    }


    public String getSuperChapterName() {
        return superChapterName;
    }

    public void setSuperChapterName(String superChapterName) {
        this.superChapterName = superChapterName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<Tags> getTags() {
        return tags;
    }

    public void setTags(List<Tags> tags) {
        this.tags = tags;
    }

    public String getShareUser() {
        return shareUser;
    }

    public void setShareUser(String shareUser) {
        this.shareUser = shareUser;
    }

    public boolean isTop() {
        return top;
    }

    public void setTop(boolean top) {
        this.top = top;
    }

    public String getEnvelopePic() {
        return envelopePic;
    }

    public void setEnvelopePic(String envelopePic) {
        this.envelopePic = envelopePic;
    }

    @Override
    public String toString() {
        return "ArticleDatas{" +
                "author='" + author + '\'' +
                ", chapterName='" + chapterName + '\'' +
                ", collect=" + collect +
                ", courseId=" + courseId +
                ", fresh=" + fresh +
                ", id=" + id +
                ", link='" + link + '\'' +
                ", niceDate='" + niceDate + '\'' +
                ", publishTime=" + publishTime +
                ", datePublishTime='" + datePublishTime + '\'' +
                ", superChapterName='" + superChapterName + '\'' +
                ", title='" + title + '\'' +
                ", visible=" + visible +
                ", desc='" + desc + '\'' +
                ", tags=" + tags +
                ", shareUser='" + shareUser + '\'' +
                ", top=" + top +
                ", envelopePic='" + envelopePic + '\'' +
                '}';
    }
}
