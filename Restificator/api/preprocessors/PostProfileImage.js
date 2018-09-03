var Error = require('../../common/Error.js');
var HttpStatus = require("../../common/HttpStatus.js");
var ErrorType = require("../../common/ErrorType");
var Error = require("../../common/Error.js");
var User = require("../../database/entities/User.js");

module.exports = class PostProfileImage{
    constructor(){
    }
    parseAndValidate(req){
        return Promise.resolve(req.body);
    }
}