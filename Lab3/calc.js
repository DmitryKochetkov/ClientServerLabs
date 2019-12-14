function calculate() {
    var param1 = document.getElementById('param1').value;
    var op = document.getElementById('operation').value;
    var param2 = document.getElementById('param2').value;
    
    //I need request here
    document.getElementById('result').value = param1 + op + param2;
}