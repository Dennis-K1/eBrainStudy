<template>
  <template v-if="pageStatus === 'articleDetail'">
    <div v-if="validationModal.openStatus == 1" id="passwordValidation">
      <span>비밀번호</span>
      <span style="color: red">*</span>
      <form>
        <input v-model="validationModal.userInputPassword" type="password" id="password"
               autocomplete="new-password" placeholder=" 비밀번호를 입력해 주세요.">
      </form>
      <button @click="checkModalPassword">확인</button>
      <button @click="closeValidationModal">취소</button>
    </div>
    <h1>
      게시글
    </h1>
    <span>카테고리</span><span style="padding: 30px">{{ article.categoryName }}</span> <br>
    <span>등록 일시</span><span style="padding: 30px">{{ article.dateCreated }}</span> <br>
    <span>수정 일시</span><span style="padding: 30px">{{ article.lastUpdated }}</span> <br>
    <span>조회수</span><span style="padding: 30px">{{ article.views }}</span> <br>
    <span>작성자</span><span style="padding: 30px">{{ article.writer }}</span> <br>
    <span>제목</span><span style="padding: 30px">{{ article.title }}</span> <br>
    <span>내용</span><span style="padding: 30px">{{ article.content }}</span> <br>
    <span>파일</span><span style="padding: 30px">{{ article.fileAttached }}</span> <br>
    <h1>
      댓글
    </h1>
    <div id="comment">
      <template v-for="(comment,index) in article.commentList" :key="index">
        <span style="padding: 30px">{{ comment.dateCreated }}</span> <br>
        <span style="padding: 30px">{{ comment.content }}</span> <br>
      </template>
      <textarea v-model="commentContent"></textarea>
      <button @click="registerComment">댓글 등록</button>
    </div>
    <button @click="toArticleList">목록</button>
    <button @click="openValidationModal('edit')">수정</button>
    <button @click="openValidationModal('delete')">삭제</button>
  </template>

  <template v-if="pageStatus=='articleEditForm'">
    <span>카테고리</span><span style="padding: 30px">{{ article.categoryName }}</span> <br>
    <span>등록 일시</span><span style="padding: 30px">{{ article.dateCreated }}</span> <br>
    <span>수정 일시</span><span style="padding: 30px">{{ article.lastUpdated }}</span> <br>
    <span>조회수</span><span style="padding: 30px">{{ article.views }}</span> <br>
    <span>비밀번호</span><span style="color: red">*</span><input type="password"
                                                             v-model="articleEditForm.userInputPassword">
    <br>
    <span>작성자</span><span style="color: red">*</span><input type="text"
                                                            v-model="articleEditForm.writer"> <br>
    <span>제목</span><span style="color: red">*</span><input v-model="articleEditForm.title"><br>
    <span>내용</span><span style="color: red">*</span><input v-model="articleEditForm.content"><br>
    <span>파일</span><span style="padding: 30px">{{ article.fileAttached }}</span> <br>
    <button @click="closeEditForm">취소</button>
    <button @click="updateArticle">저장</button>
  </template>
</template>

<script>
import boardAPI from "@/boardAPI";

