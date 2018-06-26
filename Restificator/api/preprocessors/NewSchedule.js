var Error = require('../../common/Error.js');
var HttpStatus = require('../../common/HttpStatus.js');
var HttpStatus = require("../../common/HttpStatus.js");
var ErrorType = require("../../common/ErrorType");
var Error = require("../../common/Error.js")

class Attribute{
    constructor(lastScheduleId, userId){
        this.lastScheduleId = lastScheduleId;
        this.userId= userId;
    }
    setLastScheduleId(lastScheduleId){
        this.lastScheduleId = lastScheduleId;
    }
    getLastScheduleId(){
        return this.lastScheduleId;
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
            var lastScheduleId = req.params.lastScheduleId;
            var userId = req.params.userId;
            var reg = /^\d+$/;
            if(lastScheduleId.toString() === 'null'){
                var lastScheduleIdAttribute = null;
                var userIdAttribute = parseInt(userId);
                var attribute = new Attribute(lastScheduleIdAttribute, userIdAttribute);
                resolve(attribute);
            } else if(!reg.test(lastScheduleId)){
                reject(new Error(HttpStatus.BAD_REQUEST, ErrorType.SCHEDULE_ID_ERROR));
            } else if(!reg.test(userId)){
                reject(new Error(HttpStatus.BAD_REQUEST, ErrorType.SCHEDULE_ID_ERROR));
            } else{
                var lastScheduleIdAttribute = parseInt(lastScheduleId);
                var userIdAttribute = parseInt(userId);
                var attribute = new Attribute(lastScheduleIdAttribute, userIdAttribute);
                resolve(attribute);
            }

        }

        return new Promise(promiseFunction);
    }
}