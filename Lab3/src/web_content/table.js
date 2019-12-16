function init() {
    var table = document.getElementById("users_table");
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open('GET', 'get_table', false);
    xmlhttp.send("get_table");
    if (xmlhttp.status == 200) {
        var res = xmlhttp.response + "";
        table.innerHTML += res;
    }
}

function insert() {

}

function update() {

}

function remove(id) {

}