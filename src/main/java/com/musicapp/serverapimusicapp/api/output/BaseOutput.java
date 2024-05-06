package com.musicapp.serverapimusicapp.api.output;


import java.util.ArrayList;
import java.util.List;

public class BaseOutput<T> {
    private int page;
    private int totalPage;
    private List<T> ListResult = new ArrayList<>();

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getListResult() {
        return ListResult;
    }

    public void setListResult(List<T> listResult) {
        ListResult = listResult;
    }
}
