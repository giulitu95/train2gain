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


module.exports = class Trainer extends Loadable{
    constructor(){
        super('./database/queries/insertTrainer.sql');
    }
}