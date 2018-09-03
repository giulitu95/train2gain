var DailyWorkoutEntity = require("./DailyWorkout.js");
var ScheduleStepEntity = require("./ScheduleStep.js");
var ScheduleSetEntity = require("./ScheduleSet.js");
var ScheduleItemEntity = require("./ScheduleItem.js");
var ExerciseEntity = require("./Exercise.js");
var Validator = require('jsonschema').Validator;

module.exports = class Schedule{

    
    constructor(id, startDate, trainerId, athleteId){
        this.id = id; 
        this.athleteId = athleteId;
        this.startDate = startDate;
        this.trainerId = trainerId;
        this.dailyWorkouts = [];
    }

    static validateSchema(schedule){
        var validator = new Validator();
        validator.addSchema(ExerciseEntity.getSchema(), ExerciseEntity.getSchemaId());
        validator.addSchema(ScheduleItemEntity.getSchema(), ScheduleItemEntity.getSchemaId());
        validator.addSchema(ScheduleSetEntity.getSchema(), ScheduleSetEntity.getSchemaId());
        validator.addSchema(ScheduleStepEntity.getSchema(), ScheduleStepEntity.getSchemaId());
        validator.addSchema(DailyWorkoutEntity.getSchema(), DailyWorkoutEntity.getSchemaId());
        return validator.validate(schedule, this.getSchema()).errors.length == 0;
    }

    static getSchema(){
        var scheduleSchema = {
            "id": "/SimpleSchedule",
            "type": "object",
            "properties": {
              "id": {"type": "integer"},
              "athleteId": {"type": "integer"},
              "startDate": {"type": "integer"},
              "trainerId": {"type": "integer"},
              "dailyWorkouts":{
                  "type": "array",
                  "minItems": 1,
                  "items": {"$ref": DailyWorkoutEntity.getSchemaId()}
                }
            },
            "required":["id","startDate", "trainerId", "athleteId", "dailyWorkouts"]
          };
        return scheduleSchema;
    }
    static getSchemaId(){
        return "/SimpleSchedule";
    }

}