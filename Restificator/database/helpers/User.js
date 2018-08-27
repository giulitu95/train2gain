var pool = require("./pool.js");
var HttpStatus = require("../../common/HttpStatus.js");
var ErrorType = require("../../common/ErrorType");
var Error = require("../../common/Error.js");
var UserEntity = require("../entities/User.js");
var TrainerEntity = require("../entities/Trainer.js");
var AthleteEntity = require("../entities/Athlete.js");
var GymEntity = require("../entities/Gym.js");
var Loadable = require("./Loadable");
var FileReader = require('fs');
module.exports = class User extends Loadable{
    constructor(){
        super('./database/queries/insertUser.sql');
    }
    getUserByQuery(result){
        if(result.length == 0){
            return null;
        } else {
            var user = new UserEntity(result[0].user_id, result[0].user_email, null, result[0].user_displayName, result[0].user_userType, new Date(result[0].user_registrationDate).getTime(), result[0].user_profileImageUrl);
            return user;
        }
    }
    getTrainerByQuery(result){
        if(result.length == 0){
            return null;
        } else {
            var gym = null;
            if(result[0].gym_id != null){
                gym = new GymEntity(result[0].gym_id, result[0].gym_name, result[0].gym_logoUrl);
            } 
            var trainer = new TrainerEntity(result[0].trainer_id, result[0].trainer_gymId, gym);
            return trainer;
        }
    }
    getAthleteByQuery(result){
        if(result.length == 0){
            return null;
        } else{
            var athlete = new AthleteEntity(result[0].athlete_id, result[0].athlete_weight, result[0].athlete_height, new Date(result[0].athlete_firstWorkoutDate).getTime(), result[0].athlete_trainerId);
            return athlete;
        }
    }
    getUserById(userId){
        var self = this;
        var promiseFunction = function(resolve, reject){

            pool.getConnection(function(err, connection){
                if(err){
                    var error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.DB_CONNECTION_ERROR);
                    reject(error);   
                } else{
                    //read query sql from file
                    FileReader.readFile('./database/queries/getUser.sql', 'utf8', function(err, sqlQuery){
                        if(err){
                            var error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.DB_CONNECTION_ERROR);
                            reject(error);
                        } else{
                            connection.query(sqlQuery, [userId], function(err, result){
                                
                                //console.log(result);
                                if(err){
                                    var error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.DB_CONNECTION_ERROR);
                                    connection.release();
                                    reject(error);
                                } else {
                                    connection.release();
                                    resolve(self.getUserByQuery(result));
                                }
                            });
                        }
                    });
                }
            });
        }
        return new Promise(promiseFunction);
    }

    getTrainerById(trainerId){
        var self = this;
        var promiseFunction = function(resolve, reject){

            pool.getConnection(function(err, connection){
                if(err){
                    var error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.DB_CONNECTION_ERROR);
                    reject(error);   
                } else{
                    //read query sql from file
                    FileReader.readFile('./database/queries/getTrainer.sql', 'utf8', function(err, sqlQuery){
                        if(err){
                            var error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.DB_CONNECTION_ERROR);
                            reject(error);
                        } else{
                            connection.query(sqlQuery, [trainerId], function(err, result){
                                
                                //console.log(result);
                                if(err){
                                    var error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.DB_CONNECTION_ERROR);
                                    connection.release();
                                    reject(error);
                                } else {
                                    connection.release();
                                    resolve(self.getTrainerByQuery(result));
                                }
                            });
                        }
                    });
                }
            });
        }
        return new Promise(promiseFunction);
    }
    getAthleteById(athleteId){
        var self = this;
        var promiseFunction = function(resolve, reject){

            pool.getConnection(function(err, connection){
                if(err){
                    var error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.DB_CONNECTION_ERROR);
                    reject(error);   
                } else{
                    //read query sql from file
                    FileReader.readFile('./database/queries/getAthlete.sql', 'utf8', function(err, sqlQuery){
                        if(err){
                            var error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.DB_CONNECTION_ERROR);
                            reject(error);
                        } else{
                            connection.query(sqlQuery, [athleteId], function(err, result){
                                
                                //console.log(result);
                                if(err){
                                    var error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.DB_CONNECTION_ERROR);
                                    connection.release();
                                    reject(error);
                                } else {
                                    connection.release();
                                    resolve(self.getAthleteByQuery(result));
                                }
                            });
                        }
                    });
                }
            });
        }
        return new Promise(promiseFunction);
    }

    getUserProfileListByQuery(result){
        if(result.length == 0){
            return null;
        } else{
            var userProfileList = [];
            for(var i = 0; i < result.length; i++){
                var userProfile = new UserEntity(result[i].user_id, result[i].user_email, null, result[i].user_displayName, result[i].user_userType, result[i].user_registrationDate, result[i].profileImageUrl);
                userProfileList.push(userProfile);
            }
            return userProfileList;
        }
    }
    getUserProfileListByTrainerIdAndLastUpdate(trainerId, lastUpdate){
        var self = this;
        var promiseFunction = function(resolve, reject){

            pool.getConnection(function(err, connection){
                if(err){
                    var error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.DB_CONNECTION_ERROR);
                    reject(error);   
                } else{
                    //read query sql from file
                    FileReader.readFile('./database/queries/getUserProfileListByTrainerId.sql', 'utf8', function(err, sqlQuery){
                        if(err){
                            var error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.DB_CONNECTION_ERROR);
                            reject(error);
                        } else{
                            connection.query(sqlQuery, [trainerId, lastUpdate], function(err, result){
                                
                                //console.log(result);
                                if(err){
                                    var error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.DB_CONNECTION_ERROR);
                                    connection.release();
                                    reject(error);
                                } else {
                                    connection.release();
                                    resolve(self.getUserProfileListByQuery(result));
                                }
                            });
                        }
                    });
                }
            });
        }
        return new Promise(promiseFunction);
    }
    getTrainerByToken(token){
        var self = this;
        var promiseFunction = function(resolve, reject){

            pool.getConnection(function(err, connection){
                if(err){
                    var error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.DB_CONNECTION_ERROR);
                    reject(error);   
                } else{
                    //read query sql from file
                    FileReader.readFile('./database/queries/getTrainerByToken.sql', 'utf8', function(err, sqlQuery){
                        if(err){
                            var error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.DB_CONNECTION_ERROR);
                            reject(error);
                        } else{
                            connection.query(sqlQuery, [token], function(err, result){
                                
                                //console.log(result);
                                if(err){
                                    var error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.DB_CONNECTION_ERROR);
                                    connection.release();
                                    reject(error);
                                } else {
                                    if(result.length === 0){
                                        resolve(null);
                                    } else {
                                        var user = new UserEntity(result[0].user_id, result[0].user_email, null, result[0].user_displayName, result[0].user_type, new Date(result[0].user_registrationDate).getTime(), result[0].user_profileImageUrl);
                                        var trainer = new TrainerEntity(result[0].user_id, result[0].gym_id, new GymEntity(result[0].gym_id, result[0].gym_name, result[0].gym_logoUrl))
                                        user.trainer = trainer;
                                        connection.release();
                                        resolve(user);
                                    }
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