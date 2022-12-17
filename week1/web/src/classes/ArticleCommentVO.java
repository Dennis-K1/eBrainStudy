package classes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleCommentVO {
    private int id;
    private int articleId;
    private String content;
    private String dateCreated;
}
