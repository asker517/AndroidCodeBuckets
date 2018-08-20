//Date
var now = new Date();
console.log(now);
now.getFullYear;
now.getDay;
now.getHours;
now.getSeconds;
now.getTime;

var d = new Date(2018, 6, 30);//月份从0开始 0代表1月
var d1 = Date.parse("2015-06-24T19:49:22.875+08:00");
var d2 = new Date(143222222222);

//reg

//JSON
var person = {
    name: "Vincent",
    age: 14,
    gender: "female"
}
JSON.stringify(person);
JSON.stringify(person, ['name', 'age'], '  '); //指定格式,和输入指定键值

function handler(key, value) {
    if (typeof value === 'string') {
        return value.toUpperCase;
    }
    return value;
}
JSON.stringify(person, handler, '  '); //传入函数,测每一个键值都会被函数处理后输出