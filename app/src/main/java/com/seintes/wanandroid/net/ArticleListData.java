package com.seintes.wanandroid.net;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 单页的数据
 * @Author xmhaz
 * @Time 2023/12/1 15:29
 */
public class ArticleListData {
    private int curPage;
    private List<ArticleDatas> datas = new ArrayList<>();
    private int offset;
    private boolean over;
    private int pageCount;
    private int size;
    private int total;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public List<ArticleDatas> getDatas() {
        return datas;
    }

    public void setDatas(List<ArticleDatas> datas) {
        this.datas = datas;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
