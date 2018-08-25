var ApplicationHandlerSkeleton = require('../../ApplicationHandlerSkeleton.js')
var UserHelper = require('../../database/helpers/User.js');
var TrainerHelper = require('../../database/helpers/Trainer.js');
var PostUserPreprocessor = require("../preprocessors/PostUser.js");
var Response = require('./StandardResponse.js');


module.exports = class PostUser extends ApplicationHandlerSkeleton{
    constructor() {
        var preprocessor = new PostUserPreprocessor();
        super(preprocessor);
    }

    processFailure(res, err){
        var json = JSON.stringify(
            {
                'errorCode': err.statusType.status,
                'errorMessage': err.descriptionType.errorDescription
            }
        );
        res.set({'Content-Type': 'application/json', 'Accept': 'application/json'});
        res.status(err.statusType.status);
        res.send(json);
    }

    processRequest(res, attributes){
        var userHelper = new UserHelper();
        var trainerHelper = new TrainerHelper();
        var self = this;
        userHelper.insert([[attributes.id, attributes.email, attributes.displayName, attributes.userType, new Date(attributes.registrationDate), attributes.profileImageUrl]]).then(function(){
            if(attributes.userType == 1){
                 return trainerHelper.insert([[attributes.id, "ciaociao"]])
            } else {
                return Promise.resolve();
            }
        }, function(error){
            return Promise.reject(error);
        }).then(function(){
            
            var httpResponse = new Response(new Date().getTime(), null);
            var json = JSON.stringify(httpResponse);
            res.writeHead(200, {'Content-Type': 'application/json', 'Accept': 'application/json'});
            res.end(json);
        }, function(){
            self.processFailure(res, error)
        });
    }
}