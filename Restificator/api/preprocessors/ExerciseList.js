var Error = require('../../common/Error.js');
var HttpStatus = require('../../common/HttpStatus.js');
var HttpStatus = require("../../common/HttpStatus.js");
var ErrorType = require("../../common/ErrorType");
var Error = require("../../common/Error.js")

class Attribute{
    constructor(lastUpdate){
        this.lastUpdate = lastUpdate;
    }
    setLastUpdate(lastUpdate){
        this.lastUpdate = lastUpdate;
    }
    getLastUpdate(){
        return this.lastUpdate;
    }
}

module.exports = class ExerciseList{
    constructor(){

    }

    parseAndValidate(req){
        var promiseFunction = function(resolve, reject){
            var lastUpdate = req.params.lastUpdate;
            var reg = /^\d+$/;
            if(!reg.test(lastUpdate)){
                reject(new Error(HttpStatus.BAD_REQUEST, ErrorType.DATE_ERROR));
            } else{
                var lastUpdateAttribute = new Date(parseInt(lastUpdate));
                var attribute = new Attribute(lastUpdateAttribute)
                resolve(attribute);
            }

        }

        return new Promise(promiseFunction);
    }
}