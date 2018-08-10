function myFunction(a, b) {
    return a + b;
}

//函数表达式
var x = function (a, b) { return a - b };
var z = x(1, 2);

//函数构造器
var test = new Function("a", "b", "return a+b");
test(3, 4);

//函数自调用
(function () {
    console.log("invoke self");
})();

(function (a, b) {
    return a * b;
}());

//函数也是对象,有自己的属性方法
function demo(a, b) {
    return arguments.length;
}

demo.toString(); //function demo(a,b){return arguments.length;}

//闭包函数
var add = (function () {
    var counter = 0;
    return function () { return counter += 1; };
})();
add();//1;
add();//2;
add();//3


