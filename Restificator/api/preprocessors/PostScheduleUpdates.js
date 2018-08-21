var Error = require('../../common/Error.js');
var HttpStatus = require("../../common/HttpStatus.js");
var ErrorType = require("../../common/ErrorType");
var Error = require("../../common/Error.js");
var UpdateEntity = require("../../database/entities/Updates.js");

module.exports = class NewSchedule{
    constructor(){

    }
    parseAndValidate(req){
        var promiseFunction = function(resolve, reject){
            var updates = req.body;
            console.log(UpdateEntity.validateSchema(updates));
            if(UpdateEntity.validateSchema(updates)){
                resolve(updates);
            } else{
                reject(new Error(HttpStatus.BAD_REQUEST, ErrorType.JSON_FORMAT_ERROR));
            }
        }
        return new Promise(promiseFunction);
    }
}