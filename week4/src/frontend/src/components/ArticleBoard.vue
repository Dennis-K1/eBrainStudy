<template>
  <!-- 게시글 검색창 -->
  <div id="searchBar">
    <input type="date" v-model="searchVO.startDate">
    ~
    <input type="date" v-model="searchVO.endDate">

    <select v-model="searchVO.categoryId">
      <option disabled value="">전체 카테고리</option>
      <option value="0">전체 카테고리</option>
      <option v-for="(category,index) in boardVO.categoryList" :key="index" :value="category.id">{{ category.name }}
      </option>
    </select>
    <input type="text" placeholder="검색어를 입력해 주세요. (제목 + 작성자 + 내용)" v-model="searchVO.keyword">
    <button type="button" @click="search">검색</button>
  </div>
  <!--  게시글 목록  -->
  <div class="articleList">
    <table>
      <thead>
      <!--          테이블 헤더-->
      <tr>
        <td>카테고리</td>
        <td>파일</td>
        <td>제목</td>
        <td>작성자</td>
        <td>조회수</td>
        <td>등록 일시</td>
        <td>수정 일시</td>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(article,index) in boardVO.articleList" :key="index">
        <td>{{ article.categoryName }}</td>
        <td>{{ article.fileAttached }}</td>
        <td><button @click="toArticleDetail(article.id)" type="button" class="titleButton">{{ article.title }}</button></td>
        <td>{{ article.writer }}</td>
        <td>{{ article.views }}</td>
        <td>{{ article.dateCreated }}</td>
        <td>{{ article.lastUpdated }}</td>
      </tr>
      </tbody>
    </table>
  </div>
      페지네이션
  <div id="pagination">
    <template v-if="boardVO.searchVO.pageNumber <= pages.length">
      <button type="button" @click="toPageOf(startPage)" class="pageButton">&lt;&lt;</button>
      <button type="button" @click="toPageOf(currentPageNumber-1)" class="pageButton">&lt;</button>
      <template v-for="(pageNumber,index) in pages" :key="index">
        <button type="button" v-if="pageNumber == boardVO.searchVO.pageNumber" class="pageButtonClicked">{{ pageNumber }}</button>
        <button type="button" v-else @click="toPageOf(pageNumber)" class="pageButton">{{ pageNumber }}</button>
      </template>
      <button type="button" @click="toPageOf(currentPageNumber+1)" class="pageButton">&gt;</button>
      <button type="button" @click="toPageOf(endPage)" class="pageButton">&gt;&gt;</button>
    </template>
  </div>
</template>

<script>
import {mapActions, mapState} from "vuex";

