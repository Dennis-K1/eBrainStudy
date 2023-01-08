import Player from "./Player.js";

/**
 * 게임 Caller OBSERVABLE
 */
export default class Caller {
    /**
     * Caller가 부를 숫자 (총갯수 = 마지막 라운드)
     */
    numbers = [];

    /**
     * 게임에 참가한 Player 목록 (Observer 목록)
     */
    players = [];

    /**
     * 현재 게임 회차
     */
    gameRound;

    /**
     * 마지막 게임 회차 (초과시 Game Over)
     */
    LAST_ROUND;

    /**
     * Caller 생성
     *
     * 생성시 gameRound 1 지정, LAST_ROUND 수에 맞추어 숫자 생성
     * @param LAST_ROUND 마지막 게임 회차 Game.js 에서 지정
     */
    constructor(LAST_ROUND) {
        this.gameRound = 1;
        this.LAST_ROUND = LAST_ROUND;
        this.numbers = this.#chooseNumbers();
    }

    /**
     * 게임에 참가하는 Player 등록 (Observers List에 추가)
     * @param player Player 객체
     */
    registerPlayer(player) {
        this.players.push(player);
    }

    /**
     * 게임 1회 실행후 게임 회차 증가
     * Caller 가 숫자를 부르면 매 Player 가 자기의 숫자와 비교하여 BINGO여부 판단
     */
    play() {
        let calledNumber = this.numbers[this.gameRound - 1];
        this.players.forEach((player => player.play(calledNumber)));
        this.gameRound++;
    }

    /**
     * LAST_ROUND에 맞추어 Caller의 숫자 생성
     */
    #chooseNumbers = () => {
        let numberSet = new Set();
        while (numberSet.size < this.LAST_ROUND) {
            numberSet.add(Math.floor(Math.random() * 15) + 1)
        }
        return [...numberSet];
    }

    /**
     * Caller 정보 화면 표시
     */
    renderCaller = () => {
        let $gameCount = document.querySelector('#gameCount');
        $gameCount.innerHTML = `<span>${this.gameRound}</span>
                            <span>${this.numbers}</span>`
    }

    /**
     * Player 정보 화면 표시
     */
    renderPlayers = () => {
        let $player = document.querySelector('#player');
        $player.innerHTML =
            `
        <span>user numbers</span>
        <p >${this.players.map(player => `<p style="font-size: 20px">${player.numbers}</p>`)}</p>
        <button id="newPlayer">newPlayer</button>
        `
        let $newPlayer = document.querySelector('#newPlayer');
        $newPlayer.addEventListener('click', () => {
            this.generatePlayer();
        })
    }

    /**
     * 숫자 뽑아 게임 진행
     */
    drawNumber = () => {
        if (this.isGameOver()) {
            document.body.innerHTML = `<p>gameOver</p>`;
            return;
        }
        this.renderCaller();
        this.play();

        this.renderPlayers()
    }

    /**
     * 게임 오버 여부 확인
     */
    isGameOver = () => {
        if (this.gameRound === this.LAST_ROUND) {
            return true
        }
    }

    /**
     * Player 생성 후 Caller 리스트에 추가
     */
    generatePlayer = () => {
        let player = new Player();
        player.attendGame(this);
        this.renderPlayers();
    }
}

