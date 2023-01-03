package com.week4.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FileVO {
/*
    id              `file_id` INT(10) NOT NULL AUTO_INCREMENT  - 파일 번호
	articleId       `article_id` INT(10) NOT NULL              - 파일이 첨부된 게시글 번호
	saveName        `save_name` VARCHAR(1000) NOT NULL         - 저장 파일명
	originalName    `original_name` VARCHAR(1000) NOT NULL     - 원본 파일명
	path            `path` VARCHAR(255) NOT NULL               - 파일 경로
	extension       `extension` VARCHAR(10) NOT NULL           - 확장자
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
     * 저장 파일명
     */
    private String saveName;

    /**
     * 원본 파일명
     */
    private String originalName;

    /**
     * 파일 경로
     */
    private String path;

    /**
     * 확장자
     */
    private String extension;

}
