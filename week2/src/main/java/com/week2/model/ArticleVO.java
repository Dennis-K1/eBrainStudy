package com.week2.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ArticleVO {
    /**
     * article_category_id --> category_id ??
     */
/*
    id                `article_id` INT(10) NOT NULL AUTO_INCREMENT                  - 게시글 번호
	categoryId        `article_category_id` INT(10) NOT NULL                        - 게시글 카테고리 번호
	title             `title` VARCHAR(100) NOT NULL                                 - 게시글 제목
	content           `content` VARCHAR(2000) NOT NULL                              - 게시글 내용
	views             `views` INT(10) NOT NULL DEFAULT '0'                          - 게시글 조회수
	writer            `writer` VARCHAR(5) NOT NULL                                  - 게시글 작성자
	password          `password` VARCHAR(16) NOT NULL                               - 게시글 비밀번호
	dateCreated       `date_created` DATETIME NOT NULL DEFAULT current_timestamp()  - 게시글 등록 일시
	lastUpdated       `last_updated` DATETIME NULL DEFAULT NULL                     - 게시글 수정 일시
	fileAttached      `file_attached` TINYINT(1) NOT NULL DEFAULT '0'               - 게시글 파일 첨부 여부
	articleDeleted    `article_deleted` TINYINT(1) NULL DEFAULT '0'                 - 게시글 삭제 여부
*/


    /**
     * 게시글 번호
     */
    private int id;

    /**
     * 게시글 카테고리 번호
     */
    private int categoryId;

    /**
     * 게시글 제목
     */
    private String title;

    /**
     * 게시글 내용
     */
    private String content;

    /**
     * 게시글 조회수
     */
    private int views;

    /**
     * 게시글 작성자
     */
    private String writer;

    /**
     * 게시글 비밀번호
     */
    private String password;

    /**
     * 게시글 등록 일시
     */
    private String dateCreated;

    /**
     * 게시글 수정 일시
     */
    private String lastUpdated;

    /**
     * 게시글 파일 첨부 여부
     */
    private int fileAttached;

    /**
     * 게시글 삭제 여부
     */
    private int articleDeleted;


    /**
     * 기본 생성자
     */
    public ArticleVO() {

    }
}