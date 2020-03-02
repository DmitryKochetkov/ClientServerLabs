function init() {
    var table = document.getElementById("users_table");
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open('GET', 'get_table', false);
    xmlhttp.send("get_table");
    if (xmlhttp.status == 200) {
        var res = xmlhttp.response + "";
        while (res.length > 0)
        {
            var sub = res.substr(0, res.indexOf("\n"));
            insert(sub);
            res = res.substr(res.indexOf("\n") + 1, res.length - 1);
        }
    }
}

function insert(res) {
    var table = document.getElementById("users_table");

    var row = document.createElement("tr");

        var td1 = document.createElement("td");
        var td2 = document.createElement("td");
        var td3 = document.createElement("td");
        var td4 = document.createElement("td");
        var td5 = document.createElement("td");
        var td6 = document.createElement("td");
        var td7 = document.createElement("td");

        td1.appendChild(document.createTextNode(res.substr(0, res.indexOf(" "))));
        res = push_first_word(res);
        td2.appendChild(document.createTextNode(res.substr(0, res.indexOf(" "))));
        res = push_first_word(res);
        td3.appendChild(document.createTextNode(res.substr(0, res.indexOf(" "))));
        res = push_first_word(res);
        td4.appendChild(document.createTextNode(res.substr(0, res.indexOf(" "))));
        res = push_first_word(res);
        td5.appendChild(document.createTextNode(res.substr(0, res.indexOf(" "))));
        res = push_first_word(res);
        td6.appendChild(document.createTextNode(res));
        td7.appendChild(document.createTextNode("Изменить"));
        
    
        row.appendChild(td1);
        row.appendChild(td2);
        row.appendChild(td3);
        row.appendChild(td4);
        row.appendChild(td5);
        row.appendChild(td6);
        row.appendChild(td7);
        table.appendChild(row);
}

function update() {
    var table = document.getElementById("users_table");
    var id = document.getElementById("num2").value;
    var row = table.rows[id];
    row.cells[0].innerHTML = document.getElementById("name").value;
    row.cells[1].innerHTML = document.getElementById("surname").value;
}

function remove() {
    var table = document.getElementById("users_table");
    var id = document.getElementById("num").value;
    table.deleteRow(id);
}

function add() {
    insert(document.getElementById("new_name").value + " " +
        document.getElementById("new_surname").value + " " +
        document.getElementById("new_login").value + " " +
        document.getElementById("new_pass").value + " " +
        document.getElementById("new_sex").value +
        Date()
    )
}

function push_first_word(s) {
    var r = s.indexOf(" ");
    var sub = s.substr(0, r);
    sub = s.substr(r+1, s.length - 1);
    return sub;
}