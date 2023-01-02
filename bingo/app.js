let PLAYER_COUNT = 3;
let gameCount = 1;
const ORDER_OF_LAST_NUMBER = 15;

console.log("start")

class Player {
    numbers = [];
    bingoCount = 0;
    name;

    constructor(name) {
        let numberSet = new Set();
        while (numberSet.size < 5) {
            numberSet.add(Math.floor(Math.random() * 16)+1)
        }
        this.numbers = [...numberSet];
        this.name = name;
    }

    attendGame(caller) {
        caller.registerPlayer(this);
    }

    checkNumber(callerNumber) {
        let result = this.numbers.findIndex(number => number === callerNumber);
        if (result > -1) {
            this.numbers[result] = this.numbers[result] + `\u0336`;
            this.bingoCount++;

            if (this.bingoCount == 5) {
                this.numbers = ['B','I','N','G','O'];
            }
        }

    }
}

class Caller {
    numbers = [];
    players = [];

    constructor() {
        let numberSet = new Set();
        while (numberSet.size < ORDER_OF_LAST_NUMBER) {
            numberSet.add(Math.floor(Math.random() * 16)+1)
        }
        this.numbers = [...numberSet];
    }

    registerPlayer(player) {
        this.players.push(player);
    }

    play() {
        console.log('round' + gameCount)
        this.players.forEach((player => player.checkNumber(this.numbers[gameCount - 1])));
        gameCount++;
    }
}

const caller = new Caller();

const generatePlayer = () => {
    const $player = document.querySelector('#player');
    let player = new Player('testPlayer');
    player.attendGame(caller);
    $player.innerHTML = `
    <span>user numbers</span>
    <p >${caller.players.map(player => `<p style="font-size: 20px">${player.numbers}</p>`)}</p>
    <button id="newPlayer" onclick="generatePlayer()">newPlayer</button>
    `
}

const drawNumber = () => {
    if (gameCount === ORDER_OF_LAST_NUMBER) {
        document.body.innerHTML = `<p>gameOver</p>`;
        return;
    }
    const $gameCount = document.querySelector('#gameCount');
    $gameCount.innerHTML = `<span>${gameCount}</span>
                            <span>${caller.numbers}</span>`
    caller.play();
    const $player = document.querySelector('#player');
    $player.innerHTML = `
    <span>user numbers</span>
    <p>${caller.players.map(player => `<p style="font-size: 20px">${player.numbers}</p>`)}</p>
    <button id="newPlayer" onclick="generatePlayer()">newPlayer</button>
    `
}

console.log("test");