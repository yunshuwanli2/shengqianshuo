package yswl.priv.com.shengqianshopping.bean;

public class Page {
	  private int curPage = 1; // 当前页
	    private int pageSize = 20; // 每页多少行

	    public int getCurPage() {
	        return curPage;
	    }

	    public void setCurPage(int curPage) {
	        this.curPage = curPage;
	    }

	    public int getPageSize() {
	        return pageSize;
	    }

	    public void setPageSize(int pageSize) {
	        this.pageSize = pageSize;
	    }


}
