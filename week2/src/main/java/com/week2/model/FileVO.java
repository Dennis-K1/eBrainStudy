package com.week2.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FileVO {
/*
    id         `file_id` INT(10) NOT NULL AUTO_INCREMENT  - 파일 번호
	articleId  `article_id` INT(10) NOT NULL              - 파일이 첨부된 게시글 번호
	name       `name` VARCHAR(1000) NOT NULL              - 파일명
	path       `path` VARCHAR(255) NOT NULL               - 파일 경로
 */

    /**
     * 파일 번호
     */
    private int id;

    /**
     * 파일이 첨부된 게시글 번호
     */
    private int articleId;

    /**
     * 파일명
     */
    private String name;

    /**
     * 파일 경로
     */
    private String path;


    /**
     * saveFolderName	    파일 저장 폴더
     * fileEncType			변환 형싱
     * fileMaxSize			파일 최대 크기
     */
    public static final String saveFolderName = "articleFiles";
    public static final String fileEncType = "utf-8";
    public static final int fileMaxSize=5*1024*1024;
    public static final String absolutePath = "E:\\coding\\eBrainStudy\\week2\\articleFiles";
    public static final String tempPath = "E:\\coding\\eBrainStudy\\week2\\articleFiles\\temp";
}
