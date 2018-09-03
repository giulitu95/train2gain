
var Validator = require('jsonschema').Validator;
module.exports = class User{
    constructor(id, email, password, displayName, userType, registrationDate, profileImageUrl){
        this.id = id;
        this.email = email;
        this.password = password;
        this.displayName = displayName;
        this.userType = userType;
        this.registrationDate = registrationDate;
        this.profileImageUrl = profileImageUrl;
    }

    static validateSchema(user){
        var validator = new Validator();
        return validator.validate(user, this.getSchema()).errors.length == 0;
    }

    static getSchema(){
        var user = {
            "id": "/SimpleUser",
            "type": "object",
            "properties": {
                "id": {"type": "integer"},
                "email": {"type": "string"},
                "password": {"type": "string"},
                "displayName": {"type": "string"},
                "userType": {"type": "integer"},
                "registrationDate": {"type": "integer"},
                "profileImageUrl": {"type": "string"}
            },
            "required":["id", "email", "displayName", "userType", "registrationDate"]
          };
        return user;
    }
}