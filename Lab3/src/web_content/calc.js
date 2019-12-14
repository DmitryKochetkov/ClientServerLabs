function calculate() {
    var param1 = document.getElementById('param1').value;
    var op = document.getElementById('operation');
    var param2 = document.getElementById('param2').value;
    
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open('POST', param1 + op[op.selectedIndex].value + param2, false);
    xmlhttp.send(param1 + operation + param2);
    if (xmlhttp.status == 200) {
        document.getElementById('result').value = xmlhttp.response;
    }
}