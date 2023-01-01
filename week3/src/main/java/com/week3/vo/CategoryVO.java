package com.week3.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryVO {
/*
    id   `article_category_id` INT(10) NOT NULL AUTO_INCREMENT  - 카테고리 번호
	name `name` VARCHAR(10) NOT NULL                            - 카테고리 이름
*/

    /**
     * 카테고리 번호
     */
    private int id;

    /**
     * 카테고리 이름
     */
    private String name;

    @Builder
    public CategoryVO (int id, String name) {
        this.id = id;
        this.name = name;
    }
}