export default {
  name: "ArticleDetail",
  data() {
    return {
      /**
       * 수정/삭제 시 비밀번호 입력 모달
       *    @openStatus : 0 = 닫힘, 1 = 열림
       *    @userInputPassWord : 유저 입력 비밀번호
       *    @deleteOrEdit : 'delete' = '삭제' 버튼 클릭 시, 'edit' = 수정 버튼 클릭시
       */
      validationModal: {
        openStatus: 0,
        userInputPassword: '',
        deleteOrEdit: ''
      },
      /**
       * 게시글 수정 페이지에 사용될 게시글 정보가 바인딩 될 객체 (서버 전송용)
       *    @userInputPassword : 유저 입력 비밀번호
       *    @articleId : 게시글 번호
       *    @title : 제목
       *    @content : 내용
       *    @writer : 작성자
       */
      articleEditForm: {
        userInputPassword: '',
      },
      /**
       * 페이지 표시 상태 ( 'articleDetail' : 게시글 상세페이지, 'articleEditForm' : 게시글 수정페이지)
       */
      pageStatus: 'articleDetail',
      /**
       * 게시글 정보가 바인딩 될 객체
       */
      article: {},

      /**
       * 게시글 번호가 바인딩 될 변수
       */
      articleId: '',

      /**
       * 유저가 작성하는 댓글 내용이 바인딩 될 변수
       */
      commentContent: ''
    }
  },

  /**
   * 진입시 uri 에 있는 게시글 번호를 통해 게시글 정보 조회
   */
  mounted() {
    this.articleId = this.$route.params.id
    this.getArticleDetail(this.articleId)
  },
  methods: {
    /**
     * 수정 페이지에서 '저장' 버튼 클릭시 실행
     * 유저 입력 비밀번호 인증 후, 결과에 따라 게시글 수정
     */
    updateArticle() {
      boardAPI.checkArticlePassword(this.articleId, this.articleEditForm.userInputPassword).then(
          (response) => {
            if (response.data) {
              boardAPI.updateArticle(this.articleEditForm);
              window.location.replace(`/articles/${this.articleId}`)
            } else {
              alert('비밀번호가 틀렸습니다.')
              this.articleEditForm.userInputPassword = '';
            }
          })
    },
    /**
     * 게시글 수정 페이지에서 상세 페이지로
     */
    closeEditForm() {
      this.pageStatus = 'articleDetail'
    },
    /**
     * 게시글 상세 페이지에서 수정 페이지로
     */
    openEditForm() {
      this.pageStatus = 'articleEditForm'
    },
    /**
     * 비밀번호 입력 모달 닫기
     * 닫으며 입력값 초기화
     */
    closeValidationModal() {
      this.validationModal.userInputPassword = '';
      this.validationModal.deleteOrEdit = '';
      this.validationModal.openStatus = 0;
    },
    /**
     * 비밀번호 확인 모달에서 '확인' 버튼 클릭시 실행
     * 비밀번호 인증후 성공시 게시글 삭제 / 수정페이지 이동 실행
     *              실패시 경고 표시후 모달 닫기
     */
    checkModalPassword() {
      if (this.validationModal.openStatus != 1) {
        return;
      }
      boardAPI.checkArticlePassword(this.articleId, this.validationModal.userInputPassword).then(
          (response) => {
            if (response.data) {
              if (this.validationModal.deleteOrEdit === 'delete') {
                this.deleteArticle();
                this.closeValidationModal();
              } else if (this.validationModal.deleteOrEdit == 'edit') {
                this.closeValidationModal();
                this.articleEditForm.id = this.articleId
                this.articleEditForm.writer = this.article.writer
                this.articleEditForm.title = this.article.title
                this.articleEditForm.content = this.article.content
                this.openEditForm();
              }
            } else {
              alert('비밀번호가 틀렸습니다.')
              this.closeValidationModal();
            }
          })
    },
    /**
     * 비밀번호 입력 모달 열기
     * @param buttonName '삭제' / '수정'
     */
    openValidationModal(buttonName) {
      this.validationModal.openStatus = 1;
      this.validationModal.deleteOrEdit = buttonName;
    },
    /**
     * 게시글 삭제
     * 삭제 후 목록 페이지 전환
     */
    deleteArticle() {
      boardAPI.deleteArticle(this.article);
      window.location.replace("/")
    },
    /**
     * 게시글 정보 조회
     *    -- articleDeleted 가 1인 경우 boardAPI에서 false 반환,
     *       false 인 경우 목록 페이지로 전환
     */
    getArticleDetail(articleId) {
      boardAPI.getArticle(articleId).then((response) => {
        if (response == false) {
          window.location.replace("/")
        }
        this.article = response.data
      })
    },

    /**
     * 게시글 목록으로 가기
     */
    toArticleList() {
      this.$router.replace('/articles');
    },

    /**
     * 댓글 등록
     *    -- 게시글 번호와 유저가 작성한 댓글 내용을 기반으로 댓글 객체 생성하여 댓글 등록 요청
     *    -- 댓글 등록후 새로 게시글 데이터를 가져오면서 조회수가 증가되는 것을 방지하기 위해,
     *       현 페이지에 댓글 덧붙이기
     *       -- 여러 유저가 동시에 댓글을 등록할 땐 문제가 생김. DB 조회시 조회수 증가를 막거나,
     *           댓글 등록후 새로고침으로 하는 것이 나을 수도
     */
    registerComment() {
      let commentVO = {
        articleId: this.articleId,
        content: this.commentContent
      }
      boardAPI.insertComment(commentVO);

      commentVO.dateCreated = this.formatDate(new Date())
      this.article.commentList.push(commentVO)
      this.commentContent = ''
    },

    /**
     * Date 포맷 변경을 위한 함수
     */
    padTo2Digits(num) {
      return num.toString().padStart(2, '0');
    },

    /**
     * Date 포맷 변경을 위한 함수
     */
    formatDate(date) {
      return (
          [
            date.getFullYear(),
            this.padTo2Digits(date.getMonth() + 1),
            this.padTo2Digits(date.getDate()),
          ].join('-') +
          ' ' +
          [
            this.padTo2Digits(date.getHours()),
            this.padTo2Digits(date.getMinutes()),
            this.padTo2Digits(date.getSeconds()),
          ].join(':')
      );
    }
  }
}
</script>

<style scoped>

</style>