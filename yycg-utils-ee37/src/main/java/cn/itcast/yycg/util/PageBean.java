/***************************************
 * 
 * 分页查询参数类
 * 
 * **************************************/
package cn.itcast.yycg.util;

public class PageBean {

	// 当前页码
	private int pageNumber;

	// 每页显示个数
	private int pageSize = 30;

	// 总记录数
	private int totalRecord;

	// 总页数
	private int totalPage;
	
	// 开始坐标（开始索引）
	private int startIndex;
	

	public PageBean(int pageNumber, int pageSize, int totalRecord) {
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.totalRecord = totalRecord;
		
		//开始索引
		this.startIndex = (this.pageNumber - 1) * this.pageSize;
		
		
		//总分页
		this.totalPage = (this.totalRecord + this.pageSize - 1) / this.pageSize;
		
		
	}


	public int getPageNumber() {
		return pageNumber;
	}


	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}


	public int getPageSize() {
		return pageSize;
	}


	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	public int getTotalRecord() {
		return totalRecord;
	}


	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}


	public int getTotalPage() {
		return totalPage;
	}


	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}


	public int getStartIndex() {
		return startIndex;
	}


	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

			
	
}
