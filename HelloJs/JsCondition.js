//if...else
function demo(age) {
    if (age > 20) {
        console.log("man");
    } else {
        console.log("child")
    }
}

//switch
var day = new Date().getDay;
switch (day) {
    case 0: console.log("0"); break;
    case 1: console.log("1"); break;
    default: break;
}

//for 
function testFor() {
    for (var i = 0; i <= 100; i++) {
        console.log(i);
    }
}

var person = { name: "vv", age: 20 };
var x;
var txt;
for (x in person) {
    txt = txt + person[x];
}

//while
var y = 0;
while (y < 10) {
    y++;
}