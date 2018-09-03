var Error = require('../../common/Error.js');
var HttpStatus = require("../../common/HttpStatus.js");
var ErrorType = require("../../common/ErrorType");
var Error = require("../../common/Error.js");
var User = require("../../database/entities/User.js");

module.exports = class PostUser{
    constructor(){
    }
    parseAndValidate(req){
        var promiseFunction = function(resolve, reject){
            var user = req.body;
            if(User.validateSchema(user)){
                resolve(user);
            } else{
                reject(new Error(HttpStatus.BAD_REQUEST, ErrorType.JSON_FORMAT_ERROR));
            }
        }
        return new Promise(promiseFunction);
    }
}