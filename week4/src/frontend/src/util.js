export default {
  /**
   * 게시글 입력값 유효성 검증
   * @param articleInput      입력 정보가 바인딩된 v-model 게시글 객체
   * @param articleInputRefs  입력 태그에 할당된 ref 모음
   */
  validateArticleInput : (articleInput, articleInputRefs) => {
    if (articleInput.categoryId == "") {
      alert("카테고리를 선택해주세요.")
      articleInputRefs.category.focus();
      return false;
    }

    //작성자
    if (articleInput.writer == "") {
      alert("작성자를 입력해주세요.")
      articleInputRefs.writer.focus();
      return false;
    } else if (articleInput.writer.length < 2 ||articleInput.writer.length > 4) {
      alert("작성자를 3글자 이상 5글자 미만으로 입력해주세요.")
      articleInputRefs.writer.focus();
      return false;
    }

    //비밀번호
    if (articleInput.password == "") {
      alert("비밀번호를 입력해주세요.")
      articleInputRefs.password.focus();
      return false;
    } else if (articleInput.password.length < 4 || articleInput.password.length > 16) {
      alert("비밀번호는 특수문자( '!,@,$,%,^,&,*' 만 허용), 영문 대소문자, 숫자를 포함하여 " +
          "4글자 이상 16글자 미만으로 입력해주세요.")
      articleInputRefs.password.focus();
      return false;
    } else if (!articleInput.password.match(/([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~])|([!,@,#,$,%,^,&,*,?,_,~].*[a-zA-Z0-9])/)) {
      alert("비밀번호는 특수문자( '!,@,$,%,^,&,*' 만 허용), 영문 대소문자, 숫자를 포함하여 " +
          "4글자 이상 16글자 미만으로 입력해주세요.")
      articleInputRefs.password.focus();
      return false;
    } else if (articleInput.password != articleInputRefs.passwordValidation.value) {
      alert("비밀번호 확인란에 동일한 비밀번호를 입력해주세요.")
      articleInputRefs.passwordValidation.focus();
      return false;
    }

    //제목
    if (articleInput.title == "") {
      alert("제목을 입력해주세요.")
      articleInputRefs.title.focus();
      return false;
    } else if (articleInput.title.length < 4 || articleInput.title.length > 100) {
      alert("제목을 4글자 이상 100글자 미만으로 입력해주세요.")
      articleInputRefs.title.focus();
      return false;
    }

    //내용
    if (articleInput.content == "") {
      alert("내용을 입력해주세요.")
      articleInputRefs.content.focus();
      return false;
    } else if (articleInput.content.length < 4 || articleInput.content.length > 2000) {
      alert("내용을 4글자 이상 2000글자 미만으로 입력해주세요.")
      articleInputRefs.content.focus();
      return false;
    }
  },
}

