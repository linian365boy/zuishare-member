package top.zuishare.dto;

import java.util.List;

/**
 * @author niange
 * @ClassName: PageDto
 * @desp:
 * @date: 2017/12/23 下午11:43
 * @since JDK 1.7
 */
public class PageDto<T> {
    /**
     * 分页的数据list
     */
    private List<T> data;
    /**
     * 总共数据数
     */
    private long totalNum;
    /**
     *总共分页数
     */
    private long totalPageNo;
    /**
     * 每页展示条数
     */
    private int pageSize = 10;
    /**
     * 当前页码
     */
    private int currentPageNo = 1;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public long getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(long totalNum) {
        this.totalNum = totalNum;
        totalPageNo = (totalNum%pageSize==0)?(totalNum/pageSize):(totalNum/pageSize)+1;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalPageNo() {
        return totalPageNo;
    }

    public void setTotalPageNo(long totalPageNo) {
        this.totalPageNo = totalPageNo;
    }

    public int getCurrentPageNo() {
        return currentPageNo;
    }

    public void setCurrentPageNo(int currentPageNo) {
        if(currentPageNo > totalPageNo){
            this.currentPageNo = 1;
        }else {
            this.currentPageNo = currentPageNo;
        }
    }

}
