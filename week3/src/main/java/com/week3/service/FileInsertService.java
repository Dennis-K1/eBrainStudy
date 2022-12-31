//package com.week3.service;
//
//import com.week3.dao.FileRepository;
//import com.week3.dto.ArticleUpdateDTO;
//import com.week3.vo.FileVO;
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.text.Normalizer;
//import java.text.Normalizer.Form;
//import java.util.List;
//import java.util.UUID;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//@Service
//@RequiredArgsConstructor
//public class FileInsertService {
//
//	/**
//	 * fileRepository		- 파일 테이블 접근을 위한 @Repository
//	 */
//	private final FileRepository fileRepository;
//
//	@Value("${file.upload.directory}")
//	private String FILE_UPLOAD_DIRECTORY;
//
//	public int service(ArticleUpdateDTO articleUpdateDTO, int articleId) throws IOException{
//		int result = 0;
//		List<MultipartFile> fileList = articleUpdateDTO.getFileList();
//		for (MultipartFile file : fileList) {
//			if (file.isEmpty()) {
//				continue;
//			}
//			FileVO fileVO = createFileVO(file, articleId);
//			saveFile(file, fileVO);
//			result = fileRepository.insertFile(fileVO);
//		}
//		return result;
//	}
//
//	public FileVO createFileVO(MultipartFile file, int articleId){
//
//		String fullFileName = Normalizer.normalize(file.getOriginalFilename(), Form.NFC);
//		String originalName = fullFileName.substring(0,fullFileName.lastIndexOf("."));
//		String extension = fullFileName.substring(fullFileName.lastIndexOf("."));
//		String saveName = UUID.randomUUID().toString().replaceAll("-","");
//		String path = FILE_UPLOAD_DIRECTORY + "\\" + articleId + "\\" + saveName;
//
//		FileVO fileVO = FileVO.builder()
//			.articleId(articleId)
//			.saveName(saveName)
//			.originalName(originalName)
//			.path(path)
//			.extension(extension)
//			.build();
//		return fileVO;
//	}
//
//	private void saveFile(MultipartFile file, FileVO fileVO) throws IOException  {
//		String path = fileVO.getPath();
//		createDirectoryFolder(path);
//		File targetFile = new File(path);
//		file.transferTo(targetFile);
//	}
//
//	private void createDirectoryFolder(String path) throws IOException {
//		String directory = path.substring(0,path.lastIndexOf("\\"));
//		Path directoryPath = Paths.get(directory);
//		if (!Files.isDirectory(directoryPath)) {
//			Files.createDirectory(directoryPath);
//		}
//	}
//}
