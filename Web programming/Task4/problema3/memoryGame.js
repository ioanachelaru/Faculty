// JavaScript Document

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
function newBoard(){
    tiles_flipped = 0;
    memory_array.memory_tile_shuffle();
    for(let i = 0; i < memory_array.length; i++)
    {
        $('<div id="tile_'+i+'"></div>').bind("click",function()
        {
            memoryFlipTile($(this),memory_array[$(this).attr('id').replace('tile_', '')]);
        }).appendTo($("#memory_board"));

    }
}
function memoryFlipTile(tile,val){
    tile = tile[0];
    if(tile.innerHTML === "" && memory_values.length < 2){
        tile.style.background = '#FFF';
        tile.innerHTML = val;
        if(memory_values.length === 0){
            memory_values.push(val);
            memory_tile_ids.push(tile.id);
        } else if(memory_values.length === 1){
            memory_values.push(val);
            memory_tile_ids.push(tile.id);
            if(memory_values[0] === memory_values[1]){
                tiles_flipped += 2;
                memory_values = [];
                memory_tile_ids = [];
                if(tiles_flipped === memory_array.length){
                    alert("GG, facem alta");
                    $("#memory_board").html("");
                    newBoard();
                }
            } else {
                function flip2Back(){
                    let tile_1 = $("#"+memory_tile_ids[0]);
                    let tile_2 = $("#"+memory_tile_ids[1]);
                    tile_1.css('background-color','');
                    tile_1.html("");
                    tile_2.css('background-color','');
                    tile_2.html("");
                    memory_values = [];
                    memory_tile_ids = [];
                }
                setTimeout(flip2Back, 700);
            }
        }
    }
}