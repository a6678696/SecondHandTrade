package com.ledao.util;

/**
 * 分页工具类
 *
 * @author LeDao
 * @company
 * @create 2022-01-04 12:32
 */
public class PageUtil {

    /**
     * 生成分页代码
     *
     * @param targetUrl   目标地址
     * @param totalNum    总记录数
     * @param currentPage 当前页
     * @param pageSize    每页数量
     * @param param       每页大小
     * @return
     */
    public static String genPagination1(String targetUrl, int totalNum, int currentPage, int pageSize, String param) {
        //当前页前后页的显示数量
        int pageTotal = 2;
        long totalPage = totalNum % pageSize == 0 ? totalNum / pageSize : totalNum / pageSize + 1;
        if (totalPage == 0) {
            return "<span style='color:red'>无数据</span>";
        } else {
            StringBuilder pageCode = new StringBuilder();
            //当前不是第一页时,首页链接可点击
            if (currentPage != 1) {
                pageCode.append("<li class='page-item'><a class='page-link' href='" + targetUrl + "?page=1" + param + "'>首页</a></li>");
            } else {//不可点击
                pageCode.append("<li class='page-item disabled'><a class='page-link' href='#'>首页</a></li>");
            }
            //当前不是第一页,上一页链接可点击
            if (currentPage > 1) {
                pageCode.append("<li class='page-item'><a class='page-link' href='" + targetUrl + "?page=" + (currentPage - 1) + param + "'>«</a></li>");
            } else {
                pageCode.append("<li class='page-item disabled'><a class='page-link' href='#'>«</a></li>");
            }
            for (int i = currentPage - pageTotal; i <= currentPage + pageTotal; i++) {
                if (i < 1 || i > totalPage) {
                    continue;
                }
                if (i == currentPage) {
                    pageCode.append("<li class='page-item active'><a class='page-link'>" + i + "</a></li>");
                } else {
                    pageCode.append("<li class='page-item'><a class='page-link' href='" + targetUrl + "?page=" + i + param + "'>" + i + "</a></li>");
                }
            }
            //当前不是最后一页,下一页链接可点击
            if (currentPage < totalPage) {
                pageCode.append("<a class='page-link' href='" + targetUrl + "?page=" + (currentPage + 1) + param + "'>»</a>");
            } else {
                pageCode.append("<li class='page-item disabled'><a class='page-link'>»</a></li>");
            }
            //当前不是最后一页时,尾页链接可点击
            if (totalPage != currentPage) {
                pageCode.append("<li class='page-item'><a class='page-link' href='" + targetUrl + "?page=" + totalPage + param + "'>尾页</a></li>");
            } else {//不可点击
                pageCode.append("<li class='page-item disabled'><a class='page-link' href='#'>尾页</a></li>");
            }
            return pageCode.toString();
        }
    }
}
