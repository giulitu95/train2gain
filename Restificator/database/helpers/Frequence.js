var pool = require("./pool.js");
var HttpStatus = require("../../common/HttpStatus.js");
var ErrorType = require("../../common/ErrorType");
var Error = require("../../common/Error.js");
var FrequenceEntity = require("../entities/Frequence.js");
var FileReader = require('fs');


module.exports = class Frequence{
    getLastFrequencesByQuery(result){
        if(result.length == 0){
            return null
        } else{
            var frequences = [];
            for(var i = 0; i < result.length; i++){
                var frequence = new FrequenceEntity(result[i].frequence_id, result[i].frequence_date, result[i].frequence_dailyWorkoutId, result[i].frequence_athleteId);
                frequences.push(frequence);
            }
            //console.log(JSON.stringify(loads, null, 4));
            return frequences;
        }
    }
    getFrequencesByUserAndLastUpdate(userId, lastUpdate){
        var self = this;
        var promiseFunction = function(resolve, reject){

            pool.getConnection(function(err, connection){
                if(err){
                    var error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.DB_CONNECTION_ERROR);
                    reject(error);   
                } else{
                    //read query sql from file
                    FileReader.readFile('./database/queries/getLastFrequencesByUserAndLastUpdate.sql', 'utf8', function(err, sqlQuery){
                        if(err){
                            var error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.DB_CONNECTION_ERROR);
                            reject(error);
                        } else{
                            connection.query(sqlQuery, [userId, lastUpdate], function(err, result){
                                
                                //console.log(result);
                                if(err){
                                    var error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.DB_CONNECTION_ERROR);
                                    connection.release();
                                    reject(error);
                                } else {
                                    connection.release();
                                    resolve(self.getLastFrequencesByQuery(result));
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