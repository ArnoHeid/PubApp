function json_text() {

var Parameter = '{"Startpunkt":"$_POST['Startpunkt']"}'
var obj = JSON.parse(text);

document.getElementbyId("demo").innerHTML = obj.Startpunkt;
}