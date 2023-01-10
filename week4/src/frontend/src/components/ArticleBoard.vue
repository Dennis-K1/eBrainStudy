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

  <ArticleList :boardVO="boardVO"/>

  <!--  게시글 목록 페지네이션-->
  <!--    <h1>페지네이션</h1>-->
  <!--    <div id="pagination">-->
  <!--      <a @click="onPageChange(pageNumber -1)">&lt;</a>-->
  <!--      <a v-for="(paging,index) in pages" :key="index" @click="onPageChange(paging)">{{paging}}</a>-->
  <!--      <a @click="onPageChange(pageNumber +1)">&gt;</a>-->
  <!--    </div>-->
</template>

<script>
import boardAPI from "@/boardAPI";
import ArticleList from "@/components/ArticleList.vue";

export default {
  name: "ArticleBoard",
  components: {ArticleList},
  /**
   * @searchVO : 검색창 검색값 바인딩 (v-model) 을 위한 data
   *             categoryId: 카테고리 값 (공백 방지를 위해 미리 0 대입)
   *             startDate: 등록 일시 검색 범위 from
   *             endDate: 등록 일시 검색 범위 to
   *             keyword: 검색 키워드
   * @boardVO : 게시판 표시 정보
   *             articleList: 조회 게시글 목록
   *             categoryList: 검색창 카테고리 옵션
   *             searchVO: 조회 검색값
   *             numberOfArticles: 검색값 기반 총 게시글 수
   */
  data() {
    return {
      searchVO: {
        categoryId: 0
      },
      boardVO: {},
    }
  },
  methods: {
    /**
     * 검색
     * 유저가 선택한 검색값에 맞추어 라우터 쿼리스트링 변경
     * -- (쿼리스트링 변경에 맞추어 ArticleList 컴포넌트에서 데이터 조회)
     */
    search(){
      this.$router.replace({query:this.searchVO})
    },
    /**
     * 처음 articles 경로 진입시 초기 게시판 정보 조회
     * -- (props 로 ArticleList 에 전달)
     * -- (이후 검색에 따라 조건 변경시 ArticleList 컴포넌트 내에서 처리)
     * -- (검색창, 게시글 목록 분리를 통해 라우터 변경이 일어나도 검색값은 유지하며,
     *    바뀐 조건에 맞추어 게시글 정보를 가져오기 위함)
     */
    getBoardVO(){
      boardAPI.getBoardVO(null).then((response) => {
        this.boardVO = response.data
      })
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
  /**
   * 처음 articles 경로 진입시 초기 게시판 정보 조회
   */
  created() {
    this.getBoardVO()
  },
  /**
   * uri 에 쿼리스트링은 있는데 검색창은 비었을 경우를 대비하여,
   * 쿼리스트링에 맞추어 검색값 설정
   */
  mounted() {
    this.setSearchValuesIfNull()
  }
}
</script>

<style scoped>
</style>