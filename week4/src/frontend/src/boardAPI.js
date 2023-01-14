import axios from "axios";

axios.defaults.headers.get['Content-Type'] = 'application/json;charset=utf-8';
axios.defaults.headers.post['Content-Type'] = 'application/json;charset=utf-8';
axios.defaults.headers.put['Content-Type'] = 'application/json;charset=utf-8';
axios.defaults.headers.delete['Content-Type'] = 'application/json;charset=utf-8';

/**
 *게시판 API 서버 주소
 */
const API_URL = process.env.VUE_APP_API_URL

/**
 *게시글 관련 요청 경로명
 */
const ARTICLES = "articles"

/**
 * 댓글 관련 요청 경로명
 */
const COMMENT = "comment"

/**
 * 파일 관련 요청 경로명
 */
const FILE = "file"

export default {

  /**
   * 게시판 정보 조회 (게시글 목록, 카테고리 목록, 검색 조건, 검색 조건 기반 총 게시글 수)
   *
   * @param searchVO 검색 조건
   * @returns 게시판 정보 JSON
   */
  getBoardVO: (searchVO) => {
    return axios.get(API_URL + ARTICLES, {params: searchVO});
  },

  /**
   * 게시글 삽입(등록)
   *
   * @param articleVO 대상 게시글 정보
   */
  insertArticle: (articleVO) => {
    return axios.post(API_URL + ARTICLES, articleVO, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    });
  },

  /**
   * 게시글 조회
   * 게시글 삭제 상태인 경우 false 반환
   * @param articleId 대상 게시글 번호
   */
  getArticle: async (articleId) => {
    let article = await axios.get(API_URL + ARTICLES + `/${articleId}`);
    let articleDeleted = 1;
    if (articleDeleted == article.data.articleDelete || article.data == '') {
      return false;
    }
    return article
  },

  /**
   * 게시글 수정
   *
   * @param articleVO 대상 게시글 번호와 정보
   */
  updateArticle: (articleVO) => {
    return axios.put(API_URL + ARTICLES, articleVO,
        {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        });
  },

  /**
   * 게시글 삭제
   *
   * @param articleVO 대상 게시글 번호와 정보
   */
  deleteArticle: (articleVO) => {
    return axios.delete(API_URL + ARTICLES, {data: articleVO});
  },

  /**
   * 댓글 삽입(등록)
   *
   * @param commentVO 대상 게시글 번호와 댓글 내용
   */
  insertComment: (commentVO) => {
    return axios.post(API_URL + ARTICLES + '/' + COMMENT, commentVO);
  },

  /**
   * 유저 입력 게시글 비밀번호와 서버에 저장된 원 게시글 비교 인증
   *
   * @param articleId 대상 게시글 번호
   * @param userInputPassword 유저 입력 비밀번호
   */
  checkArticlePassword: (articleId, userInputPassword) => {
    return axios.post(API_URL + ARTICLES + `/password`,
        {id: articleId, password: userInputPassword})
  },

  /**
   * 파일 다운로드
   * 서버에 요청후 링크 생성하여 다운로드 진행후 링크 삭제
   *
   * @param fileVO 다운로드 진행할 파일 정보 객체
   */
  downloadFile: (fileVO) => {
    return axios.post(API_URL + ARTICLES + '/' + FILE, fileVO,
        {responseType: "blob"}).then(response => {
      const url = window.URL.createObjectURL(
          new Blob([response.data], {type: response.headers['content-type']}));
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', fileVO.originalName);
      document.body.appendChild(link);
      link.click();
      link.remove();
    })
  }
}
