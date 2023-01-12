<template>
  <h1>
    게시글
  </h1>
  <span>작성자</span><span style="padding: 30px">{{ article.writer }}</span> <br>
  <span>등록 일시</span><span style="padding: 30px">{{ article.dateCreated }}</span> <br>
  <span>수정 일시</span><span style="padding: 30px">{{ article.lastUpdated }}</span> <br>
  <span>카테고리</span><span style="padding: 30px">{{ article.categoryName }}</span> <br>
  <span>제목</span><span style="padding: 30px">{{ article.title }}</span> <br>
  <span>조회수</span><span style="padding: 30px">{{ article.views }}</span> <br>
  <span>내용</span><span style="padding: 30px">{{ article.content }}</span> <br>
  <span>파일</span><span style="padding: 30px">{{ article.fileAttached }}</span> <br>
  <h1>
    댓글
  </h1>
  <div id="comment">
    <template v-for="(comment,index) in article.commentList" :key="index">
      <span style="padding: 30px">{{ comment.dateCreated }}</span> <br>
      <span style="padding: 30px">{{ comment.content }}</span> <br>
    </template>
    <textarea v-model="commentContent"></textarea>
    <button @click="registerComment">댓글 등록</button>
  </div>
  <button @click="toArticleList">목록</button>
</template>

<script>
import boardAPI from "@/boardAPI";

export default {
  name: "ArticleDetail",
  data() {
    return {
      /**
       * 게시글 정보가 바인딩 될 객체
       */
      article: {},

      /**
       * 게시글 번호가 바인딩 될 변수
       */
      articleId: '',

      /**
       * 유저가 작성하는 댓글 내용이 바인딩 될 변수
       */
      commentContent: ''
    }
  },

  /**
   * 진입시 uri 에 있는 게시글 번호를 통해 게시글 정보 조회
   */
  mounted() {
    this.articleId = this.$route.params.id
    this.getArticleDetail(this.articleId)
  },
  methods: {
    /**
     * 게시글 정보 조회
     */
    getArticleDetail(articleId) {
      boardAPI.getArticle(articleId).then((response) => {
        this.article = response.data
      })
    },

    /**
     * 게시글 목록으로 가기
     */
    toArticleList() {
      this.$router.replace('/articles');
    },

    /**
     * 댓글 등록
     *    -- 게시글 번호와 유저가 작성한 댓글 내용을 기반으로 댓글 객체 생성하여 댓글 등록 요청
     *    -- 댓글 등록후 새로 게시글 데이터를 가져오면서 조회수가 증가되는 것을 방지하기 위해,
     *       현 페이지에 댓글 덧붙이기
     *       -- 여러 유저가 동시에 댓글을 등록할 땐 문제가 생김. DB 조회시 조회수 증가를 막거나,
     *           댓글 등록후 새로고침으로 하는 것이 나을 수도
     */
    registerComment() {
      let commentVO = {
        articleId: this.articleId,
        content: this.commentContent
      }
      boardAPI.insertComment(commentVO);

      commentVO.dateCreated = this.formatDate(new Date())
      this.article.commentList.push(commentVO)
      this.commentContent = ''
    },

    /**
     * Date 포맷 변경을 위한 함수
     */
    padTo2Digits(num) {
      return num.toString().padStart(2, '0');
    },

    /**
     * Date 포맷 변경을 위한 함수
     */
    formatDate(date) {
      return (
          [
            date.getFullYear(),
            this.padTo2Digits(date.getMonth() + 1),
            this.padTo2Digits(date.getDate()),
          ].join('-') +
          ' ' +
          [
            this.padTo2Digits(date.getHours()),
            this.padTo2Digits(date.getMinutes()),
            this.padTo2Digits(date.getSeconds()),
          ].join(':')
      );
    }
  }
}
</script>

<style scoped>

</style>