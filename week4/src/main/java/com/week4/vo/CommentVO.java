package com.week4.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentVO {
/*
    id   `article_comment_id` INT(10) NOT NULL AUTO_INCREMENT                 - 댓글 번호
	articleId   `article_id` INT(10) NOT NULL                                 - 댓글이 등록된 게시글 번호
	content     `content` VARCHAR(500) NOT NULL                               - 댓글 내용
	dateCreated `date_created` DATETIME NOT NULL DEFAULT current_timestamp()  - 댓글 등록 일시
 */

    /**
     * 댓글 번호
     */
    private Integer id;

    /**
     * 댓글이 등록된 게시글 번호
     */
    private Integer articleId;

    /**
     * 댓글 내용
     */
    private String content;

    /**
     * 댓글 등록 일시
     */
    private String dateCreated;
}