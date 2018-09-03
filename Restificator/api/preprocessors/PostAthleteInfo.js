var Error = require('../../common/Error.js');
var HttpStatus = require("../../common/HttpStatus.js");
var ErrorType = require("../../common/ErrorType");
var Error = require("../../common/Error.js");
var Athlete = require("../../database/entities/Athlete.js");

module.exports = class PostAthleteInfo{
    constructor(){
    }
    parseAndValidate(req){
        var promiseFunction = function(resolve, reject){
            var athleteInfo = req.body;
            if(Athlete.validateSchema(athleteInfo)){
                resolve(athleteInfo);
            } else{
                reject(new Error(HttpStatus.BAD_REQUEST, ErrorType.JSON_FORMAT_ERROR));
            }
        }
        return new Promise(promiseFunction);
    }
}