export default {
  name: "ArticleBoard",

  data() {
    return {
      /**
       * @searchVO : 검색창 검색값 바인딩 (v-model) 을 위한 data
       *             categoryId: 카테고리 값 (공백 방지를 위해 미리 0 대입)
       *             startDate: 등록 일시 검색 범위 from
       *             endDate: 등록 일시 검색 범위 to
       *             keyword: 검색 키워드
       */
      searchVO: {
        categoryId: 0,
      }
    }
  },
  methods: {
    toArticleDetail(articleId){
      this.$router.replace(`/articles/${articleId}`)
    },
    /**
     * 현 페이지 번호 정보 삭제후 searchVO v-model 에 바인딩된 값에 기반하여 route.query 변경
     *    -- route.query 가 변경되면 watch() 에서 감지후 데이터 조회
     */
    search() {
      delete this.searchVO.pageNumber
      this.$router.replace({query: this.searchVO})
    },

    /**
     * searchVO 에 선택된 페이지 번호를 더하여 rotue.query 변경 후 watch() 에서 검색 진행
     */

    toPageOf(pageNumber) {
      if (pageNumber <= 0 || pageNumber > this.endPage) {
        return;
      }
      this.searchVO.pageNumber = pageNumber
      this.$router.replace({query: this.searchVO})
    },

    /**
     * 쿼리스트링에 맞추어 검색값 표시
     * -- (유저가 주소창으로 검색값을 입력해서 들어올 경우,
     *     그에 맞추어 검색창에 검색값 표시)
     * -- (검색값이 없을 경우 공백으로 표시되는 category 옵션을 위해
     *     '전체 카테고리'에 해당하는 '0' 대입)
     */
    indicateSearchValues() {
      if (!this.isQueryStringEmpty()) {
        this.searchVO = this.$route.query
        if (this.searchVO.categoryId == null) {
          this.searchVO.categoryId = 0
        }
      }
    },

    /**
     * 쿼리스트링 유무 확인
     */
    isQueryStringEmpty() {
      if (Object.keys(this.$route.query).length == 0) {
        return true;
      }
    },

    /*
    유저가 초과되는 pageNumber 를 입력 시 조회되는 게시물은 없으나, 검색 버튼이 먹히질 않는 버그가 있음.
     */
    /**
     * queryString 에 불필요한 값이 입력되었을 경우 초기 화면으로 redirect
     */
    validateQueryString() {
      const acceptableQueryString = ['categoryId', 'startDate', 'endDate', 'keyword', 'pageNumber']
      let queryString = {...this.$route.query};

      acceptableQueryString.forEach(query => delete queryString[query])

      if (Object.keys(queryString).length > 0) {
        window.location.href = '/articles'
      }
    }
  },
  computed: {
    /**
     * @boardVO : 게시판 표시 정보
     *             articleList: 조회 게시글 목록
     *             categoryList: 검색창 카테고리 옵션
     *             searchVO: 조회 검색값
     *             numberOfArticles: 검색값 기반 총 게시글 수
     */
    ...mapState(['boardVO']),

    /**
     * @fetchBoardVO : 검색값에 맞추어 데이터 조회후 $store.boardVO에 저장
     */
    ...mapActions(['fetchBoardVO']),

    /**
     * 게시글 검색 결과 페이지에 표시될 페이지 (최대 10개)
     */
    pages: function () {
      const pageNumberList = [];
      for (let index = this.startPage; index <= this.endPage; index += 1) {
        pageNumberList.push(index);
      }
      return pageNumberList;
    },

    /**
     * 목록에 표시될 최대 페이지 갯수 (10개)
     */
    pageSize() {
      return this.boardVO.searchVO.page_SIZE;
    },

    /**
     * 게시글 검색 결과 갯수에 따른 총 페이지 수
     */
    pageCount() {
      return Math.round(this.boardVO.numberOfArticles / this.pageSize)
    },

    /**
     * 현재 페이지 번호
     */
    currentPageNumber() {
      return this.boardVO.searchVO.pageNumber
    },

    /**
     * 현재 페이지 번호 기반 페지네이션 시작 번호
     */
    startPage() {
      return parseInt(this.currentPageNumber / 10) * 10 + 1;
    },

    /**
     * 현재 페이지 번호 기반 페지네이션 끝 번호
     */
    endPage() {
      let endPage = parseInt(this.currentPageNumber / 10) * 10 + 10;
      if (endPage > this.pageCount) {
        return this.pageCount
      } else {
        return endPage
      }
    },
  },

  /**
   * 생성 직후 queryString (searchVO)에 기반하여 데이터 조회후 $store.boardVO에 바인딩,
   * queryString 값 검색 옵션 v-model 에 바인딩하여, 검색창에 검색값 표시
   * queryString 에 불필요한 값이 입력되었을 경우 초기 화면으로 redirect
   */
  created() {
    this.$store.dispatch('fetchBoardVO', this.$route.query)
    this.indicateSearchValues()
    this.validateQueryString();
  },

  /**
   * search 함수 (route.query 변경) 가 실행될 때 변경되는 queryString 에 기반하여 데이터 검색
   */
  watch: {
    '$route'(to) {
      this.$store.dispatch('fetchBoardVO', to.query)
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
.pageButton{
  background-color: white;
  border: none;
  padding: 5px;
  cursor: pointer;

}

.pageButtonClicked{
  background-color: white;
  border: none;
  padding: 5px;
  color: red;
}

.titleButton{
  background-color: white;
  border: none;
  text-decoration: underline;
  font-size: 16px;
  cursor: pointer;
}

.pageButton:hover{
  background-color: grey;
}
</style>