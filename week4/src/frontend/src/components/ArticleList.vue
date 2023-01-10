<template>
  <!--  // 라우터 replace (쿼리 스트링 넘겨주기)-->
  <!--  게시글 목록  -->
  <div class="articleList">
    <table>
      <thead>
      <tr>
        <!--          테이블 헤더-->
        <th v-for="(column, index) in tableHeaders" :key="index">{{ column }}</th>
      </tr>
      </thead>
      <tbody>
      <!--      처음 게시판 진입해서 따로 조회한 게시글 데이터가 없을 경우 부모 컴포넌트에서 받은 props 표시-->
      <template v-if="Object.keys(articleList).length == 0">
        <tr v-for="(article,index) in boardVO.articleList" :key="index">
          <td>{{ article.categoryName }}</td>
          <td>{{ article.fileAttached }}</td>
          <td style="text-decoration: underline" @click="toArticleDetail(article.id)"><a href="#">{{article.title }}</a>
          </td>
          <td>{{ article.writer }}</td>
          <td>{{ article.views }}</td>
          <td>{{ article.dateCreated }}</td>
          <td>{{ article.lastUpdated }}</td>
        </tr>
      </template>
      <!-- 검색 후 데이터가 assign 됐을 경우 해당 데이터 표시-->
      <template v-else>
        <tr v-for="(article,index) in articleList" :key="index">
          <td>{{ article.categoryName }}</td>
          <td>{{ article.fileAttached }}</td>
          <td style="text-decoration: underline" @click="toArticleDetail(article.id)"><a href="#">{{article.title }}</a>
          </td>
          <td>{{ article.writer }}</td>
          <td>{{ article.views }}</td>
          <td>{{ article.dateCreated }}</td>
          <td>{{ article.lastUpdated }}</td>
        </tr>
      </template>
      </tbody>
    </table>

    <a href="/">
      <button>처음으로</button>
    </a>
  </div>
</template>

<script>
import boardAPI from "@/boardAPI";

export default {
  name: "ArticleList",
  data() {
    return {
      tableHeaders: ['카테고리', '파일', '제목', '작성자', '조회수', '등록 일시', '수정 일시'],
      articleList: {}
    }
  },
  props: ['boardVO'],
  methods: {
    getBoardVO(queryString) {
      boardAPI.getBoardVO(queryString).then((response) => {
            let articleList = response.data.articleList;
            if (Object.keys(articleList).length == 0) {
              this.articleList = {'categoryName': '데이터 없음'}
            } else {
              this.articleList = response.data.articleList
            }
          }
      )
    },
    toArticleDetail(articleId) {
      this.$router.replace(`/articles/${articleId}`
      );
    }
  }
  ,
  watch: {
    // 쿼리스트링 변경되면 (즉, 유저가 검색을 누르면) 데이터 조회
    '$route'(to) {
      this.getBoardVO(to.query)
    }
  }
}
</script>

<style scoped>
a:link {
  color: black;
  text-decoration: none;
}

a:visited {
  color: black;
  text-decoration: none;
}
</style>