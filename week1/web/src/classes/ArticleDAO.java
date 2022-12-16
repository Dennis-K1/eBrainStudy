package classes;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ArticleDAO {
    private String url = "jdbc:mariadb://localhost:3306/eBrain_week1";
    private String userName = "root";
    private String userPassword = "1234";
    private String query = "";
    private Connection conn;

    public ArticleDAO() {

    }

    public List getArticles(int pageSize, int currentPage) {
        try {
            conn = DriverManager.getConnection(url,userName,userPassword);
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
                    "order by article.article_id desc " +
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
            conn = DriverManager.getConnection(url,userName,userPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<ArticleVO> articlesList = new ArrayList<>();
        if (queryStrings.get("fromDate").equals("") && queryStrings.get("toDate").equals("")) {
            queryStrings.put("fromDate","2000-01-01");
            queryStrings.put("toDate","2099-01-01");
        } else if (queryStrings.get("toDate").equals("")) {
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
                    "order by article.article_id desc " +
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

    public ArticleVO getArticle(int articleId) {
        try {
            conn = DriverManager.getConnection(url,userName,userPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArticleVO article = new ArticleVO();
        try {
            query = "update article " +
                    "set views = views + 1 " +
                    "where article_id = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, articleId);
            pstmt.executeQuery();
            System.out.println("getarticle" + pstmt);

            query = "select article_category.name, article.* " +
                    "from article_category " +
                    "right outer join article " +
                    "on article.article_category_id = article_category.article_category_id " +
                    "where article_id = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, articleId);
            rs = pstmt.executeQuery();
            System.out.println("getarticle" + pstmt);
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
                article.setId(id);
                article.setArticleCategoryName(articleCategoryName);
                article.setTitle(title);
                article.setContent(content);
                article.setWriter(writer);
                article.setViews(views);
                article.setDateCreated(dateCreated);
                article.setLastUpdated(lastUpdated);
                article.setFileAttached(fileAttached);
                rs.close();
                pstmt.close();
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("getArticle error");
            e.printStackTrace();
        }
        return article;
    }

    public List getArticleCategories() {
        try {
            conn = DriverManager.getConnection(url,userName,userPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<ArticleCategoryVO> articleCategoriesList = new ArrayList<>();
        try {
            query = "select article_category_id, name from article_category";
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("article_category_id");
                String name = rs.getString("name");
                ArticleCategoryVO articleCategory = new ArticleCategoryVO();
                articleCategory.setId(id);
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
            conn = DriverManager.getConnection(url,userName,userPassword);
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
            System.out.println("getNumberOfArticles error");
            e.printStackTrace();
        }
        return numberOfArticles;
    }

    /**
     * 검색 조건이 있을 경우 반환되는 게시글의 수
     * **/
    public int getNumberOfArticles(HashMap<String,String> queryStrings) {
        try {
            conn = DriverManager.getConnection(url,userName,userPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int numberOfArticles = 0;
        if (queryStrings.get("fromDate").equals("") && queryStrings.get("toDate").equals("")) {
            queryStrings.put("fromDate","2000-01-01");
            queryStrings.put("toDate","2099-01-01");
        }
        try {
            query = "select COUNT(*) cnt " +
                    "from article_category " +
                    "right outer join article " +
                    "on article.article_category_id = article_category.article_category_id " +
                    "where" +
                    "    (? < date_created  and (? + INTERVAL 1 DAY) > date_created)" +
                    "    and article_category.name LIKE ?" +
                    "    and (title LIKE ? or writer LIKE ? or content like ?)";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, queryStrings.get("fromDate"));
            pstmt.setString(2, queryStrings.get("toDate"));
            pstmt.setString(3, queryStrings.get("category"));
            pstmt.setString(4, queryStrings.get("query"));
            pstmt.setString(5, queryStrings.get("query"));
            pstmt.setString(6, queryStrings.get("query"));
            rs = pstmt.executeQuery();
            while (rs.next()) {
                numberOfArticles = rs.getInt("cnt");
                rs.close();
                pstmt.close();
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("getNumberOfArticles error");
            e.printStackTrace();
        }
        return numberOfArticles;
    }


    public int uploadArticle(ArticleVO article) {
        try {
            conn = DriverManager.getConnection(url,userName,userPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PreparedStatement pstmt = null;
        int result = 0;
        try {
            query = "insert into article " +
                    "(board_id, article_category_id, writer, password, title, content) " +
                    "values " +
                    "(1, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, article.getArticleCategoryId());
            pstmt.setString(2, article.getWriter());
            pstmt.setString(3, article.getPassword());
            pstmt.setString(4, article.getTitle());
            pstmt.setString(5, article.getContent());
            result = pstmt.executeUpdate();
            System.out.println("DAO result is " + result);
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("article error");
            e.printStackTrace();
        }
        return result;
    }
    public int test() {
        return 1;
    }
}
