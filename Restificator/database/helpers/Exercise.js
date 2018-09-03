var pool = require("./pool.js");
var HttpStatus = require("../../common/HttpStatus.js");
var ErrorType = require("../../common/ErrorType");
var Error = require("../../common/Error.js");
var ExerciseEntity = require("../entities/Exercise.js");
var FileReader = require('fs');


module.exports = class Frequence{
    getExerciseListByQuery(result){
        if(result.length == 0){
            return null;
        } else{
            var exerciseList = [];
            for(var i = 0; i < result.length; i++){
                var exercise = new ExerciseEntity(result[i].exercise_id, result[i].exercise_name, result[i].exercise_description, result[i].exercise_muscleGroup, result[i].exercise_imageUrl);
                exerciseList.push(exercise);
            }
            //console.log(JSON.stringify(loads, null, 4));
            return exerciseList;
        }
    }
    getExerciseListByLastUpdate(lastUpdate){
        var self = this;
        var promiseFunction = function(resolve, reject){

            pool.getConnection(function(err, connection){
                if(err){
                    var error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.DB_CONNECTION_ERROR);
                    reject(error);   
                } else{
                    //read query sql from file
                    FileReader.readFile('./database/queries/getExerciseListByLastUpdate.sql', 'utf8', function(err, sqlQuery){
                        if(err){
                            var error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.DB_CONNECTION_ERROR);
                            reject(error);
                        } else{
                            connection.query(sqlQuery, [lastUpdate], function(err, result){
                                
                                //console.log(result);
                                if(err){
                                    var error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.DB_CONNECTION_ERROR);
                                    connection.release();
                                     reject(error);
                                } else {
                                    connection.release();
                                    resolve(self.getExerciseListByQuery(result));
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