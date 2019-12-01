let memory_array = ['1','1','2','2','3','3','4','4','5','5','6','6','7','7','8','8'];
let memory_values = [];
let memory_tile_ids = [];
let tiles_flipped = 0;
Array.prototype.memory_tile_shuffle = function(){
    let i = this.length, j, temp;
    while(--i > 0){
        j = Math.floor(Math.random() * (i+1));
        temp = this[j];
        this[j] = this[i];
        this[i] = temp;
    }
};
function getURLFromName(e)
{
    if (e === "1")
    {
        return "http://www.cs.ubbcluj.ro/wp-content/uploads/Suciu-Mihai.jpg";
    }
    else if (e === "2")
    {
        return "http://www.cs.ubbcluj.ro/wp-content/uploads/Mihis-Andreea.jpg";
    }
    else if (e === "3")
    {
        return "http://www.cs.ubbcluj.ro/wp-content/uploads/Czibula-Istvan.jpg";
    }
    else if (e === "4")
    {
        return "http://www.cs.ubbcluj.ro/wp-content/uploads/Diosan-Laura.jpg";
    }
    else if (e === "5")
    {
        return "http://www.cs.ubbcluj.ro/wp-content/uploads/Grigoreta-Cojocar.jpg";
    }
    else if (e === "6")
    {
        return "http://www.cs.ubbcluj.ro/wp-content/uploads/Bufnea-Darius.jpg";
    }
    else if (e === "7")
    {
        return "http://www.cs.ubbcluj.ro/wp-content/uploads/Serban-Camelia.jpg";
    }
    else if (e === "8")
    {
        return "http://www.cs.ubbcluj.ro/wp-content/uploads/Marian-Zsuzsanna.jpg";
    }
}
function newBoard(){
    tiles_flipped = 0;
    let output = '';
    memory_array.memory_tile_shuffle();
    for(let i = 0; i < memory_array.length; i++){
        output += '<div id="tile_'+i+'" onclick="memoryFlipTile(this,\''+memory_array[i]+'\')"></div>';
    }
    $("#memory_board").html(output);
}
function memoryFlipTile(tile,val){
    if(tile.innerHTML === "" && memory_values.length < 2){
        tile.style.background = "url("+getURLFromName(val)+") no-repeat";
        //tile.innerHTML = val;
        if(memory_values.length === 0){
            memory_values.push(val);
            memory_tile_ids.push(tile.id);
        } else if(memory_values.length === 1){
            memory_values.push(val);
            memory_tile_ids.push(tile.id);
            if(memory_values[0] === memory_values[1]){
                tiles_flipped += 2;
                // Clear both arrays
                memory_values = [];
                memory_tile_ids = [];
                // Check to see if the whole board is cleared
                if(tiles_flipped === memory_array.length){
                    alert("Board cleared... generating new board");
                    document.getElementById('memory_board').innerHTML = "";
                    newBoard();
                }
            } else {
                function flip2Back(){
                    // Flip the 2 tiles back over
                    let tile_1 = $("#"+memory_tile_ids[0]);
                    let tile_2 = $("#"+memory_tile_ids[1]);
                    tile_1.css('background','url(tile-bg.jpg) no-repeat');
                    tile_1.html("");
                    tile_2.css('background','url(tile-bg.jpg) no-repeat');
                    tile_2.html("");
                    memory_values = [];
                    memory_tile_ids = [];
                }
                setTimeout(flip2Back, 700);
            }
        }
    }
}