package com.week3.service;

import com.week3.dao.ArticleDAO;
import com.week3.dao.CategoryDAO;
import com.week3.dao.FileDAO;
import com.week3.dto.ArticleDetailDTO;
import com.week3.dto.ArticleUpdateDTO;
import com.week3.vo.ArticleVO;
import com.week3.vo.CategoryVO;
import com.week3.vo.FileVO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ArticleInsertService {

	/**
	 * FILE_UPLOAD_DIRECTORY	- 파일 저장 경로
	 * ARTICLE_DAO				- 게시글 테이블 접근을 위한 @Repository
	 * CATEGORY_DAO				- 카테고리 테이블 접근을 위한 @Repository
	 * FILE_DAO					- 파일 테이블 접근을 위한 @Repository
	 */
	@Value("${file.upload.directory}")
	private String FILE_UPLOAD_DIRECTORY;
	private final ArticleDAO ARTICLE_DAO;
	private final CategoryDAO CATEGORY_DAO;
	private final FileDAO FILE_DAO;

	/**
	 * 게시글 및 파일 유효성 검증 후 DB insert
	 * @param articleUpdateDTO ArticleInputForm 페이지 사용자 입력값 모음
	 * @return 결과????
	 */
	@Transactional(rollbackFor = {RuntimeException.class, Exception.class})
	public int service(ArticleUpdateDTO articleUpdateDTO) throws IOException {
		ArticleVO articleVO = createArticleVO(articleUpdateDTO);
		int result = ARTICLE_DAO.insertArticle(articleVO);
		int articleId = getInsertedArticleId(result, articleVO);
		result = uploadFiles(articleUpdateDTO, articleId);
		updateArticleFileStatus(articleId);
		return result;
	}

	private void updateArticleFileStatus(int articleId) {
		int fileStatus = getArticleFileStatus(articleId);
		HashMap<String,Integer> articleFileStatus = new HashMap<>();
		articleFileStatus.put("articleId", articleId);
		articleFileStatus.put("fileStatus", fileStatus);
		ARTICLE_DAO.updateFileStatus(articleFileStatus);
	}

	private int getArticleFileStatus(int articleId) {
		int fileStatus = FILE_DAO.countArticleFiles(articleId);
		if (fileStatus > 0) {
			fileStatus = 1;
		}
		return fileStatus;
	}

	private int getInsertedArticleId(int result, ArticleVO articleVO) {
		if (result == 1){
			return articleVO.getId();
		}
		result = -1;
		return result;
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

	private int uploadFiles(ArticleUpdateDTO articleUpdateDTO, int articleId) throws IOException {
		if (articleId < 1) {
			throw new RuntimeException("invalid articleId");
		}
		int result = 0;
		List<MultipartFile> fileList = articleUpdateDTO.getFileList();
		for (MultipartFile file : fileList) {
			if (file.isEmpty()) {
				continue;
			}
			FileVO fileVO = createFileVO(file, articleId);
			saveFile(file, fileVO);
			result = FILE_DAO.insertFile(fileVO);
		}
		return result;
	}

	private FileVO createFileVO(MultipartFile file, int articleId){
		String fullFileName = Normalizer.normalize(file.getOriginalFilename(), Form.NFC);
		String originalName = fullFileName.substring(0,fullFileName.lastIndexOf("."));
		String extension = fullFileName.substring(fullFileName.lastIndexOf("."));
		String saveName = UUID.randomUUID().toString().replaceAll("-","");
		String path = FILE_UPLOAD_DIRECTORY + "\\" + articleId + "\\" + saveName;

		FileVO fileVO = FileVO.builder()
			.articleId(articleId)
			.saveName(saveName)
			.originalName(originalName)
			.path(path)
			.extension(extension)
			.build();
		return fileVO;
	}

	private void saveFile(MultipartFile file, FileVO fileVO) throws IOException  {
		String path = fileVO.getPath();
		createDirectoryFolder(path);
		File targetFile = new File(path);
		file.transferTo(targetFile);
	}

	private void createDirectoryFolder(String path) throws IOException {
		String directory = path.substring(0,path.lastIndexOf("\\"));
		Path directoryPath = Paths.get(directory);
		if (!Files.isDirectory(directoryPath)) {
			Files.createDirectory(directoryPath);
		}
	}
}
