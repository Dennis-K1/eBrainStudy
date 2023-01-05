package com.week4.service;

import com.week4.repository.ArticleRepository;
import com.week4.repository.CategoryRepository;
import com.week4.repository.CommentRepository;
import com.week4.repository.FileRepository;
import com.week4.vo.ArticleVO;
import com.week4.vo.BoardVO;
import com.week4.vo.CategoryVO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {

	private final ArticleRepository articleRepository;
	private final CategoryRepository categoryRepository;
	private final CommentRepository commentRepository;
	private final FileRepository fileRepository;


	/**
	 * 대상 게시글 객체 반환
	 * @param articleId 대상 게시글 번호
	 * @return 대상 게시글 객체
	 */
	public ArticleVO getArticle(int articleId) {
		return articleRepository.selectArticle(articleId);
	}

	public List<BoardVO> getBoardVO() {
		return articleRepository.getBoardVO();
	}

	public List<CategoryVO> getBoardCategories() {
		return articleRepository.getBoardCategoryList();
	}

}
