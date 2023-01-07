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


export default {

  /**
   * 게시판 정보 조회 (게시글 목록, 카테고리 목록, 검색 조건, 검색 조건 기반 총 게시글 수)
   *
   * @param searchVO 검색 조건
   * @returns 게시판 정보 JSON
   */
  getBoardVO : (searchVO) => {
    return axios.get(API_URL + ARTICLES,{params:searchVO});
  },

  /**
   * 게시글 삽입(등록)
   *
   * @param articleVO 대상 게시글 정보
   */
  insertArticle : (articleVO) => {
    return axios.post(API_URL + ARTICLES, articleVO);
  },

  /**
   * 게시글 조회
   *
   * @param articleId 대상 게시글 번호
   */
  getArticle : (articleId) => {
    return axios.get(API_URL + ARTICLES + `/${articleId}`);
  },

  /**
   * 게시글 수정
   *
   * @param articleVO 대상 게시글 번호와 정보
   */
  updateArticle : (articleVO) => {
    return axios.put(API_URL + ARTICLES, articleVO);
  },

  /**
   * 게시글 삭제
   *
   * @param articleVO 대상 게시글 번호와 정보
   */
  deleteArticle : (articleVO) => {
    return axios.delete(API_URL + ARTICLES, {data:articleVO});
  },

  /**
   * 댓글 삽입(등록)
   *
   * @param commentVO 대상 게시글 번호와 댓글 내용
   */
  insertComment : (commentVO) => {
      return axios.post(API_URL + ARTICLES + '/' + COMMENT, commentVO);
  }
}
