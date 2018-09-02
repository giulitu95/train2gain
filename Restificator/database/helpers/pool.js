var mysql = require('mysql');
var fs = require('fs');

var pool = mysql.createPool({
            //connectionLimit: 
            
            host: "185.25.206.189",
            user: "giulianoturri",
            password: "Giu95Tur*180063",
            database: "giulianoturri",
            ssl      : true 
            /*
            host: "localhost",
            user: "root",
            password: "password",
            database: "train2gain"
            */
        });

module.exports = pool;