package com.jjh.blueberry.dto;

public class PagingDto {

	private int totalCount;// 전체글 개수
	private int totalPage;// 전체 페이지 수
	private int countList = 5;// 한 페이지에 출력될 게시물 수
	private int countPage = 5;// 화면에 보여질 페이지 수
	private int curPage;// 현재 페이지

	private int startPage;// 페이징 시작 페이지 ex)'1' 2 3 4 5
	private int endPage;// 페이징 끝 페이지 ex) 1 2 3 4 '5'
	
	private int prevPage;//'이전'클릭시 페이지
	private int nextPage;//'다음'클릭시 페이지
	
	public PagingDto(int curPage) {
		this.curPage = curPage;
	}

	public int getTotalCount() {
		return this.totalCount;
	}
	//글:23개일때 -> 23/5+1=5페이지
	//글:20개일때 -> 20/5=4페이지
	public int getTotalPage() {
		this.totalPage = this.totalCount / this.countList;
		if((this.totalCount % this.countList) != 0){
			this.totalPage++;
		}
		return this.totalPage;
	}

	public int getCountList() {
		return this.countList;
	}

	public int getCountPage() {
		return this.countPage;
	}

	public int getCurPage() {
		return this.curPage;
	}
	//((7-1)/5)*5+1=6
	//((5-1)/5)*5+1=1
	public int getStartPage() {
		return ((this.curPage - 1) / this.countPage) * this.countPage + 1;
	}
	//6+5-1=10
	public int getEndPage() {
		this.endPage = getStartPage() + this.countPage -1;
		return this.endPage > getTotalPage() ? getTotalPage() : this.endPage;
	}

	public int getPrevPage() {
		if(getStartPage() == 1){
			return getStartPage();
		} else {
			return getStartPage() - 1;			
		}
	}

	public int getNextPage() {
		if(getEndPage() < getTotalPage()){
			return getEndPage() + 1;			
		} else{
			return getEndPage();
		}
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public void setCountList(int countList) {
		this.countList = countList;
	}

	public void setCountPage(int countPage) {
		this.countPage = countPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public void setPrevPage(int prevPage) {
		this.prevPage = prevPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

}
