package com.ledao.entity;

/**
 * 分页实体类
 *
 * @author LeDao
 * @company
 * @create 2022-01-04 12:33
 */
public class PageBean {

    /**
     * 第几页
     */
    private int page;
    /**
     * 每页记录数
     */
    private int pageSize;
    /**
     * 起始页
     */
    private int start;


    public PageBean(int page, int pageSize) {
        super();
        this.page = page;
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStart() {
        return (page - 1) * pageSize;
    }
}
