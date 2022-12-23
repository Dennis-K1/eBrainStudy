package com.week2.command;


import static com.week2.model.FileVO.absolutePath;
import static com.week2.model.FileVO.fileEncType;
import static com.week2.model.FileVO.fileMaxSize;
import static com.week2.model.FileVO.tempPath;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.week2.model.ArticleDAO;
import com.week2.model.ArticleVO;
import com.week2.model.FileDAO;
import com.week2.model.FileVO;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InsertArticleCommand implements Command {

	private HttpServletRequest request;
	private HttpServletResponse response;



	public String execute() throws ServletException, IOException {

		/**
		 * tempPath			임시 저장 경로
		 * absolutePath		파일 저장 절대 경로
		 */
		MultipartRequest multi = new MultipartRequest(request,tempPath,fileMaxSize,fileEncType,new DefaultFileRenamePolicy());

		String fileName = multi.getFilesystemName("fileName");
		int categoryId = Integer.parseInt(multi.getParameter("category"));
		String title = multi.getParameter("title");
		String content = multi.getParameter("content");
		String writer = multi.getParameter("writer");
		String password = multi.getParameter("password");

		ArticleDAO articleDAO = new ArticleDAO();
		ArticleVO article = ArticleVO.builder()
			.categoryId(categoryId)
			.title(title)
			.content(content)
			.writer(writer)
			.password(password)
			.build();

		/*
		insertArticle 성공시 게시글 번호 반환
		 */
		int result = articleDAO.insertArticle(article);

		if (result != 0) {
			String fileSavePath = absolutePath+"\\"+result;
			Path tempFile = Paths.get(tempPath + "\\" + fileName);
			Path saveFile = Paths.get(fileSavePath);
			Files.createDirectories(saveFile);
			Files.move(tempFile,saveFile.resolve(tempFile.getFileName()));

			FileVO file = FileVO.builder()
				.articleId(result)
				.name(fileName)
				.path(fileSavePath)
				.build();
			FileDAO fileDAO = new FileDAO();
			result = fileDAO.insertFile(file);
		}

		if (result == 1){
			return "articleInserted.jsp";
		} else {
			return "articleNotInserted.jsp";
		}
	}

	public void validation (int categoryId) {}
}
