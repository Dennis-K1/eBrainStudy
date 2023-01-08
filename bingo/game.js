import Player from './Player.js'
import Caller from './Caller.js'

const LAST_ROUND = 15;
const caller = new Caller(LAST_ROUND);

document.querySelector("#call").addEventListener("click", () => {
    caller.drawNumber();
})
document.querySelector("#newPlayer").addEventListener("click", () => {
    caller.generatePlayer();
})




