var Error = require('../../common/Error.js');
var HttpStatus = require('../../common/HttpStatus.js');
var HttpStatus = require("../../common/HttpStatus.js");
var ErrorType = require("../../common/ErrorType");
var Error = require("../../common/Error.js")

class Attribute{
    constructor(scheduleId, lastUpdate, userId){
        this.scheduleId = scheduleId;
        this.lastUpdate = lastUpdate;
        this.userId = userId;
    }
    setScheduleId(scheduleId){
        this.scheduleId = scheduleId;
    }
    getScheduleId(){
        return this.scheduleId;
    }
    setLastUpdate(lastUpdate){
        this.lastUpdate = lastUpdate;
    }
    getLastUpdate(){
        return this.lastUpdate;
    }
    getUserId(){
        return this.userId;
    }
    setUserId(userId){
        this.userId = userId;
    }
}

module.exports = class ScheduleUpdates{
    constructor(){

    }

    parseAndValidate(req){
        var promiseFunction = function(resolve, reject){
            var scheduleId = req.params.scheduleId;
            var lastUpdate = req.params.lastUpdate;
            var userId = req.params.userId;
            var reg =/^[+\-]?\d+$/;
            if(!reg.test(lastUpdate)){
                reject(new Error(HttpStatus.BAD_REQUEST, ErrorType.DATE_ERROR));
            } else if(!reg.test(scheduleId)){
                reject(new Error(HttpStatus.BAD_REQUEST, ErrorType.SCHEDULE_ID_ERROR));
            } else if(!reg.test(userId)){
                reject(new Error(HttpStatus.BAD_REQUEST, ErrorType.SCHEDULE_ID_ERROR));
            }else{
                var lastUpdateAttribute = new Date(parseInt(lastUpdate));
                var scheduleIdAttribute = parseInt(scheduleId);
                var attribute = new Attribute(scheduleIdAttribute, lastUpdateAttribute, userId)
                resolve(attribute);
            }

        }

        return new Promise(promiseFunction);
    }
}