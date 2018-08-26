var ExerciseEntity = require("./Exercise.js");
var Validator = require('jsonschema').Validator;
module.exports = class ScheduleItem{
    constructor(id, exercise, orderNumber, load, reps, scheduleSetId, exerciseId){
        this.id = id;
        this.orderNumber = orderNumber;
        this.load = load;
        this.reps = reps;
        this.scheduleSetId = scheduleSetId;
        this.exerciseId = exerciseId;
        this.exercise = exercise;
    }
    static validateSchema(scheduleItem){
        var validaotr = new Validator();
        validator.addSchema(ExerciseEntity.getSchema(), ExerciseEntity.getSchemaId());
        return validaotr.validate(scheduleItem, this.getSchema()).errors.length == 0;
    }
    static getSchema(){
        var scheduleItemSchema = {
            "id": "/SimpleScheduleItem",
            "type": "object",
            "properties": {
              "id": {"type": "integer"},
              "orderNumber": {"type": "integer"},
              "reps": {"type": "integer"},
              "scheduleSetId": {"type": "integer"},
              "exerciseId": {"type": "integer"},
              "exercise":{"$ref": ExerciseEntity.getSchemaId()}
            },

            "required":["id","orderNumber", "reps", "scheduleSetId", "exerciseId"]
          };
        return scheduleItemSchema;
    }
    static getSchemaId(){
        return "/SimpleScheduleItem";
    }
}