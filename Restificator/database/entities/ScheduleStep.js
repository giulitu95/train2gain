var ScheduleSetEntity = require("./ScheduleSet.js");
var ScheduleItemEntity = require("./ScheduleItem.js");
var ExerciseEntity = require("./Exercise.js");
var Validator = require('jsonschema').Validator;
module.exports = class ScheduleStep{
    constructor(id, restTime, orderNumber, scheduleStepTypeId, dailyWorkoutId){
        this.id = id; 
        this. restTime = restTime;
        this.orderNumber = orderNumber;
        this.scheduleStepTypeId = scheduleStepTypeId;
        this.dailyWorkoutId = dailyWorkoutId;
        this.scheduleSets = [];
    }
    static validateSchema(scheduleStep){
        var validaotr = new Validator();
        validator.addSchema(ExerciseEntity.getSchema(), ExerciseEntity.getSchemaId());
        validator.addSchema(ScheduleItemEntity.getSchema(), ScheduleStepEntity.getSchemaId());
        validator.addSchema(ScheduleSetEntity.getSchema(), ScheduleSetEntity.getSchemaId());
        return validaotr.validate(scheduleStep, this.getSchema()).errors.length == 0;
    }
    static getSchema(){
        var scheduleStepSchema = {
            "id": "/SimpleScheduleStep",
            "type": "object",
            "properties": {
              "id": {"type": "integer"},
              "restTime": {"type": "integer"},
              "orderNumber": {"type": "integer"},
              "scheduleStepTypeId": {"type": "integer"},
              "dailyWorkoutId": {"type": "integer"},
              "scheduleSets":{
                "type": "array",
                "minItems": 1,
                "items": {"$ref": ScheduleSetEntity.getSchemaId()}
              }
            },
            "required":["id","restTime", "orderNumber", "scheduleStepTypeId", "dailyWorkoutId", "scheduleSets"]
          };
        return scheduleStepSchema;
    }
    static getSchemaId(){
        return "/SimpleScheduleStep";
    }
}