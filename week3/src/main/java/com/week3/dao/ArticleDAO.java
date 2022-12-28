package com.week3.dao;

import com.week3.vo.ArticleVO;
import com.week3.vo.SearchVO;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleDAO {

	// field injection is not recommended?>
	@Autowired
	SqlSession sqlSession;

	public ArticleVO selectOneArticle(int articleId) {
		return sqlSession.selectOne("mapper.article.selectArticle",articleId);
	}

	public List selectArticles(SearchVO searchVO) {
		return sqlSession.selectList("mapper.article.selectArticles",searchVO);
	}

	public int countArticles(SearchVO searchVO) {
		return sqlSession.selectOne("mapper.article.countArticles", searchVO);
	}

	public int insertArticle(ArticleVO article){
		return sqlSession.insert("mapper.article.insertArticle", article);
	}

	public int deleteArticle(int articleId){
		return sqlSession.update("mapper.article.deleteArticle", articleId);
	}

	public int updateArticle(ArticleVO article){
		return sqlSession.update("mapper.article.updateArticle", article);
	}

	private void increaseViews ( int articleId) {
		sqlSession.update("mapper.article.increaseViews", articleId);

	}
}
