<template>
  <form>
    <table>
      <tr>
        <td>
          <span>카테고리</span>
          <span style="color: red">*</span>
        </td>
        <td>
          <select v-model="article.categoryId">
            <option disabled value="">카테고리 선택</option>
            <option v-for="(category,index) in categoryList" :key="index" :value="category.id">{{ category.name }}
            </option>
          </select>
        </td>
      </tr>
      <tr>
        <td>
          <span>작성자</span>
          <span style="color: red">*</span>
        </td>
        <td>
          <input v-model="article.writer" type="text" name="writer" autocomplete="username">
        </td>
      </tr>
      <tr>
        <td>
          <span>비밀번호</span>
          <span style="color: red">*</span>
        </td>
        <td>
          <input v-model="article.password" type="password" name="password" autocomplete="new-password" placeholder="비밀번호">
          <input type="password" name="passwordValidation" autocomplete="new-password" placeholder="비밀번호 확인">
        </td>
      </tr>
      <tr>
        <td>
          <span>제목</span>
          <span style="color: red">*</span>
        </td>
        <td>
          <input v-model="article.title" type="text" name="title">
        </td>
      </tr>
      <tr>
        <td>
          <span>내용</span>
          <span style="color: red">*</span>
        </td>
        <td>
          <textarea v-model="article.content" name="content"></textarea>
        </td>
      </tr>
      <tr>
        <td>
          <input type="file" name="fileList">
        </td>
        <td>
          <div>
            <p>                               </p>
          </div>
        </td>
      </tr>
    </table>
  </form>

  <button @click="registerArticle"> 게시글 등록 </button>
</template>

<script>
import boardAPI from "@/boardAPI";

export default {
  name: "ArticleInput",
  data(){
    return{
      /**
       * 게시글 정보가 담길 객체
       */
      article:{
      },
      /**
       * 카테고리 옵션 선택값이 담길 객체
       */
      categoryList:{}
    }
  },
  methods:{
    /**
     * 카테고리 리스트 반환
     */
    getCategoryList(){
      boardAPI.getBoardVO(null).then((response) => {
        this.categoryList = response.data.categoryList})
    },
    /**
     * 게시글 등록
     */
    registerArticle(){
      boardAPI.insertArticle(this.article);
      window.location.replace("/")
    }
  },
  mounted() {
    this.getCategoryList()
  }
}
</script>

<style scoped>

</style>