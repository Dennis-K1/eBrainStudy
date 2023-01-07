<template>
 <h1>페지네이션</h1>
  <div class="pagination">
    <a @click="onPageChange(pageNumber -1)">&lt;</a>
    <a v-for="(paging,index) in pages" :key="index" @click="onPageChange(paging-1)">{{paging}}</a>
    <a @click="onPageChange(pageNumber +1)">&gt;</a>

  </div>
</template>

<script>
export default {
  name: "ArticlePagination",
  props: ['numberOfArticles','firstArticleIndex','PAGE_SIZE','pageNumber','pageChange'],
  data() {
    return {
      pageBlock : 10,
    }
  },
  methods: {
    onPageChange(pageNumber) {
      if (pageNumber < 0) {
        alert('첫 페이지입니다.');
        return;
      }
      if (pageNumber >= this.endPage) {
        alert('마지막 페이지입니다.');
        return;
      }
      const param = {
        requestPage: pageNumber,
      };
      this.pageChange(param);
    }
  },
  computed: {
    pages: function() {
      const list = [];
      for (let index = this.startPage; index <= this.endPage; index += 1) { list.push(index); }
      return list;
    },
    pageCount() {
      return Math.round(this.numberOfArticles / this.PAGE_SIZE)
    },
    startPage() {
      return parseInt(this.pageNumber / 10) * 10 + 1;
    },
    endPage() {
      let endPage = parseInt(this.pageNumber / 10) * 10 + 10;
      if (endPage > this.pageCount) {
        return this.pageCount
      } else {
        return endPage
      }
    },
  },
  watch : {
  }
}
</script>

<style scoped>

</style>