package com.week3.vo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ArticleVO {
/*
    id                `article_id` INT(10) NOT NULL AUTO_INCREMENT                  - 게시글 번호
	categoryId        `article_category_id` INT(10) NOT NULL                        - 게시글 카테고리 번호 (categoryVO로 대체)
	title             `title` VARCHAR(100) NOT NULL                                 - 게시글 제목
	content           `content` VARCHAR(2000) NOT NULL                              - 게시글 내용
	views             `views` INT(10) NOT NULL DEFAULT '0'                          - 게시글 조회수
	writer            `writer` VARCHAR(5) NOT NULL                                  - 게시글 작성자
	password          `password` VARCHAR(16) NOT NULL                               - 게시글 비밀번호
	dateCreated       `date_created` DATETIME NOT NULL DEFAULT current_timestamp()  - 게시글 등록 일시
	lastUpdated       `last_updated` DATETIME NULL DEFAULT NULL                     - 게시글 수정 일시
	fileAttached      `file_attached` TINYINT(1) NOT NULL DEFAULT '0'               - 게시글 파일 첨부 여부
	articleDeleted    `article_deleted` TINYINT(1) NULL DEFAULT '0'                 - 게시글 삭제 여부
	categoryVO        'article_category'  table                                     - 게시글 카테고리 테이블
*/

    /**
     * 게시글 번호
     */
    private int id;

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
     * 게시글 카테고리 테이블 객체
     */
    private CategoryVO categoryVO;

    /***
     * 게시글 등록 (insertArticle) : categoryId, title, content, writer, password
     * 게시글 수정 (updateArticle) : id, title, content, writer
     * @param id 게시글 번호
     * @param categoryVO 게시글 카테고리 번호 및 이름
     * @param title 게시글 제목
     * @param content 게시글 내용
     * @param writer 게시글 작성자
     * @param password 게시블 비밀번호
     * @param fileAttached 게시글 파일 첨부 여부
     */
    @Builder
    public ArticleVO(int id, CategoryVO categoryVO, String title, String content, String writer, String password, int fileAttached){
        this.id = id;
        this.categoryVO = categoryVO;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.password = password;
        this.fileAttached = fileAttached;
    }

    public int getCategoryId() {
        return this.categoryVO.getId();
    }
}
