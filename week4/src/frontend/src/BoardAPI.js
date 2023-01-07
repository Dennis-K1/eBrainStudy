import axios from "axios";

axios.defaults.headers.get['Content-Type'] = 'application/json;charset=utf-8';
axios.defaults.headers.post['Content-Type'] = 'application/json;charset=utf-8';
axios.defaults.headers.put['Content-Type'] = 'application/json;charset=utf-8';
axios.defaults.headers.delete['Content-Type'] = 'application/json;charset=utf-8';

const API_URL = process.env.VUE_APP_API_URL
const ARTICLES = "articles"
const COMMENT = "comment"

export default {
    getBoardVO : (searchVO) => {
      return axios.get(API_URL + ARTICLES,{params:searchVO});
    },
    insertArticle : (articleVO) => {
      return axios.post(API_URL + ARTICLES, articleVO);
    },
    getArticle : (articleId) => {
      return axios.get(API_URL + ARTICLES + `/${articleId}`);
    },
    updateArticle : (articleVO) => {
      return axios.put(API_URL + ARTICLES, articleVO);
    },
    deleteArticle : (articleVO) => {
      return axios.delete(API_URL + ARTICLES, {data:articleVO});
    },
    insertComment : (commentVO) => {
        return axios.post(API_URL + ARTICLES + '/' + COMMENT, commentVO);
    }
}