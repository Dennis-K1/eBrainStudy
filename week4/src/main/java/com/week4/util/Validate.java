package com.week4.util;

import com.week4.vo.BoardVO;
import com.week4.vo.BoardVO.ArticleVO;
import java.util.Objects;

public class Validate {

	/**
	 * 게시글 입력값 서버측 유효성 검증
	 *
	 * @param articleVO 유저 입력값이 담긴 객체
	 * @return 오류 있을 시 -1 반환
	 */
	public static int validateArticleInput(ArticleVO articleVO){
		int categoryId = articleVO.getCategoryId();
		String writer = articleVO.getWriter();
		String title = articleVO.getTitle();
		String password = articleVO.getPassword();
		String content = articleVO.getContent();

		if (Objects.equals(null,categoryId) || categoryId < 1 || categoryId > 3) {
			return -1;
		}
		if (writer.length() < 2 || writer.length() > 4) {
			return -1;
		}
		if (password.length() < 4 || password.length() > 16) {
			return -1;
		} else if (!password.matches("/([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~])|([!,@,#,$,%,^,&,*,?,_,~].*[a-zA-Z0-9])/")) {
			return -1;
		}
		if (title.length() < 2 || title.length() > 4) {
			return -1;
		}
		if (content.length() < 2 || content.length() > 4) {
			return -1;
		}
		return 1;
	}

	/**
	 * 인자가 공백일 시 null 반환
	 */
	public static Object setEmptyNull(Object parameter) {
		if ("".equals(parameter)) {
			return null;
		}
		return parameter;
	}

	/**
	 * 유저 검색값 유효성 검증 및 정정하여 반환
	 * @param searchVO 유저 검색값 객체
	 * @return 유효성 검증 후 수정된 검색값 객체
	 */
	public static BoardVO.SearchVO validateSearchVO(BoardVO.SearchVO searchVO) {
		validateEmptyPageNumber(searchVO);
		searchVO.setFirstArticleIndex(searchVO.getPageNumber());
		setEmptySearchValuesNull(searchVO);
		return searchVO;
	}

	/**
	 * 검색 옵션값 (startDate, endDate, Category, keyword) 공백일 경우 null 지정
	 * @param searchVO 유저 검색값 객체
	 */
	private static void setEmptySearchValuesNull(BoardVO.SearchVO searchVO) {
		searchVO.setCategoryId((Integer) setEmptyNull(searchVO.getCategoryId()));
		searchVO.setKeyword((String) setEmptyNull(searchVO.getKeyword()));
		searchVO.setStartDate((String) setEmptyNull(searchVO.getStartDate()));
		searchVO.setEndDate((String) setEmptyNull(searchVO.getEndDate()));
	}

	/**
	 * 검색 페이지 번호 유효성 검증 및 수정
	 * null 인 경우 1 반환, 아닌 경우 매핑된 숫자 반환
	 * @param searchVO 유저 검색값 객체
	 */
	private static void validateEmptyPageNumber(BoardVO.SearchVO searchVO) {
		int pageNumber = 1;
		Integer searchedPageNumber = searchVO.getPageNumber();
		if (searchedPageNumber != 0) {
			pageNumber = searchedPageNumber;
		}
		searchVO.setPageNumber(pageNumber);
	}

}
