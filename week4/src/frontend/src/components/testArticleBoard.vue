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
    <button @click="search">검색</button>
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
        <td>{{ article.title }}</td>
        <td>{{ article.writer }}</td>
        <td>{{ article.views }}</td>
        <td>{{ article.dateCreated }}</td>
        <td>{{ article.lastUpdated }}</td>
      </tr>
      </tbody>
    </table>

    <a href="/">
      <button>처음으로</button>
    </a>
  </div>
</template>

<script>
import {mapActions, mapState} from "vuex";

export default {
  name: "testArticleBoard",
  data() {
    return {
      searchVO: {
        categoryId:0
      }
    }
  },
  methods:{
    search(){
      this.$router.replace({query:this.searchVO})
    },
    /**
     * 쿼리스트링이 존재할 경우, 쿼리스트링에 맞추어 검색값 설정
     * -- (유저가 주소창으로 검색값을 입력해서 들어올 경우,
     *     그에 맞추어 검색창에 검색값 표시)
     * -- (null 일 경우 공백으로 표시되는 category 옵션을 위해
     *     '전체 카테고리'에 해당하는 '0' 대입)
     */
    setSearchValuesIfNull(){
      if (!this.isQueryStringEmpty()){
        this.searchVO = this.$route.query
        if (this.searchVO.categoryId == null) {
          this.searchVO.categoryId = 0
        }
      }
    },
    /**
     * 쿼리스트링 유무 확인
     */
    isQueryStringEmpty(){
      if (Object.keys(this.$route.query).length == 0){
        return true;
      }
    }
  },
  computed: {
    ...mapState(['boardVO']),
    ...mapActions(['fetchBoardVO'])
  },
  async mounted() {
    await this.setSearchValuesIfNull()
    await this.$store.dispatch('fetchBoardVO',this.searchVO)

  },
  watch: {
    '$route'(to) {
      console.log(to.query)
      this.$store.dispatch('fetchBoardVO',to.query)
    }
  }

}
</script>

<style scoped>

</style>