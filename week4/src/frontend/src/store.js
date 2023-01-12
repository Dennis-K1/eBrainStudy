import {createStore} from 'vuex'
import boardAPI from "@/boardAPI";

const store = createStore({
    state() {
        return {
            /**
             * 조회한 boardVO 가 바인딩 될 Object.
             * pageNumber 를 미리 지정하지 않으면 ArticleBoard Pagination 부분에서 경고/에러 발생
             */
            boardVO: {
                searchVO: {
                    pageNumber: 1
                }
            },
        }
    },
    mutations: {
        setBoardVO(state, data) {
            state.boardVO = data
        },
    },
    actions: {
        fetchBoardVO(context, searchVO) {
            boardAPI.getBoardVO(searchVO).then((response) => {
                context.commit('setBoardVO', response.data)
            })
        }
    }
})

export default store