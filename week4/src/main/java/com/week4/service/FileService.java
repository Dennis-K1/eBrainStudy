package com.week4.service;

import com.week4.repository.ArticleRepository;
import com.week4.repository.FileRepository;
import com.week4.vo.FileVO;
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
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileService {

	/**
	 * 파일 저장 경로
	 */
	@Value("${file.upload.directory}")
	private String fileUploadDirectory;

	/**
	 * 파일 테이블 접근을 위한 @Repository
	 */
	private final FileRepository fileRepository;

	/**
	 * 게시글 테이블 접근을 위한 @Repositiry
	 */
	private final ArticleRepository articleRepository;

	/**
	 * 대상 파일 삭제
	 */
	public int deleteFile(Integer deletedFileId){
		FileVO fileVO = fileRepository.getFile(deletedFileId);
		String filePath = fileVO.getDirectory() + '\\' + fileVO.getSaveName();
		try {
			Files.deleteIfExists(Paths.get(filePath));
			int result = fileRepository.deleteFile(fileVO.getId());
			updateArticleFileStatus(fileVO.getArticleId(), result);
			return result;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 파일 경로에서 대상 파일 반환
	 * @param fileVO 대상 파일명 및 디렉토리 정보
	 * @return 파일
	 */
	public File getFile(FileVO fileVO) {
		String filePath = fileVO.getDirectory() + '/' + fileVO.getSaveName();
		File file = new File(filePath);
		return file;
	}

	/**
	 * 대상 게시물 파일 정보 조회
	 * @param articleId 대상 게시물 번호
	 * @return 파일 리스트
	 */
	public List<FileVO> getArticleFileList(int articleId) {
		List<FileVO> fileList = fileRepository.getArticleFileList(articleId);
		return fileList;
	}

	/**
	 * 대상 게시물 파일 첨부 여부 업데이트
	 * @param articleId 대상 게시물 번호
	 */
	private void updateArticleFileStatus(int articleId, int fileInsertResult) {
		if (fileInsertResult == 1){
			int fileStatus = getArticleFileStatus(articleId);
			HashMap<String,Integer> articleFileStatus = new HashMap<>();
			articleFileStatus.put("articleId", articleId);
			articleFileStatus.put("fileStatus", fileStatus);
			articleRepository.updateFileStatus(articleFileStatus);
		}
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
	 * 게시글 파일 저장 (서버/DB)
	 * @param fileList 파일이 담긴 리스트
	 * @param articleId 대상 게시글 번호
	 * @return 저장 결과
	 * @throws IOException
	 */
	public int uploadFiles(List<MultipartFile> fileList, int articleId) throws IOException {
		int result = 0;
		for (MultipartFile file : fileList) {
			if (file.isEmpty()) {
				continue;
			}
			FileVO fileVO = createFileVO(file, articleId);
			saveFile(file, fileVO);
			result = fileRepository.insertFile(fileVO);
			
		}
		updateArticleFileStatus(articleId, result);
		return result;
	}

	/**
	 * DB에 정보 저장을 위한 FileVO 객체 생성
	 * @param file 파일 객체
	 * @param articleId 첨부 게시글 번호
	 * @return FileVO 객체
	 */
	private FileVO createFileVO(MultipartFile file, int articleId){
		String originalName = Normalizer.normalize(file.getOriginalFilename(), Form.NFC);
		String extension = originalName.substring(originalName.lastIndexOf("."));
		String saveName = UUID.randomUUID().toString().replaceAll("-","") + extension;
		String directory = fileUploadDirectory + "\\" + articleId;

		FileVO fileVO = FileVO.builder()
			.articleId(articleId)
			.saveName(saveName)
			.originalName(originalName)
			.directory(directory)
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
		createDirectoryFolder(fileVO.getDirectory());
		String filePath = fileVO.getDirectory() + "//" + fileVO.getSaveName();
		File targetFile = new File(filePath);
		file.transferTo(targetFile);
	}

	/**
	 * 첨부 파일 저장시 게시글 번호 폴더에 저장, 폴더 존재 확인 및 생성
	 * @param directory 파일 저장 경로
	 * @throws IOException
	 */
	private void createDirectoryFolder(String directory) throws IOException {
		Path folder = Paths.get(directory);
		if (!Files.isDirectory(folder)) {
			Files.createDirectory(folder);
		}
	}
}
