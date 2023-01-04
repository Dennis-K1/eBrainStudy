export default class Player {
    numbers = [];
    bingoCount = 0;
    playerName;

    constructor(playerName) {
        this.numbers = this.#chooseNumbers();
        this.playerName = playerName;
    }

    attendGame(caller) {
        caller.registerPlayer(this);
    }

    play(callerNumber) {
        this.checkNumber(callerNumber);
        this.checkBingo();
    }
    checkNumber(callerNumber) {
        let resultIndex = this.numbers.findIndex(number => number === callerNumber);
        if (resultIndex > -1) {
            this.numbers[resultIndex] = this.numbers[resultIndex] + `\u0336`;
            this.bingoCount++;
        }
    }
    checkBingo() {
        if (this.bingoCount == 5) {
            this.numbers = ['B','I','N','G','O'];
        }
    }
    #chooseNumbers = () => {
        let numberSet = new Set();
        while (numberSet.size < 5) {
            numberSet.add(Math.floor(Math.random() * 15)+1)
        }
        return [...numberSet];
    }
}

