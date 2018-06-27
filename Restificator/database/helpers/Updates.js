var pool = require("./pool.js");
var DailyWorkoutEntity = require("../entities/DailyWorkout.js");
var ScheduleEntity = require("../entities/Schedule.js");
var ScheduleItemEntity = require("../entities/ScheduleItem.js");
var ScheduleSetEntity = require("../entities/ScheduleSet.js");
var ScheduleStepEntity = require("../entities/ScheduleStep.js");
var HttpStatus = require("../../common/HttpStatus.js");
var ErrorType = require("../../common/ErrorType");
var Error = require("../../common/Error.js");
var UpdatesEntity = require("../entities/Updates.js");
var ExerciseEntity = require('../entities/Exercise.js')
var FileReader = require('fs');


module.exports = class Updates{

    constructor(){
    }
    getUpdateByQuery(result){
        var updates = [];
        var currentUpdatesId= -1;
        var currentSetId = -1;
        var scheduleItemId = -1;
        var setIndex = -1;
        var itemIndex = -1;
        
        if(result.length == 0){
            updates = null;
            return updates;
        }
        for(var i = 0; i < result.length; i++){
            var exercise = new ExerciseEntity(result[i].exercise_id, result[i].exercise_name,result[i].exercise_description, result[i].exercise_muscleGroupId, result[i].exercise_imageUrl);
            var item = new ScheduleItemEntity(result[i].scheduleItem_id, exercise, result[i].scheduleItem_orderNumber, result[i].scheduleItem_weight, result[i].scheduleItem_reps, result[i].scheduleItem_scheduleSetId, result[i].exercise_id);
            var set  = new ScheduleSetEntity(result[i].scheduleSet_id, result[i].scheduleSet_orderNumber, result[i].scheduleSet_scheduleStepId);
            set.scheduleItems.push(item);
            var step = new ScheduleStepEntity(result[i].updates_scheduleStepId, result[i].scheduleStep_restTime, result[i].scheduleStep_orderNumber, result[i].scheduleStep_type, result[i].scheduleStep_dailyWorkoutId);
            step.scheduleSets.push(set);
            if(currentUpdatesId != result[i].updates_id){
                currentUpdatesId = result[i].updates_id;
                currentSetId = result[i].scheduleSet_id;
                setIndex = 0; itemIndex = 0;
                if(result[i].scheduleStep_id === null){
                    updates.push(new UpdatesEntity(result[i].updates_type, new ScheduleStepEntity(result[i].updates_scheduleStepId, null, null, null, null)));
                } else{
                    updates.push(new UpdatesEntity(result[i].updates_type, step));
                }
            } else if(currentSetId != result[i].scheduleSet_id){
                currentSetId = result[i].scheduleSet_id;
                updates[updates.length -1].scheduleStep.scheduleSets.push(set);
                setIndex++;
            } else{
                updates[updates.length -1].scheduleStep.scheduleSets[currentSetId].scheduleItems.push(item);
                itemIndex++;
            }
        }
        //console.log(JSON.stringify(updates, null, 4));
        return updates;
    }
    getUpdatesByScheduleAndDate(scheduleId, lastUpdate, userId){
        var self = this;
        var promiseFunction = function(resolve, reject){

            pool.getConnection(function(err, connection){
                if(err){
                    var error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.DB_CONNECTION_ERROR);
                    reject(error);   
                } else{
                    //read query sql from file
                    FileReader.readFile('./database/queries/getUpdatesByScheduleAndDate.sql', 'utf8', function(err, sqlQuery){
                        if(err){
                            var error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.DB_CONNECTION_ERROR);
                            reject(error)
                        } else{
                            connection.query(sqlQuery, [lastUpdate, scheduleId, userId], function(err, result){
                                
                                //console.log(result);
                                if(err){
                                    var error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.DB_CONNECTION_ERROR);
                                    connection.release();
                                    reject(error);
                                } else {
                                    connection.release();
                                    resolve(self.getUpdateByQuery(result));
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