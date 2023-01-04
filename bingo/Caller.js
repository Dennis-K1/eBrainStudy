export default class Caller {
    numbers = [];
    players = [];
    gameRound;
    LAST_ROUND;

    constructor(LAST_ROUND) {
        this.gameRound = 1;
        this.LAST_ROUND = LAST_ROUND;
        this.numbers = this.#chooseNumbers();
    }

    registerPlayer(player) {
        this.players.push(player);
    }

    play() {
        let calledNumber = this.numbers[this.gameRound - 1];
        this.players.forEach((player => player.play(calledNumber)));
        this.gameRound++;
    }

    #chooseNumbers = () => {
        let numberSet = new Set();
        while (numberSet.size < this.LAST_ROUND) {
            numberSet.add(Math.floor(Math.random() * 15)+1)
        }
        return [...numberSet];
    }
}

