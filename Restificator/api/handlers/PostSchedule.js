var ApplicationHandlerSkeleton = require('../../ApplicationHandlerSkeleton.js')
var ScheduleHelper = require('../../database/helpers/Schedule.js');
var DailyWorkoutHelper = require('../../database/helpers/DailyWorkout.js');
var ScheduleStepHelper = require('../../database/helpers/ScheduleStep.js');
var ScheduleItemHelper = require('../../database/helpers/ScheduleItem.js');
var PostSchedulePreprocessor = require("../preprocessors/PostSchedule.js");
var Response = require('./StandardResponse.js');
var ScheduleResponse = require('./responseObjects/Schedule.js');
var DailyWorkoutResponse = require('./responseObjects/DailyWorkout.js');
var ScheduleStepResponse = require('./responseObjects/ScheduleStep.js');
var ScheduleSetResponse = require('./responseObjects/ScheduleSet.js');
var ScheduleSetHelper = require('../../database/helpers/ScheduleSet.js');
var ScheduleItemResponse = require('./responseObjects/ScheduleItem.js');
var Error = require('../../common/Error.js');
var HttpStatus = require("../../common/HttpStatus.js");
var ErrorType = require("../../common/ErrorType");
var Error = require("../../common/Error.js");


module.exports = class Loads extends ApplicationHandlerSkeleton{
    constructor() {
        var preprocessor = new PostSchedulePreprocessor();
        super(preprocessor);
    }


    processFailure(res, err){
        var json = JSON.stringify(
            {
                'errorCode': err.statusType.status,
                'errorMessage': err.descriptionType.errorDescription
            }
        );
        res.set({'Content-Type': 'application/json', 'Accept': 'application/json'});
        res.status(err.statusType.status);
        res.send(json);
    }

    processRequest(res, schedule){
        var self = this;
        var scheduleHelper = new ScheduleHelper();
        var dailyWorkoutHelper = new DailyWorkoutHelper();
        var scheduleStepHelper = new ScheduleStepHelper();
        var scheduleSetHelper = new ScheduleSetHelper();
        var scheduleItemHelper = new ScheduleItemHelper();
        var scheduleResponse = new ScheduleResponse(schedule.id);
        var schedulesList = []
        var scheduleProperties = [schedule.athleteId, new Date(schedule.startDate), schedule.trainerId];
        schedulesList.push(scheduleProperties);
        scheduleHelper.insert(schedulesList).then(function(firstIndex){
            scheduleResponse.addRemoteId(firstIndex);
            var dailyWorkoutsListData = [];
            for(var i = 0; i < schedule.dailyWorkouts.length; i++){
                var dailyWorkoutResponse = new DailyWorkoutResponse(schedule.dailyWorkouts[i].id);
                var dailyWorkoutProperties = [schedule.dailyWorkouts[i].orderNumber, firstIndex];
                dailyWorkoutsListData.push(dailyWorkoutProperties);
                scheduleResponse.addDailyWorkout(dailyWorkoutResponse);
            }
            
            return  dailyWorkoutHelper.insert(dailyWorkoutsListData);
            
        }, function(error){
            self.processFailure(error);
        }).then(function(firstDailyWorkoutIndex){
            var scheduleStepListData = [];
            var promiseArray  = [];
            for(let i = 0; i < schedule.dailyWorkouts.length; i++){
                scheduleResponse.dailyWorkouts[i].addRemoteId(i + firstDailyWorkoutIndex);
                scheduleResponse.dailyWorkouts[i].addScheduleId(scheduleResponse.id);
                var scheduleStepList = schedule.dailyWorkouts[i].scheduleSteps;
                var scheduleStepListData = [];
                
                for(var j = 0; j <scheduleStepList.length; j++){
                    var scheduleStepData = [scheduleStepList[j].restTime, scheduleStepList[j].orderNumber,  scheduleStepList[j].scheduleStepTypeId, i + firstDailyWorkoutIndex];
                    scheduleResponse.dailyWorkouts[i].addScheduleStep(new ScheduleStepResponse(scheduleStepList[j].id));
                    scheduleStepListData.push(scheduleStepData);
                
                }
                var  ret = scheduleStepHelper.insert(scheduleStepListData);
                promiseArray.push(ret);
               
            }
            return Promise.all(promiseArray)
            
        }, function(error){
            return Promise.reject(error);
        }).then(function(firstScheduleStepIndex){
            var promiseArray = [];
            for(var i = 0; i < firstScheduleStepIndex.length; i++){
                
                for(var j = 0; j < schedule.dailyWorkouts[i].scheduleSteps.length; j++){
                    scheduleResponse.dailyWorkouts[i].scheduleSteps[j].addRemoteId(j + firstScheduleStepIndex[i]);
                    scheduleResponse.dailyWorkouts[i].scheduleSteps[j].addDailyWorkoutId(scheduleResponse.dailyWorkouts[i].id);
                    var scheduleSetList  = schedule.dailyWorkouts[i].scheduleSteps[j].scheduleSets;
                    var scheduleSetListData = [];
                    for(var k = 0; k < scheduleSetList.length; k++){
                        var scheduleSetData = [scheduleSetList[k].orderNumber, j + firstScheduleStepIndex[i]];
                        scheduleResponse.dailyWorkouts[i].scheduleSteps[j].addScheduleSet(new ScheduleSetResponse(scheduleSetList[k].id));
                        scheduleSetListData.push(scheduleSetData);
                    }
                    var ret = scheduleSetHelper.insert(scheduleSetListData);
                    promiseArray.push(ret);
                }
            }
            return Promise.all(promiseArray);
            
        }, function(error){
            return Promise.reject(error);
        }).then(function(firstScheduleSetIndex){
            var k = 0;
            var promiseArray = [];
            for(var i = 0; i < schedule.dailyWorkouts.length; i++){
                for(var j = 0; j < schedule.dailyWorkouts[i].scheduleSteps.length; j++){
                    for( var t = 0; t < schedule.dailyWorkouts[i].scheduleSteps[j].scheduleSets.length; t++){
                        scheduleResponse.dailyWorkouts[i].scheduleSteps[j].scheduleSets[t].addRemoteId(t + firstScheduleSetIndex[k]);
                        scheduleResponse.dailyWorkouts[i].scheduleSteps[j].scheduleSets[t].addScheduleStepId(scheduleResponse.dailyWorkouts[i].scheduleSteps[j].id);
                        var scheduleItemList = schedule.dailyWorkouts[i].scheduleSteps[j].scheduleSets[t].scheduleItems;
                        var scheduleItemListData = [];
                        for(var s = 0; s < scheduleItemList.length; s++){
                            var scheduleItemData = [scheduleItemList[s].reps, scheduleItemList[s].orderNumber, t + firstScheduleSetIndex[k], scheduleItemList[s].exerciseId];
                            scheduleResponse.dailyWorkouts[i].scheduleSteps[j].scheduleSets[t].addScheduleItem(new ScheduleItemResponse(scheduleItemList[s].id));
                            scheduleItemListData.push(scheduleItemData);
                        }
                        var ret = scheduleItemHelper.insert(scheduleItemListData);
                        promiseArray.push(ret);
                    }
                    k++;
                }
            }
            return Promise.all(promiseArray);
        }, function(error){
            return Promise.reject(error);
        }).then(function(firstScheduleItemIndex){
            var k = 0;
            for(var i = 0; i < schedule.dailyWorkouts.length; i++){
                for(var j = 0; j < schedule.dailyWorkouts[i].scheduleSteps.length; j++){
                    for(var t = 0; t < schedule.dailyWorkouts[i].scheduleSteps[j].scheduleSets.length; t++){
                        for(var g = 0; g < schedule.dailyWorkouts[i].scheduleSteps[j].scheduleSets[t].scheduleItems.length; g++){
                            scheduleResponse.dailyWorkouts[i].scheduleSteps[j].scheduleSets[t].scheduleItems[g].addRemoteId(g + firstScheduleItemIndex[k]);
                            scheduleResponse.dailyWorkouts[i].scheduleSteps[j].scheduleSets[t].scheduleItems[g].addScheduleSetId(scheduleResponse.dailyWorkouts[i].scheduleSteps[j].scheduleSets[t].id);
                        } 
                        k++
                    }
                }
            }
            var httpResponse = new Response(new Date().getTime(), scheduleResponse);
            var json = JSON.stringify(httpResponse);
            res.writeHead(200, {'Content-Type': 'application/json', 'Accept': 'application/json'});
            res.end(json);
        }, function(error){
            self.processFailure(res, error);
        });;
    }
}