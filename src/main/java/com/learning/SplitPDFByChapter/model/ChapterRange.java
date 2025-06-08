package com.learning.SplitPDFByChapter.model;

public class ChapterRange {

    private String title;
    private int startPage;
    private int endPage;
    
    public ChapterRange() {
    }

    public ChapterRange(int endPage, int startPage, String title) {
        this.endPage = endPage;
        this.startPage = startPage;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getStartPage() {
        return startPage;
    }
    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }
    public int getEndPage() {
        return endPage;
    }
    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

}
