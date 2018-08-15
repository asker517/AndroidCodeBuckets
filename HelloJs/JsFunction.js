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

//map 变换

function change(args) {
    return args + 10;
}

var arr = [2, 4, 6];
arr.map(change);
arr.map(z => z * 8);

//reduce
function product(arr) {
    return arr.reduce((x, y) => x * y);
}

if (product([1, 2, 3, 4]) === 24 && product([0, 1, 2]) === 0 && product([99, 88, 77, 66]) === 44274384) {
    console.log('测试通过!');
} else {
    console.log('测试失败!');
}

//filter
arr.filter(x => x % 2 == 0);
arr.filter((Element, index, selft) => true);

//闭包
function close() {
    var arr = [];
    for (var i = 1; i <= 3; i++) {
        arr.push(function () {
            return i * i;
        })
    }
    return arr;
}
var results = close(); //[function(){return i*i},function(){return i*i},function(){return i*i}]
var r1 = results[0]; //r1() 16
var r2 = results[1]; // r2() 16
var r3 = results[2]; //r3() 16