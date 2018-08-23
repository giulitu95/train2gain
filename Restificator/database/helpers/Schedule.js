var pool = require("./pool.js");
var DailyWorkoutEntity = require("../entities/DailyWorkout.js");
var ScheduleEntity = require("../entities/Schedule.js");
var ScheduleItemEntity = require("../entities/ScheduleItem.js");
var ScheduleSetEntity = require("../entities/ScheduleSet.js");
var ScheduleStepEntity = require("../entities/ScheduleStep.js");
var HttpStatus = require("../../common/HttpStatus.js");
var ErrorType = require("../../common/ErrorType");
var Error = require("../../common/Error.js");
var ExerciseEntity = require('../entities/Exercise.js');
var Loadable = require('./Loadable.js');
var FileReader = require('fs');

module.exports = class Schedule extends Loadable{
    constructor(){
        super('./database/queries/insertSchedule.sql');
    }
    getNewScheduleByQuery(result){
        var schedule = null;
        if(result.length > 0){
            schedule = new ScheduleEntity(result[0].schedule_id, new Date(result[0].schedule_startDate).getTime(), result[0].schedule_trainerId, result[0].schedule_athleteId);
        } else{
            return schedule;
        }
        var currentDailyWorkoutId= -1;
        var currentStepId = -1;
        var currentSetId = -1;
        var dailyWorkoutIndex = -1;
        var stepIndex = -1;
        var setIndex = -1;
        for(var i = 0; i < result.length; i++){
            var exercise = new ExerciseEntity(result[i].exercise_id, result[i].exercise_name,result[i].exercise_description, result[i].exercise_muscleGroupId, result[i].exercise_imageUrl);
            var item = new ScheduleItemEntity(result[i].scheduleItem_id, exercise, result[i].scheduleItem_orderNumber, result[i].scheduleItem_weight, result[i].scheduleItem_reps, result[i].scheduleItem_scheduleSetId, result[i].exercise_id);
            var set  = new ScheduleSetEntity(result[i].scheduleSet_id, result[i].scheduleSet_orderNumber, result[i].scheduleSet_scheduleStepId);
            set.scheduleItems.push(item);
            var step = new ScheduleStepEntity(result[i].scheduleStep_id, result[i].scheduleStep_restTime, result[i].scheduleStep_orderNumber, result[i].scheduleStep_type, result[i].scheduleStep_dailyWorkoutId);
            step.scheduleSets.push(set);
            var dailyWorkout = new DailyWorkoutEntity(result[i].dailyWorkout_id, result[i].dailyWorkout_orderNumber, result[i].dailyWorkout_scheduleId);
            dailyWorkout.scheduleSteps.push(step);
            if(currentDailyWorkoutId != result[i].dailyWorkout_id){
                currentDailyWorkoutId = result[i].dailyWorkout_id;
                currentStepId = result[i].scheduleStep_id;
                currentSetId = result[i].scheduleSet_id;
                setIndex = 0; stepIndex = 0; dailyWorkoutIndex ++;
                schedule.dailyWorkouts.push(dailyWorkout);
            } else if(currentStepId != result[i].scheduleStep_id){
                currentStepId = result[i].scheduleStep_id;
                currentSetId = result[i].scheduleSet_id;
                setIndex = 0;  stepIndex++; 
                schedule.dailyWorkouts[dailyWorkoutIndex].scheduleSteps.push(step);
            } else if(currentSetId != result[i].scheduleSet_id){
                currentSetId = result[i].scheduleSet_id;
                setIndex ++;
                schedule.dailyWorkouts[dailyWorkoutIndex].scheduleSteps[stepIndex].scheduleSets.push(set);
            } else{
                schedule.dailyWorkouts[dailyWorkoutIndex].scheduleSteps[stepIndex].scheduleSets[setIndex].scheduleItems.push(item);
            }
        }
        //console.log(JSON.stringify(schedule, null, 4));
        return schedule;
    }

    getNewScheduleByLastScheduleAndUserId(lastScheduleId, userId){
        var self = this;
        var promiseFunction = function(resolve, reject){

            pool.getConnection(function(err, connection){
                if(err){
                    var error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.DB_CONNECTION_ERROR);
                    reject(error);   
                } else{
                    //read query sql from file
                    FileReader.readFile('./database/queries/getNewScheduleByLastScheduleId.sql', 'utf8', function(err, sqlQuery){
                        if(err){
                            var error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.DB_CONNECTION_ERROR);
                            reject(error)
                        } else{
                            connection.query(sqlQuery, [lastScheduleId, userId, userId], function(err, result){
                                if(err){
                                    var error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.DB_CONNECTION_ERROR);
                                    connection.release();
                                    reject(error)
                                } else {
                                    connection.release();
                                    resolve(self.getNewScheduleByQuery(result));
                                }
                            });
                        }
                    });
                }
            });
        }
        return new Promise(promiseFunction);
    }

    getLastScheduleByUserId(userId){
        var self = this;
        var promiseFunction = function(resolve, reject){

            pool.getConnection(function(err, connection){
                if(err){
                    var error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.DB_CONNECTION_ERROR);
                    reject(error);   
                } else{
                    //read query sql from file
                    FileReader.readFile('./database/queries/getLastScheduleByUserId.sql', 'utf8', function(err, sqlQuery){
                        if(err){
                            var error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.DB_CONNECTION_ERROR);
                            reject(error)
                        } else{
                            connection.query(sqlQuery, [userId], function(err, result){
                                
                                //console.log(result);
                                if(err){
                                    var error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.DB_CONNECTION_ERROR);
                                    connection.release();
                                    reject(error)
                                } else {
                                    connection.release();
                                    resolve(self.getNewScheduleByQuery(result));
                                }
                            });
                        }
                    });
                }
            });
        }
        return new Promise(promiseFunction);
    }

    getScheduleListByQuery(result){
        var scheduleList = [];
        if(result.length == 0){
            return null;
        }
        for(var i = 0; i < result.length; i++){
            var schedule = new ScheduleEntity(result[i].schedule_id, new Date(result[i].schedule_startDate).getTime(), result[i].schedule_trainerId, result[i].schedule_athleteId);
            scheduleList.push(schedule);
        }
        return scheduleList;
    }
    getScheduleListByUserIdAndLastUpdate(userId, lastUpdate){
        var self = this;
        var promiseFunction = function(resolve, reject){

            pool.getConnection(function(err, connection){
                if(err){
                    var error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.DB_CONNECTION_ERROR);
                    reject(error);   
                } else{
                    //read query sql from file
                    FileReader.readFile('./database/queries/getScheduleListByUserAndLastUpdate.sql', 'utf8', function(err, sqlQuery){
                        if(err){
                            var error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.DB_CONNECTION_ERROR);
                            reject(error)
                        } else{
                            connection.query(sqlQuery, [userId, lastUpdate], function(err, result){
                                if(err){
                                    var error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.DB_CONNECTION_ERROR);
                                    connection.release();
                                    reject(error)
                                } else {
                                    connection.release();
                                    resolve(self.getScheduleListByQuery(result));
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