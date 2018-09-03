var ScheduleStepEntity = require("./ScheduleStep.js");
var ScheduleSetEntity = require("./ScheduleSet.js");
var ScheduleItemEntity = require("./ScheduleItem.js");
var ExerciseEntity = require("./Exercise.js");
var Validator = require('jsonschema').Validator;
module.exports = class Updates{
    constructor(type, scheduleStep){
        this.type = type;
        this.scheduleStep = scheduleStep;
    }


    static getDeleteUpdate(){
        
        var updateDeleteSchema = {
            "id": "/SimpleDeleteUpdate",
            "type": "object",
            "properties": {
              "type": {"enum": [2]},
              "scheduleStep":  {"$ref": "/EmptyScheduleStep"}
            },
              
            "required":["type", "scheduleStep"]
          };
          return updateDeleteSchema;
    }
    static validateSchema(scheduleStep){
        var updateListSchema = {
            "id": "/SimpleUpdateList",
            "type": "object",
            "properties":{
                "updates":{
                    "type": "array",
                    "items": {"anyOf":[{"$ref": "/SimpleUpdate"},{"$ref": "/SimpleDeleteUpdate"}]}
                }   
            },
            "required":["updates"]    
        };
        var validator = new Validator();
        validator.addSchema(ExerciseEntity.getSchema(), ExerciseEntity.getSchemaId());
        validator.addSchema(ScheduleItemEntity.getSchema(), ScheduleItemEntity.getSchemaId());
        validator.addSchema(ScheduleSetEntity.getSchema(), ScheduleSetEntity.getSchemaId());
        validator.addSchema(ScheduleStepEntity.getSchema(), ScheduleStepEntity.getSchemaId());
        validator.addSchema(this.getSchema(), this.getSchemaId());
        validator.addSchema(this.getDeleteUpdate(), "/SimpleDeleteUpdate");
        validator.addSchema(this.getEmptySchduleStep(), "/SimpleUpdateList");
        return validator.validate(scheduleStep, updateListSchema).errors.length == 0;
    }

    static getEmptySchduleStep(){
        var emptyScheduleStep = {
            "id": "/EmptyScheduleStep",
            "type": "object",
            "properties":{
                "id": {"type": "integer"},
                "restTime": {"type": "null"},
                "orderNumber":{"type": "null"},
                "scheduleStepTypeId": {"type": "null"},
                "dailyWorkoutId": {"type": "null"},
                "scheduleSets":{
                    "type": "array",
                    "maxItems": 0,
                    "items": {"type": "object"}
                }
            },
            "required":["id", "restTime", "orderNumber", "scheduleStepTypeId", "dailyWorkoutId", "scheduleSets"]    
        };
        return emptyScheduleStep;
    }
    static getSchema(){
        
        
        var updateSchema = {
            "id": "/SimpleUpdate",
            "type": "object",
            "properties": {
              "type": {"enum": [1,3]},
              "scheduleStep":  {"$ref": ScheduleStepEntity.getSchemaId()}
            },
              
            "required":["type", "scheduleStep"]
          };
        return updateSchema;
    }
    static getSchemaId(){
        return "/SimpleUpdate";
    }
}