package com.week3.vo;

import lombok.Getter;

@Getter
public class CategoryVO {
/*
    id   `article_category_id` INT(10) NOT NULL AUTO_INCREMENT  - 카테고리 번호
	name `name` VARCHAR(10) NOT NULL                            - 카테고리 이름
*/

    /**
     * 카테고리 번호
     */
    private int id;  // Category 클래스 내부에 있으니 categoryId 가 아니라 id여도 괜찮을까?

    /**
     * 카테고리 이름
     */
    private String name;

}
