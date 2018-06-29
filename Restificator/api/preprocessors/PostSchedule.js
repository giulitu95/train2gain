var Error = require('../../common/Error.js');
var HttpStatus = require("../../common/HttpStatus.js");
var ErrorType = require("../../common/ErrorType");
var Error = require("../../common/Error.js");
var Schedule = require("../../database/entities/Schedule.js");

module.exports = class NewSchedule{
    constructor(){

    }
    parseAndValidate(req){
        var promiseFunction = function(resolve, reject){
            var schedule = req.body;
            console.log(Schedule.validateSchema(schedule));
            if(Schedule.validateSchema(schedule)){
                resolve(schedule);
            } else{
                reject(new Error(HttpStatus.BAD_REQUEST, ErrorType.JSON_FORMAT_ERROR));
            }
        }
        return new Promise(promiseFunction);
    }
}