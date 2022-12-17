package classes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleCommentDAO {
    private String url = "jdbc:mariadb://localhost:3306/eBrain_week1";
    private String userName = "root";
    private String userPassword = "1234";
    private String query = "";
    private Connection conn;

    public ArticleCommentDAO() {

    }

    public List getComments(int articleId) {
        try {
            conn = DriverManager.getConnection(url,userName,userPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<ArticleCommentVO> commentList = new ArrayList<>();
        try {
            query = "select * " +
                    "from article_comment " +
                    "right join article a " +
                    "on a.article_id = article_comment.article_id " +
                    "where a.article_id= ? " +
                    "order by article_comment_id asc ";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, articleId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("article_comment_id");
                String content = rs.getString("content");
                String  dateCreated = rs.getString("date_created");
                ArticleCommentVO comment = new ArticleCommentVO();
                comment.setId(id);
                comment.setContent(content);
                comment.setDateCreated(dateCreated);
                if (content != null) {
                    commentList.add(comment);
                }
                rs.close();
                pstmt.close();
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("get Comments error");
            e.printStackTrace();
        }
        return commentList;
    }


    public int uploadComment(ArticleCommentVO comment) {
        try {
            conn = DriverManager.getConnection(url,userName,userPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PreparedStatement pstmt = null;
        int result = 0;
        try {
            query = "insert into article_comment " +
                    "(article_id, content) " +
                    "values " +
                    "(?, ?)";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, comment.getArticleId());
            pstmt.setString(2, comment.getContent());
            result = pstmt.executeUpdate();
            System.out.println("commentDAO result is " + result);
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("commentUpload error");
            e.printStackTrace();
        }
        return result;
    }
}
