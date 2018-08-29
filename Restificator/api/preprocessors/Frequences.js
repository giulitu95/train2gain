var Error = require('../../common/Error.js');
var HttpStatus = require('../../common/HttpStatus.js');
var HttpStatus = require("../../common/HttpStatus.js");
var ErrorType = require("../../common/ErrorType");
var Error = require("../../common/Error.js")

class Attribute{
    constructor(lastUpdate, userId){
        this.lastUpdate = lastUpdate;
        this.userId = userId;
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

module.exports = class Frequences{
    constructor(){

    }

    parseAndValidate(req){
        var promiseFunction = function(resolve, reject){
            var lastUpdate = req.params.lastUpdate;
            var userId = req.params.userId;
            var reg = /^[+\-]?\d+$/;
            if(!reg.test(lastUpdate)){
                reject(new Error(HttpStatus.BAD_REQUEST, ErrorType.DATE_ERROR));
            } else if(!reg.test(userId)){
                reject(new Error(HttpStatus.BAD_REQUEST, ErrorType.SCHEDULE_ID_ERROR));
            }else{
                var lastUpdateAttribute = new Date(parseInt(lastUpdate));
                var attribute = new Attribute(lastUpdateAttribute, userId)
                resolve(attribute);
            }

        }

        return new Promise(promiseFunction);
    }
}