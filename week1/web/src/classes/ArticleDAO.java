package classes;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ArticleDAO {
    private String url = "jdbc:mariadb://localhost:3306/eBrain_week1";
    private String userName = "root";
    private String password = "1234";
    private String query = "";
    private Connection conn;

    public ArticleDAO() {

    }

    public List getArticles(int pageSize, int currentPage) {
        try {
            conn = DriverManager.getConnection(url,userName,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<ArticleVO> articlesList = new ArrayList<>();
        try {
            query = "select article_category.name, article.* from " +
                    "article_category right outer join article " +
                    "on article.article_category_id = article_category.article_category_id " +
                    "limit ? " +
                    "offset ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, pageSize);
            pstmt.setInt(2, currentPage);
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
            System.out.println("article error");
            e.printStackTrace();
        }
        return articlesList;
    }

    public List getArticles(int pageSize, int currentPage, HashMap<String,String> queryStrings) {
        try {
            conn = DriverManager.getConnection(url,userName,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<ArticleVO> articlesList = new ArrayList<>();
        if (queryStrings.get("fromDate").equals("") && queryStrings.get("toDate").equals("")) {
            queryStrings.put("fromDate","2000-01-01");
            queryStrings.put("toDate","2099-01-01");
        }
        try {
            query = "select article_category.name, article.* " +
                    "from article_category " +
                    "right outer join article " +
                    "on article.article_category_id = article_category.article_category_id " +
                    "where" +
                    "    (? < date_created  and (? + INTERVAL 1 DAY) > date_created)" +
                    "    and article_category.name LIKE ?" +
                    "    and (title LIKE ? or writer LIKE ? or content like ?)" +
                    "limit ? " +
                    "offset ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, queryStrings.get("fromDate"));
            pstmt.setString(2, queryStrings.get("toDate"));
            pstmt.setString(3, queryStrings.get("category"));
            pstmt.setString(4, queryStrings.get("query"));
            pstmt.setString(5, queryStrings.get("query"));
            pstmt.setString(6, queryStrings.get("query"));
            pstmt.setInt(7, pageSize);
            pstmt.setInt(8, currentPage);
            System.out.println(pstmt.toString());
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
            System.out.println("article error");
            e.printStackTrace();
        }
        return articlesList;
    }

    public List getArticleCategories() {
        try {
            conn = DriverManager.getConnection(url,userName,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<ArticleCategoryVO> articleCategoriesList = new ArrayList<>();
        try {
            query = "select name from article_category";
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                ArticleCategoryVO articleCategory = new ArticleCategoryVO();
                articleCategory.setName(name);
                articleCategoriesList.add(articleCategory);
                rs.close();
                pstmt.close();
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("category error");
            e.printStackTrace();
        }
        return articleCategoriesList;
    }

    public int getNumberOfArticles() {
        try {
            conn = DriverManager.getConnection(url,userName,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int numberOfArticles = 0;
        try {
            query = "select COUNT(*) cnt from article";
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                numberOfArticles = rs.getInt("cnt");
                rs.close();
                pstmt.close();
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("category error");
            e.printStackTrace();
        }
        return numberOfArticles;
    }

    public int test() {
        return 1;
    }
}
