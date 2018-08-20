//constuctor
function Person(name) {
    this.name = name;
    this.hello = function () {
        console.log(name + "say hello");
    }
}
var p1 = new Person("vincent");
p1.name;
p1.hello();

function Cat(name) {
    this.name = name;
}

Cat.prototype.say = function () {
    return "Hello, " + this.name;
}

//class
class Person {
    constructor(name) {
        this.name = name;
    }
    hello() {
        console.log("say hello ");
    }
}

class Animal {
    constructor(name) {
        this.name = name;
    }
}

class Cat extends Animal {
    constructor(name) {
        super(name);
        this.name = name;
    }

    say() {
        return "Hello, " + this.name + "!";
    }
}