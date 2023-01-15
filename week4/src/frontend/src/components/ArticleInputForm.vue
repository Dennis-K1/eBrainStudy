<template>
  <form>
    <table>
      <tr>
        <td>
          <span>카테고리</span>
          <span style="color: red">*</span>
        </td>
        <td>
          <select ref="category" v-model="article.categoryId">
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
          <input ref="writer" v-model="article.writer" type="text" name="writer" autocomplete="username">
        </td>
      </tr>
      <tr>
        <td>
          <span>비밀번호</span>
          <span style="color: red">*</span>
        </td>
        <td>
          <input ref="password" v-model="article.password" type="password" name="password" autocomplete="new-password" placeholder="비밀번호">
          <input ref="passwordValidation" type="password" name="passwordValidation" autocomplete="new-password" placeholder="비밀번호 확인">
        </td>
      </tr>
      <tr>
        <td>
          <span>제목</span>
          <span style="color: red">*</span>
        </td>
        <td>
          <input ref="title" v-model="article.title" type="text" name="title">
        </td>
      </tr>
      <tr>
        <td>
          <span>내용</span>
          <span style="color: red">*</span>
        </td>
        <td>
          <textarea ref="content" v-model="article.content" name="content"></textarea>
        </td>
      </tr>
      <tr v-for="(fileIndex) in this.numberOfFiles" :key="fileIndex">
        <td>
          <input type="file" ref="file" @change="selectFile(fileIndex-1)">
        </td>
      </tr>
    </table>
  </form>

  <button @click="registerArticle"> 게시글 등록 </button>
</template>

<script>
import boardAPI from "@/boardAPI";
import util from "@/util";

export default {
  name: "ArticleInput",
  data(){
    return{
      numberOfFiles:3,
      /**
       * 게시글 정보가 담길 객체
       */
      article:{
        categoryId:'',
        writer:'',
        password:'',
        title:'',
        content:'',
        fileList:[],
      },
      /**
       * 카테고리 옵션 선택값이 담길 객체
       */
      categoryList:{}
    }
  },
  methods:{
    /**
     * 파일 select 시 게시글 객체에 파일 정보 저장
     */
    selectFile(fileIndex){
      this.article.fileList.push(this.$refs.file[fileIndex].files[0])
    },
    /**
     * 카테고리 리스트 반환
     */
    getCategoryList(){
      boardAPI.getBoardVO(null).then((response) => {
        this.categoryList = response.data.categoryList})
    },

    /**
     * 게시글 등록
     *
     * 입력값 서버 유효성 검증 실패시 alert 와 함께 목록 페이지로 전환
     */
    registerArticle(){
      util.validateArticleInput(this.article,this.$refs);
      boardAPI.insertArticle(this.article).then((response) => {
        if (response.data === 'validationError'){
          alert('입력값에 오류가 있습니다.')
          window.location.replace("/")
        }
      });
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