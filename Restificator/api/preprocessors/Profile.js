var Error = require('../../common/Error.js');
var HttpStatus = require('../../common/HttpStatus.js');
var HttpStatus = require("../../common/HttpStatus.js");
var ErrorType = require("../../common/ErrorType");
var Error = require("../../common/Error.js")

class Attribute{
    constructor(userId){
        this.userId= userId;
    }
    setUserId(userId){
        this.userId = userId;
    }
    getUserId(){
        return this.userId;
    }
}

module.exports = class NewSchedule{
    constructor(){

    }
    parseAndValidate(req){
        var promiseFunction = function(resolve, reject){
            var userId = req.params.userId;
            var reg = /^\d+$/;
            if(!reg.test(userId)){
                reject(new Error(HttpStatus.BAD_REQUEST, ErrorType.SCHEDULE_ID_ERROR));
            } else{
                var userIdAttribute = parseInt(userId);
                var attribute = new Attribute(userIdAttribute);
                resolve(attribute);
            }

        }

        return new Promise(promiseFunction);
    }
}