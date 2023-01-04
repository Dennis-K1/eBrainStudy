import Player from './Player.js'
import Caller from './Caller.js'

const LAST_ROUND = 15;
const caller = new Caller(LAST_ROUND);

const generatePlayer = () => {
        let player = new Player('testPlayer');
        player.attendGame(caller);
        let $player = document.querySelector('#player');
        $player.innerHTML = `
    <span>user numbers</span>
    <p >${caller.players.map(player => `<p style="font-size: 20px">${player.numbers}</p>`)}</p>
    <button id="newPlayer">newPlayer</button>
    `
    let $newPlayer = document.querySelector('#newPlayer');
    $newPlayer.addEventListener('click', () => {
        generatePlayer();
    })
    }
const drawNumber = () => {
        if (caller.gameRound === LAST_ROUND) {
            document.body.innerHTML = `<p>gameOver</p>`;
            return;
        }
        const $gameCount = document.querySelector('#gameCount');
        $gameCount.innerHTML = `<span>${caller.gameRound}</span>
                            <span>${caller.numbers}</span>`
        caller.play();
        const $player = document.querySelector('#player');
        $player.innerHTML = `
    <span>user numbers</span>
    <p>${caller.players.map(player => `<p style="font-size: 20px">${player.numbers}</p>`)}</p>
    <button id="newPlayer" onclick="generatePlayer()">newPlayer</button>
    `
    }

document.querySelector("#call").addEventListener("click", () => {
    drawNumber();
})
document.querySelector("#newPlayer").addEventListener("click", () => {
    generatePlayer();
})




