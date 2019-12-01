/*
for (i = 0; i < toggler.length; i++) {
    toggler[i].addEventListener("click", function () {
        this.parentElement.querySelector(".nested").classList.toggle("active");
        this.classList.toggle("caret-down");
    });
}
*/

function addOnClick(dir, li) {
    if (li.className === "caret") {
        li.onclick = function () {
            var ul = document.createElement("ul");
            var newDir = dir + "/" + li.firstChild.nodeValue;
            doStructureRequest(newDir, ul);
            li.appendChild(ul);
        }
    }
    else {
        console.log("am gasit");
    }


}

function doStructureRequest(dir, ul) {
    var request;
    request = new XMLHttpRequest();
    request.onreadystatechange = function () {
        if (request.readyState === 4) { // cerere rezolvata
            if (request.status === 200) { // raspuns Ok

                var array = JSON.parse(request.responseText);
                console.log(array);
                var i;
                var li;
                for (i = 0; i < array.files.length; i++) {
                    li = document.createElement('li');
                    li.appendChild(document.createTextNode(array.files[i]));
                    li.className = "file";
                    (function () {

                        var fileName = array.files[i];
                        var lii=li;
                        li.onclick = function () {
                            getFileContent(lii, dir + '/' + fileName);
                        };
                        ul.appendChild(li);
                    })();
                }
                for (i = 0; i < array.directories.length; i++) {
                    li = document.createElement('li');
                    li.appendChild(document.createTextNode(array.directories[i]));
                    li.className = 'caret';
                    ul.appendChild(li);
                    addOnClick(dir, li);
                }

            }


            else
                alert('Eroare request.status: ' + request.status);
        }
    }
    ;

    request.open('GET', 'getDirectories.php?dir=' + dir);
    request.send('');

}

function getFileContent(li, fileName) {
    var request;
    request = new XMLHttpRequest();
    request.onreadystatechange = function () {
        if (request.readyState === 4) { // cerere rezolvata
            if (request.status === 200) { // raspuns Ok

                var response = JSON.parse(request.responseText);
                console.log(response);

                var div = document.createElement("div");
                div.innerHTML=response;
                li.appendChild(div);
            }


            else
                alert('Eroare request.status: ' + request.status);
        }
    };
    request.open('GET', 'getContent.php?file=' + fileName);
    request.send('');
};

function wrapper(dir) {

    var ul = document.getElementById("myUl");
    doStructureRequest(dir, ul);
}
