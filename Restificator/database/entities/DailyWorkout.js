var ScheduleStepEntity = require("./ScheduleStep.js");
var ScheduleSetEntity = require("./ScheduleSet.js");
var ScheduleItemEntity = require("./ScheduleItem.js");
var ExerciseEntity = require("./Exercise.js");
var Validator = require('jsonschema').Validator;
module.exports = class DailyWorkout{
    constructor(id, orderNumber, scheduleId){
        this.id = id; 
        this.orderNumber = orderNumber;
        this.scheduleId = scheduleId;
        this.scheduleSteps = [];
    }
    static validateSchema(dailyWorkout){
        var validator = new Validator();
        validator.addSchema(ExerciseEntity.getSchema(), ExerciseEntity.getSchemaId());
        validator.addSchema(ScheduleItemEntity.getSchema(), ScheduleStepEntity.getSchemaId());
        validator.addSchema(ScheduleSetEntity.getSchema(), ScheduleSetEntity.getSchemaId());
        validator.addSchema(ScheduleStepEntity.getSchema(), ScheduleStepEntity.getSchemaId());
        return validator.validate(dailyWorkout, this.getSchema()).errors.length == 0;
    }
    static getSchema(){

        var dailyWorkoutSchema = {
            "id": "/SimpleDailyWorkout",
            "type": "object",
            "properties": {
              "id": {"type": "integer"},
              "orderNumber": {"type": "integer"},
              "scheduleId": {"type": "integer"},
              "scheduleSteps":{
                "type": "array",
                "minItems": 1,
                "items": {"$ref": ScheduleStepEntity.getSchemaId()}
              }
            },
            "required":["id","orderNumber","scheduleId"]
          };
        return dailyWorkoutSchema;
    }
    static getSchemaId(){
        return "/SimpleDailyWorkout";
    }

}