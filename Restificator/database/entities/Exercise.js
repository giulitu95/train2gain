var Validator = require('jsonschema').Validator;
module.exports = class Exercise{
    constructor(id, name, description, muscleGroupId, imageUrl){
        this.id = id;
        this.name = name;
        this.description = description;
        this.muscleGroupId = muscleGroupId;
        this.imageUrl = imageUrl;
    }
    static validateSchema(exercise){
        var validator = new Validator();
        return validator.validate(exercise, this.getSchema()).errors.length == 0;
    }
    static getSchema(){
        var exerciseSchema = {
            "id": "/SimpleExercise",
            "type": "object",
            "properties": {
              "id": {"type": "integer"},
              "name": {"type": "string"},
              "description": {"type": "string"},
              "muscleGroupId": {"type": "integer"},
              "imageUrl": {"type": "string"}

            },

            "required":["id", "name", "description", "muscleGroupId", "imageUrl"]
          };
        return exerciseSchema;
    }
    static getSchemaId(){
        return "/SimpleExercise";
    }
}