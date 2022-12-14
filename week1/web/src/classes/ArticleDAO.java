package classes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleDAO {
    private String url = "jdbc:mariadb://localhost:3306/eBrain_week1";
    private String userName = "root";
    private String password = "1234";
    private String query = "";
    private Connection conn;

    public ArticleDAO() {

    }

    public List getArticles() {
        try {
            conn = DriverManager.getConnection(url,userName,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<ArticleVO> articlesList = new ArrayList<>();
        try {
            query = "select article_category.name, article.* from article_category, article where article_category.article_category_id = article.article_id";
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("article_id");
                String articleCategoryName = rs.getString("article_category.name");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String writer = rs.getString("writer");
                int views = rs.getInt("views");
                String  dateCreated = rs.getString("date_created");
                String  lastUpdated = rs.getString("last_updated");
                if (lastUpdated == null) {
                    lastUpdated = "-";
                }
                int fileAttached = rs.getInt("file_attached");
                ArticleVO article = new ArticleVO();
                article.setId(id);
                article.setArticleCategoryName(articleCategoryName);
                article.setTitle(title);
                article.setContent(content);
                article.setWriter(writer);
                article.setViews(views);
                article.setDateCreated(dateCreated);
                article.setLastUpdated(lastUpdated);
                article.setFileAttached(fileAttached);
                articlesList.add(article);
                rs.close();
                pstmt.close();
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articlesList;
    }

    public int test() {
        return 1;
    }
}
