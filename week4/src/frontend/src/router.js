import {createWebHistory, createRouter} from "vue-router";

import ArticleDetail from "@/components/ArticleDetail";
import ArticleBoard from "@/components/ArticleBoard.vue";


const routes = [
    {
        path: '/',
        name: 'home',
        redirect: 'articles'
    },
    {
        path: '/articles',
        name: 'articleBoard',
        component: ArticleBoard,
    },
    {
        path: '/articles/:id',
        name: 'articleDetail',
        component: ArticleDetail
    },
]

const router = createRouter({
    history: createWebHistory(),
    routes: routes
});

export default router;