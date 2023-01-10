import { createStore } from 'vuex'
import boardAPI from "@/boardAPI";

const store = createStore({
    state(){
        return {
            boardVO : {}
        }
    },
    mutations : {
        setBoardVO(state, data) {
            state.boardVO = data
        }
    },
    actions : {
        fetchBoardVO(context, searchVO){
            boardAPI.getBoardVO(searchVO).then((response) => {
                context.commit('setBoardVO',response.data)
            })
        }
    }
})

export default store