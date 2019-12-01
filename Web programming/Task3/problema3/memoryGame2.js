var matches = 0;
const cards = document.querySelectorAll('.memory-card');

let hasFlippedCard = false;
let lockBoard = false;
let firstCard, secondCard;

shuffle();

function flipCard() {
    if (lockBoard) return;
    if (this === firstCard) return;

    this.classList.add('flip');

    if (!hasFlippedCard) {
        // first click
        hasFlippedCard = true;
        firstCard = this;
        return;
    }
    // second click
    secondCard = this;
    checkForMatch();
    setTimeout(()=>{if (matches === 8){
        alert("Well done! :)");
        restart();
        shuffle();
    }}, 250);
}

function restart(){
    cards.forEach((card => card.classList.remove('flip')));
    cards.forEach(card => card.addEventListener('click', flipCard));
}

function checkForMatch() {
    let isMatch = firstCard.dataset.image === secondCard.dataset.image;

    isMatch ? disableCards() : unflipCards();
}

function disableCards() {
    firstCard.removeEventListener('click', flipCard);
    secondCard.removeEventListener('click', flipCard);

    resetBoard();
    matches ++;
}

function unflipCards() {
    lockBoard = true;

    setTimeout(() => {
        firstCard.classList.remove('flip');
        secondCard.classList.remove('flip');

        resetBoard();
    }, 1500);
}

function resetBoard() {
    [hasFlippedCard, lockBoard] = [false, false];
    [firstCard, secondCard] = [null, null];
}

function shuffle() {
    cards.forEach(card => {
        card.style.order = String(Math.floor(Math.random() * 12));
    });
}

cards.forEach(card => card.addEventListener('click', flipCard));