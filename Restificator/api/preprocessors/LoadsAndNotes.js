var Error = require('../../common/Error.js');
var HttpStatus = require('../../common/HttpStatus.js');
var HttpStatus = require("../../common/HttpStatus.js");
var ErrorType = require("../../common/ErrorType");
var Error = require("../../common/Error.js")

class Attribute{
    constructor(userId, scheduleId, lastUpdate ){
        this.userId = userId;
        this.scheduleId = scheduleId;
        this.lastUpdate = lastUpdate;
    }

    getUserId(){
        return this.userId;
    }
    setUserId(userId){
        this.userId = userId;
    }
    getScheduleId(){
        return this.scheduleId;
    }
    setScheduleId(scheduleId){
        this.scheduleId = scheduleId    ;
    }
    getLastUpdate(){
        return this.lastUpdate;
    }
    setLastUpdate(lastUpdate){
        this.lastUpdate = lastUpdate;
    }
}

module.exports = class Loads{
    constructor(){

    }
    parseAndValidate(req){
        var promiseFunction = function(resolve, reject){
            var userId = req.params.userId;
            var scheduleId = req.params.scheduleId;
            var lastUpdate = req.params.lastUpdate;
            var reg = /^[+\-]?\d+$/;
            if(!reg.test(scheduleId)){
                reject(new Error(HttpStatus.BAD_REQUEST, ErrorType.SCHEDULE_ID_ERROR));
            } else if(!reg.test(lastUpdate)){
                reject(new Error(HttpStatus.BAD_REQUEST, ErrorType.DATE_ERROR));
            } else if(!reg.test(userId)){
                reject(new Error(HttpStatus.BAD_REQUEST, ErrorType.SCHEDULE_ID_ERROR));
            } else{
                var userIdAttribute = parseInt(userId);
                var scheduleIdAttribute = parseInt(scheduleId);
                var lastUpdateAttribute = new Date(parseInt(lastUpdate));
                var attribute = new Attribute(userIdAttribute, scheduleIdAttribute, lastUpdateAttribute);
                resolve(attribute);
            }

        }

        return new Promise(promiseFunction);
    }
}