<template>
  <div class="articleList">
    <table>
      <thead>
        <tr>
          <th v-for="(column, index) in tableHeaders" :key="index">{{column}}</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(article,index) in articleList" :key="index">
          <td v-for="(articleKey,subIndex) in articleKeyList" :key="subIndex">
            {{article[articleKey]}}
          </td>
        </tr>
      </tbody>
    </table>
    <ArticlePagination :numberOfArticles="numberOfArticles"
                       :firstArticleIndex="firstArticleIndex"
                       :PAGE_SIZE="PAGE_SIZE"
                       :pageNumber="pageNumber"
                       :pageChange="pageChange"

    />
  </div>
</template>

<script>

import ArticlePagination from "@/components/ArticlePagination";
export default {
  name: "ArticleList",
  components: {ArticlePagination},
  props: ['tableHeaders','articleList','articleKeyList','categoryList','numberOfArticles','firstArticleIndex','PAGE_SIZE','pageNumber','pageChange'],
  methods: {
  },
  watch: {},
  beforeCreate() {
  },
  created() {
  },
}
</script>

<style scoped>

</style>