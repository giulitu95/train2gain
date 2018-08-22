var pool = require("./pool.js");
var HttpStatus = require("../../common/HttpStatus.js");
var ErrorType = require("../../common/ErrorType");
var Error = require("../../common/Error.js");
var LoadEntity = require("../entities/Load.js");
var FileReader = require('fs');


module.exports = class Loads{
    getLastLoadByQuery(result){
        if(result.length == 0){
            return null
        } else{
            var loads = [];
            for(var i = 0; i < result.length; i++){
                var load = new LoadEntity(result[i].weight_id, result[i].weight_weight, result[i].weight_scheduleItemId, result[i].weight_date);
                loads.push(load);
            }
            //console.log(JSON.stringify(loads, null, 4));
            return loads;
        }
    }
    getLoadByScheduleAndLastUpdate(scheduleId, lastUpdate){
        var self = this;
        var promiseFunction = function(resolve, reject){

            pool.getConnection(function(err, connection){
                if(err){
                    var error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.DB_CONNECTION_ERROR);
                    reject(error);   
                } else{
                    //read query sql from file
                    FileReader.readFile('./database/queries/getLastLoadsByScheduleAndDate.sql', 'utf8', function(err, sqlQuery){
                        if(err){
                            var error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.DB_CONNECTION_ERROR);
                            reject(error);
                        } else{
                            connection.query(sqlQuery, [scheduleId, lastUpdate], function(err, result){
                                
                                //console.log(result);
                                if(err){
                                    var error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.DB_CONNECTION_ERROR);
                                    connection.release();
                                    reject(error);
                                } else {
                                    connection.release();
                                    resolve(self.getLastLoadByQuery(result));
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