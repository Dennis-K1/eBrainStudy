<template>
  {{msg}}
</template>

<script>
import boardApi from '@/BoardAPI';

export default {
  name: 'HelloWorld',
  data() {
    return {
      msg: '',
      searchVO : {
        'keyword' : '111',
        'pageNumber' : 1,
        'categoryId' : 2,
      },
      mockInsertArticle : {
        'categoryId' : 3,
        'title' : 'Test',
        'content' : 'VUE api calling Test',
        'writer' : 'VUE',
        'password' : 'qwe123!'
      },
      mockUpdateArticle : {
        'id' : 130,
        'title' : 'Test',
        'content' : 'VUE api calling Test',
        'writer' : 'VUE',
        'password' : 'qwe123!'
      },
      mockInsertComment : {
        'articleId' : 130,
        'content' : 'VUE frontend API TEST'
      }
    }
  },
  mounted() {
    // boardApi.getBoardVO(this.searchVO).then(response => this.msg = response.data);
    // boardApi.updateArticle(JSON.stringify(this.mockUpdateArticle)).then(response => this.msg = response.data);

  }
}
</script>