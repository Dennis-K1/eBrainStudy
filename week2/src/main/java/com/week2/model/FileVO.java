package com.week2.model;

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
}
