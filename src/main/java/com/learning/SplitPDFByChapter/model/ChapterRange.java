package com.learning.SplitPDFByChapter.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChapterRange {

    private String title;
    private int startPage;
    private int endPage;

    public ChapterRange(int endPage, int startPage, String title) {
        this.endPage = endPage;
        this.startPage = startPage;
        this.title = title;
    }

}
