var Error = require('../../common/Error.js');
var HttpStatus = require('../../common/HttpStatus.js');
var HttpStatus = require("../../common/HttpStatus.js");
var ErrorType = require("../../common/ErrorType");
var Error = require("../../common/Error.js")

class Attribute{
    constructor(token){
        this.token = token;
    }
    setToken(token){
        this.token = token;
    }
    getToken(){
        return this.token;
    }
}

module.exports = class TrainerByToken{
    constructor(){

    }

    parseAndValidate(req){
        var attributes = new Attribute(req.params.token);
        return Promise.resolve(attributes);
    }
}