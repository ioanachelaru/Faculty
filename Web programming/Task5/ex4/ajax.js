var arrayGame = ['X', 'O'];
var userMove;
var computerMove;
var completed = [0, 0, 0, 0, 0, 0, 0, 0, 0];

function startGame() {
    userMove = arrayGame[~~(arrayGame.length * Math.random())];
    if (userMove === 'X')
        computerMove = 'O';
    else
        computerMove = 'X';

    if (computerMove === 'X') {
        doComputerRequest();
    }
}

function doComputerRequest() {
    verifyEndGame();
    var request;
    request = new XMLHttpRequest();
    request.onreadystatechange = function () {
        if (request.readyState === 4) { // cerere rezolvata
            if (request.status === 200) { // raspuns Ok


                console.log('response',JSON.parse(request.responseText.toString()));

                var computerResponse = JSON.parse(request.responseText);
                console.log(computerResponse);

                document.getElementById('td' + computerResponse['row'] + computerResponse['column']).innerHTML = computerMove;
                completed[computerResponse['nr'].toString()] = computerMove;

                verifyEndGame();

            }
            else
                alert('Eroare request.status: ' + request.status);

        }
    };

    console.log('json');
    var delimiter='^';
    console.log(completed.join(delimiter));
    request.open('GET', 'doComputerMove.php?completed=' + completed.join(delimiter) + '&move=' + computerMove);
    request.send('');
}

function doUserMove(id) {
    var elem = document.getElementById(id);
    var row = parseInt(id.slice(2, 3));
    var column = parseInt(id.slice(3, 4));
    var nr;
    var moveDone=false;
    if (row === 0 && completed[column] === 0) {
        nr = column;
        elem.innerHTML = userMove;
        completed[nr]=userMove;
        moveDone=true;
    }
    else {
        if (row === 1 && completed[3 + column] === 0) {
            elem.innerHTML = userMove;
            nr = 3 + column;
            completed[nr]=userMove;
            moveDone=true;
        }
        else {
            if (row === 2 && completed[6 + column] === 0) {
                nr = 6 + column;
                elem.innerHTML = userMove;
                completed[nr]=userMove;
                moveDone=true;
            }
        }
    }
    if (moveDone) {
        doComputerRequest();
    }



}

function verifyEndGame() {
    var request;
    request = new XMLHttpRequest();
    request.onreadystatechange = function () {
        if (request.readyState === 4) { // cerere rezolvata
            if (request.status === 200) { // raspuns Ok


                var computerResponse = JSON.parse(request.responseText);
                console.log('response:',computerResponse);

                if (computerResponse===1) {
                    alert("Game ended.");
                    return true;
                }

            }
            else
                alert('Eroare request.status: ' + request.status);
        }
    };

    var delimiter='^';
    request.open('GET', 'verifyEndGame.php?completed=' + completed.join(delimiter));
    request.send('');
    return false;
}