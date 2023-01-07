<template>
  <ArticleList
      :tableHeaders="tableHeaders"
      :articleList="articleList"
      :articleKeyList="articleKeyList"
      :categoryList="categoryList"
      :numberOfArticles="numberOfArticles"
      :firstArticleIndex="firstArticleIndex"
      :PAGE_SIZE="PAGE_SIZE"
      :pageNumber="pageNumber"
      :pageChange="onPageChange"
  />
</template>

<script>
import boardAPI from "@/boardAPI";
import ArticleList from "@/components/ArticleList";

export default {
  name: 'App',
  components: {ArticleList},
  data() {
    return {
      tableHeaders: ["카테고리","  ","제목","작성자","조회수","등록 일시","수정 일시"],
      articleList: [],
      articleKeyList: ["categoryName","fileAttached","title","writer","views","dateCreated","lastUpdated"],
      categoryList: [],
      pageBlock : 10,
      numberOfArticles : 0,
      firstArticleIndex : 0,
      pageNumber : 0,
      PAGE_SIZE : 10,
      searchVO: {},
      boardVO: {},
      startDate: '',
      endDate: '',
      categoryId: '',
      keyword: '',
    }
  },
  watch: {

  },
  methods: {
    search() {
      boardAPI.getBoardVO(this.searchVO).then((response) => {
        this.articleList = response.data.articleList
        this.categoryList = response.data.categoryList
        this.numberOfArticles = response.data.numberOfArticles
        this.pageNumber = response.data.searchVO.pageNumber
        this.firstArticleIndex = response.data.searchVO.firstArticleIndex;
      })
    },
    onPageChange(value) {
      this.pageNumber = value.requestPage;
      this.searchVO.pageNumber = this.pageNumber
      this.search(this.searchVO);
    },
  },
  mounted() {
    this.search()
  }
}
</script>