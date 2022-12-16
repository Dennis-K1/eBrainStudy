package classes;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class ArticleVO {

    private int id;
    private int articleCategoryId;
    private String articleCategoryName;
    private String title;
    private String content;
    private int views;
    private String writer;
    private String password;
    private String dateCreated;
    private String lastUpdated;
    private int fileAttached;

    public ArticleVO() {

    }
}
