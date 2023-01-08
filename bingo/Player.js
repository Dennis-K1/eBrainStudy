/**
 * 게임 Player OBSERVER
 */
export default class Player {
    /**
     * Player의 숫자 5개
     */
    numbers = [];

    /**
     * 빙고 여부 (5가 될 시 BINGO)
     */
    bingoCount = 0;

    /**
     * 생성시 5개 랜덤 숫자 생성
     */
    constructor() {
        this.numbers = this.#chooseNumbers();
    }

    /**
     * 게임 참가 caller 의 ObserverList 에 등록
     */
    attendGame(caller) {
        caller.registerPlayer(this);
    }

    /**
     * 게임 실행
     * Caller가 주는 숫자와 자신의 숫자 비교후 빙고 여부 확인
     */
    play(callerNumber) {
        this.checkNumber(callerNumber);
        this.checkBingo();
    }

    /**
     * Caller의 숫자가 자신의 숫자 목록에 있는지 확인
     */
    checkNumber(callerNumber) {
        let resultIndex = this.numbers.findIndex(number => number === callerNumber);
        if (resultIndex > -1) {
            this.numbers[resultIndex] = this.numbers[resultIndex] + `\u0336`;
            this.bingoCount++;
        }
    }

    /**
     * 빙고여부 확인, 빙고면 숫자 BINGO로 변경
     */
    checkBingo() {
        if (this.bingoCount == 5) {
            this.numbers = ['B','I','N','G','O'];
        }
    }

    /**
     * 5가지 랜덤 숫자 생성
     */
    #chooseNumbers = () => {
        let numberSet = new Set();
        while (numberSet.size < 5) {
            numberSet.add(Math.floor(Math.random() * 15)+1)
        }
        return [...numberSet];
    }

    render = () => {
        let $player = document.querySelector('#player');
        $player.innerHTML = `
    <span>user numbers</span>
   <p style="font-size: 20px">${this.numbers}</p>
    <button id="newPlayer">newPlayer</button>
    `
        let $newPlayer = document.querySelector('#newPlayer');
        $newPlayer.addEventListener('click', () => {
            generatePlayer();
        })
    }
}

