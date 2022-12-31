package com.week3.service;

import com.week3.repository.ArticleRepository;
import com.week3.repository.CategoryRepository;
import com.week3.repository.FileRepository;
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

/**
 * 게시글 등록을 위한 @Service
 */
@Service
@RequiredArgsConstructor
public class ArticleInsertService {

	/**
	 * fileUploadDirectory	- 파일 저장 경로
	 * articleRepository				- 게시글 테이블 접근을 위한 @Repository
	 * categoryRepository				- 카테고리 테이블 접근을 위한 @Repository
	 * fileRepository					- 파일 테이블 접근을 위한 @Repository
	 */
	@Value("${file.upload.directory}")
	private String fileUploadDirectory;
	private final ArticleRepository articleRepository;
	private final CategoryRepository categoryRepository;
	private final FileRepository fileRepository;

	/**
	 * 게시글 및 파일 유효성 검증 후 DB insert
	 * @param articleUpdateDTO ArticleInputForm 페이지 사용자 입력값 모음
	 * @return DB 트랜잭션 결과 (1: 성공, 0: 실패)
	 */
	@Transactional(rollbackFor = {RuntimeException.class, Exception.class})
	public int service(ArticleUpdateDTO articleUpdateDTO) throws IOException {
		ArticleVO articleVO = createArticleVO(articleUpdateDTO);
		int result = articleRepository.insertArticle(articleVO);
		int articleId = getInsertedArticleId(result, articleVO);
		result = uploadFiles(articleUpdateDTO, articleId);
		updateArticleFileStatus(articleId);
		return result;
	}

	/**
	 * 대상 게시물 파일 첨부 여부 업데이트
	 * @param articleId 대상 게시물 번호
	 */
	private void updateArticleFileStatus(int articleId) {
		int fileStatus = getArticleFileStatus(articleId);
		HashMap<String,Integer> articleFileStatus = new HashMap<>();
		articleFileStatus.put("articleId", articleId);
		articleFileStatus.put("fileStatus", fileStatus);
		articleRepository.updateFileStatus(articleFileStatus);
	}

	/**
	 * 대상 게시물 파일 첨부 갯수를 통한 첨부 상태 확인
	 * @param articleId 대상 게시물 번호
	 * @return 파일 첨부 상태 (미첨부 : 0, 첨부 : 1)
	 */
	private int getArticleFileStatus(int articleId) {
		int fileStatus = fileRepository.countArticleFiles(articleId);
		if (fileStatus > 0) {
			fileStatus = 1;
		}
		return fileStatus;
	}

	/**
	 * ArticleInsertService 의 동일 Transaction 내에서 insert 된 게시글의 게시글 번호
	 * @param result insert 결과
	 * @param articleVO 게시글 객체
	 * @return insert 성공되었을 시 게시글 번호 반환, 그 외 실패값 (-1) 반환
	 */
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
		return categoryRepository.selectCategories();
	}

	/**
	 * 게시글 파일 저장 (서버/DB)
	 * @param articleUpdateDTO 파일 정보가 담긴 게시글 객체
	 * @param articleId 파일 첨부 게시글 번호
	 * @return 저장 결과
	 * @throws IOException
	 */
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
			result = fileRepository.insertFile(fileVO);
		}
		return result;
	}

	/**
	 * DB에 정보 저장을 위한 FileVO 객체 생성
	 * @param file 파일 객체
	 * @param articleId 첨부 게시글 번호
	 * @return FileVO 객체
	 */
	private FileVO createFileVO(MultipartFile file, int articleId){
		String fullFileName = Normalizer.normalize(file.getOriginalFilename(), Form.NFC);
		String originalName = fullFileName.substring(0,fullFileName.lastIndexOf("."));
		String extension = fullFileName.substring(fullFileName.lastIndexOf("."));
		String saveName = UUID.randomUUID().toString().replaceAll("-","");
		String path = fileUploadDirectory + "\\" + articleId + "\\" + saveName;

		FileVO fileVO = FileVO.builder()
			.articleId(articleId)
			.saveName(saveName)
			.originalName(originalName)
			.path(path)
			.extension(extension)
			.build();
		return fileVO;
	}

	/**
	 * 서버에 물리 파일 저장
	 * @param file 파일 객체
	 * @param fileVO 파일 정보가 담긴 VO 객체
	 * @throws IOException
	 */
	private void saveFile(MultipartFile file, FileVO fileVO) throws IOException  {
		String path = fileVO.getPath();
		createDirectoryFolder(path);
		File targetFile = new File(path);
		file.transferTo(targetFile);
	}

	/**
	 * 첨부 파일 저장시 게시글 번호 폴더에 저장, 폴더 존재 확인 및 생성
	 * @param path 파일 저장 경로
	 * @throws IOException
	 */
	private void createDirectoryFolder(String path) throws IOException {
		String directory = path.substring(0,path.lastIndexOf("\\"));
		Path directoryPath = Paths.get(directory);
		if (!Files.isDirectory(directoryPath)) {
			Files.createDirectory(directoryPath);
		}
	}
}
