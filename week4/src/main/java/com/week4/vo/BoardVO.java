package com.week4.vo;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardVO {

	private ArticleVO articleVO;

	@Getter
	@NoArgsConstructor
	public static class CategoryVO{

		/**
		 * 카테고리 번호
		 */
		private int id;

		/**
		 * 카테고리 이름
		 */
		private String name;
	}
}


