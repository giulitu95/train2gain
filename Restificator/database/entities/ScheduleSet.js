var ScheduleItemEntity = require("./ScheduleItem.js");
var ExerciseEntity = require("./Exercise.js");
var Validator = require('jsonschema').Validator;
module.exports = class ScheduleSet{
    constructor(id, orderNumber, scheduleStepId){
        this.id = id; 
        this.orderNumber = orderNumber;
        this.scheduleStepId = scheduleStepId;
        this.scheduleItems = [];
    }
    static validateSchema(scheduleSet){
        var validaotr = new Validator();
        validator.addSchema(ExerciseEntity.getSchema(), ExerciseEntity.getSchemaId());
        validator.addSchema(ScheduleItemEntity.getSchema(), ScheduleStepEntity.getSchemaId());
        return validaotr.validate(scheduleSet, this.getSchema()).errors.length == 0;
    }
    static getSchema(){
        var scheduleSetSchema = {
            "id": "/SimpleScheduleSet",
            "type": "object",
            "properties": {
              "id": {"type": "integer"},
              "orderNumber": {"type": "integer"},
              "scheduleStepId": {"type": "integer"},
              "scheduleItems":{
                "type": "array",
                "minItems": 1,
                "items": {"$ref": ScheduleItemEntity.getSchemaId()}
              }
            },
            "required":["id", "orderNumber", "scheduleStepId", "scheduleItems"]
          };
        return scheduleSetSchema;
    }
    static getSchemaId(){
        return "/SimpleScheduleSet";
    }
}