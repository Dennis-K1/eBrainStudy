package com.week3.service;

import com.week3.dao.ArticleDAO;
import com.week3.dao.CategoryDAO;
import com.week3.dto.ArticleDetailDTO;
import com.week3.dto.ArticleUpdateDTO;
import com.week3.vo.ArticleVO;
import com.week3.vo.CategoryVO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleInsertService {

	/**
	 * ARTICLE_DAO		- 게시글 테이블 접근을 위한 @Repository
	 * CATEGORY_DAO		- 카테고리 테이블 접근을 위한 @Repository
	 */
	private final ArticleDAO ARTICLE_DAO;
	private final CategoryDAO CATEGORY_DAO;

	/**
	 * 게시글 및 파일 유효성 검증 후 DB insert
	 * @param articleUpdateDTO ArticleInputForm 페이지 사용자 입력값 모음
	 * @return 결과????
	 */
	public int service(ArticleUpdateDTO articleUpdateDTO) {
//		validateArticleInputValues(articleUpdateDTO);
		ArticleVO articleVO = createArticleVO(articleUpdateDTO);
		int insertedArticleId = ARTICLE_DAO.insertArticle(articleVO);
		if (insertedArticleId < 1){
		}
		return insertedArticleId;
	}

	/**
	 * 유효성 검증 후 게시글 객체 생성
	 * @param articleUpdateDTO ArticleInputForm 페이지 사용자 입력값 모음
	 * @return 게시글 객체
	 */
	private ArticleVO createArticleVO(ArticleUpdateDTO articleUpdateDTO) {
		ArticleVO articleVO = ArticleVO.builder()
			.categoryId(articleUpdateDTO.getCategoryId())
			.title(articleUpdateDTO.getTitle())
			.content(articleUpdateDTO.getContent())
			.writer(articleUpdateDTO.getWriter())
			.password(articleUpdateDTO.getPassword())
			.build();
		return articleVO;
	}

	/**
	 * 사용자 입력값 서버 유효성 검증
	 * @param articleDetailDTO ArticleInputForm 페이지 사용자 입력값 모음
	 */

	private Boolean validateArticleInputValues(ArticleDetailDTO articleDetailDTO) {
		return true;
	}
	/**
	 * 검색창 카테고리 옵션 목록 반환
	 * @return 검색창 카테고리 옵션 목록
	 */
	public List<CategoryVO> getCategoryList() {
		return CATEGORY_DAO.selectCategories();
	}


}