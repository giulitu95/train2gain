
var Validator = require('jsonschema').Validator;
module.exports = class Athlete{
    constructor(id, weight, height, firstWorkoutDate, trainerId){
        this.id = id;
        this.weight = weight;
        this.height = height;
        this.firstWorkoutDate = firstWorkoutDate;
        this.trainerId = trainerId;
    }

    
    static validateSchema(athlete){
        var validator = new Validator();
        return validator.validate(athlete, this.getSchema()).errors.length == 0;
    }

    static getSchema(){
        var athlete = {
            "id": "/SimpleAthlete",
            "type": "object",
            "properties": {
                "id": {"type": "integer"},
                "weight": {"type": "integer"},
                "height": {"type": "integer"},
                "firstWorkoutDate": {"type": "integer"},
                "trainerId": {"type": "integer"}
            },
            "required":["id"]
          };
        return athlete;
    }

}