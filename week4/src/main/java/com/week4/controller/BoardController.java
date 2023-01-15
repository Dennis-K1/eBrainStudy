package com.week4.controller;

import com.week4.service.ArticleService;
import com.week4.service.FileService;
import com.week4.util.Validate;
import com.week4.vo.BoardVO;
import com.week4.vo.BoardVO.ArticleVO;
import com.week4.vo.BoardVO.CommentVO;
import com.week4.vo.BoardVO.SearchVO;
import com.week4.vo.FileVO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@CrossOrigin(origins = "http://localhost:3000", exposedHeaders = {"Content-Disposition"})
@RequestMapping("api/v1/")
@RequiredArgsConstructor
public class BoardController {

	/**
	 * 게시글 도메인 (게시글, 카테고리, 댓글) @Service
	 */
	private final ArticleService articleService;

	/**
	 * 파일 @Service
	 */
	private final FileService fileService;

	/**
	 * 프론트에서 인증하고자 하는 게시글 번호와 유저가 입력한 비밀번호를 전달 받아 해당 게시글 비밀번호 조회하여 대조
	 *
	 * @param articleVO 대상 게시글 번호와 유저 입력 비밀번호가 담긴 객체
	 */
	@PostMapping("articles/password")
	public Boolean isArticlePasswordCorrect(@RequestBody BoardVO.ArticleVO articleVO) {
		return articleService.isArticlePasswordCorrect(articleVO);
	}


	/**
	 * 메소드명 ???????????????????? 게시판 정보 전체 조회
	 *
	 * @return 게시글 목록 (댓글 및 카테고리 정보 포함), 카테고리 목록
	 */
	@GetMapping("articles")
	public BoardVO getBoardVO(SearchVO searchVO) {
		SearchVO validatedSearchVO = Validate.validateSearchVO(searchVO);
		BoardVO boardVO = BoardVO.builder()
			.articleList(articleService.getArticleList(validatedSearchVO))
			.categoryList(articleService.getCategoryList())
			.searchVO(validatedSearchVO)
			.numberOfArticles(articleService.getNumberOfArticles(validatedSearchVO))
			.build();
		return boardVO;
	}

	/**
	 * 프론트에서 multipart 타입의 게시글 정보 및 파일 정보를 받아 게시글 정보 DB 삽입후 게시글 번호 반환, 게시글 번호 이용하여 파일 저장 및 정보 DB 삽입
	 *
	 * @param articleVO multipart 타입 게시글 정보 및 파일
	 * @return 게시글 목록
	 */
	@PostMapping("articles")
	public String insertArticle(ArticleVO articleVO) throws IOException {
		int articleId = articleService.registerArticle(articleVO);
		if (articleId == -1){
			return "validationError";
		}
		List<MultipartFile> fileList = articleVO.getFileList();
		if (!Objects.equals(fileList, null)) {
			fileService.uploadFiles(fileList, articleId);
		}
		return "registerSuccess";
	}

	/**
	 * 게시글 조회
	 *
	 * @param articleId 대상 게시글 번호
	 * @return 게시글 정보 및 댓글 목록
	 */
	@GetMapping("articles/{articleId}")
	public BoardVO.ArticleVO getArticle(@PathVariable("articleId") int articleId) {
		BoardVO.ArticleVO articleVO = articleService.getArticle(articleId);
		articleVO.setFileInformationList(fileService.getArticleFileList(articleId));
		return articleVO;
	}

	/**
	 * 게시글 수정
	 *
	 * @param articleVO 수정 내용이 담긴 게시글 객체
	 * @return 수정된 게시글
	 */
	@PutMapping("articles")
	public String updateArticle(ArticleVO articleVO) throws IOException {
		articleVO.getId();
		int result = articleService.updateArticle(articleVO);
		List<Integer> deletedFileList = articleVO.getDeletedFileList();
		if (!Objects.equals(deletedFileList,null)){
			for (Integer deletedFileId : deletedFileList) {
				fileService.deleteFile(deletedFileId);
			}
		}
		List<MultipartFile> fileList = articleVO.getFileList();
		if (!Objects.equals(fileList, null)) {
			fileService.uploadFiles(fileList, articleVO.getId());
		}
		return "updateSuccess";
	}

	/**
	 * 대상 게시글 삭제
	 *
	 * @param articleVO 대상 게시글 번호 및 유효성 검사를 위한 정보가 담긴 게시글 객체
	 * @return 게시글 목록
	 */
	@DeleteMapping("articles")
	public int deleteArticle(@RequestBody ArticleVO articleVO) {
		return articleService.deleteArticle(articleVO);
	}

	/**
	 * 대상 게시글 댓글 등록(삽입)
	 *
	 * @param commentVO 대상 게시글 번호 및 댓글 내용
	 * @return 수행 결과
	 */
	@PostMapping("articles/comment")
	public int insertComment(@RequestBody CommentVO commentVO) {
		return articleService.registerComment(commentVO);
	}

	/**
	 * 파일 다운로드
	 *
	 * @param fileVO 다운 받을 파일 정보 객체
	 */
	@PostMapping("articles/file")
	public void getFile(@RequestBody FileVO fileVO, HttpServletResponse response) throws Exception {
		try {
			File file = fileService.getFile(fileVO);
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition",
				"attachment; filename=" + URLEncoder.encode(fileVO.getOriginalName(), "UTF-8") + ";");
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.setHeader("Content-Type", "application/octet-stream");
			response.setHeader("filename", URLEncoder.encode(fileVO.getOriginalName(), "UTF-8"));
			InputStream inputStream = new FileInputStream(file);
			assert inputStream != null;
			StreamUtils.copy(inputStream, response.getOutputStream());
			response.flushBuffer();
			inputStream.close();
		} catch (Exception e) {
			throw new Exception("download error");
		}
	}
}
