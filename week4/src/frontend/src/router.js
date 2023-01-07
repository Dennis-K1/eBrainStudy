import { createWebHistory, createRouter } from "vue-router";

import ArticleList from "@/components/ArticleList";


const routes = [
  { path : '/articles',
    name : 'articles',
    component : ArticleList,
    beforeEach : function (to,from) {
      console.log(to)
      console.log(from)
    }
  }
]

const router = createRouter({
  history : createWebHistory(),
  routes : routes
});

export default router;