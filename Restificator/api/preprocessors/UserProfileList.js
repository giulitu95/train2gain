var Error = require('../../common/Error.js');
var HttpStatus = require('../../common/HttpStatus.js');
var HttpStatus = require("../../common/HttpStatus.js");
var ErrorType = require("../../common/ErrorType");
var Error = require("../../common/Error.js")

class Attribute{
    constructor(lastUpdate, trainerId){
        this.lastUpdate = lastUpdate;
        this.trainerId = trainerId;
    }
    setLastUpdate(lastUpdate){
        this.lastUpdate = lastUpdate;
    }
    getLastUpdate(){
        return this.lastUpdate;
    }
    getTrainerId(){
        return this.trainerId;
    }
    setTrainerId(trainerId){
        this.trainerId = trainerId;
    }
}

module.exports = class UserProfileList{
    constructor(){

    }

    parseAndValidate(req){
        var promiseFunction = function(resolve, reject){
            var lastUpdate = req.params.lastUpdate;
            var trainerId = req.params.trainerId;
            var reg = /^[+\-]?\d+$/;
            if(!reg.test(lastUpdate)){
                reject(new Error(HttpStatus.BAD_REQUEST, ErrorType.DATE_ERROR));
            } else if(!reg.test(trainerId)){
                reject(new Error(HttpStatus.BAD_REQUEST, ErrorType.SCHEDULE_ID_ERROR));
            }else{
                var lastUpdateAttribute = new Date(parseInt(lastUpdate));
                var attribute = new Attribute(lastUpdateAttribute, trainerId)
                resolve(attribute);
            }

        }

        return new Promise(promiseFunction);
    }
}