var pool = require("./pool.js");
var HttpStatus = require("../../common/HttpStatus.js");
var ErrorType = require("../../common/ErrorType");
var Error = require("../../common/Error.js");
var FileReader = require('fs');


module.exports = class Loadable{
    constructor(queryPath){
        this.queryPath = queryPath;
    }

    insert(dataArray){
        var self = this;
        var promiseFunction = function(resolve, reject){

            pool.getConnection(function(err, connection){
                if(err){
                    var error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.DB_CONNECTION_ERROR);
                    reject(error);   
                } else{
                    //read query sql from file
                    FileReader.readFile(self.queryPath, 'utf8', function(err, sqlQuery){
                        if(err){
                            var error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.DB_CONNECTION_ERROR);
                            reject(error);
                        } else{
                            connection.query(sqlQuery, [dataArray], function(err, result){
                                
                                //console.log(result);
                                if(err){
                                    var error = new Error(HttpStatus.BAD_REQUEST, ErrorType.DB_QUERY_ERROR);
                                    connection.release();
                                    reject(error);
                                } else {
                                    connection.release();
                                    resolve(result.insertId);
                                }
                            });
                        }
                    });
                }
            });
        }
        return new Promise(promiseFunction);
    }

}