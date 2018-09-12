'use strict'

var sayHello = require('./hellonode');

var name = "Vincent";

sayHello(name);

var fs = require('fs');
fs.readFile('hellonode.js', 'utf-8', function (err, data) {
    if (err) {
        console.log(err);
    } else {
        console.log(data);
    }
});

var content = 'this is just node write file test';
fs.writeFile('node write.js', content, function (err) {
    if (err) {
        console.log(err);
    } else {
        console.log('write DONE');
    }
});