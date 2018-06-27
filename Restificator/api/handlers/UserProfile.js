var ApplicationHandlerSkeleton = require('../../ApplicationHandlerSkeleton.js')
var UserHelper = require('../../database/helpers/User.js');
var UserProfilePreprocessor = require("../preprocessors/Profile.js");
var Response = require('./StandardResponse.js');


module.exports = class UserProfile extends ApplicationHandlerSkeleton{
    constructor() {
        var preprocessor = new UserProfilePreprocessor();
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
        var self = this;
        userHelper.getUserById(attributes.getUserId()).then(function(userProfile){
            var httpResponse = new Response(new Date().getTime(), userProfile);
            var json = JSON.stringify(httpResponse);
            res.writeHead(200, {'Content-Type': 'application/json', 'Accept': 'application/json'});
            res.end(json);
        }, function(error){
            self.processFailure(error);
        });
    }
}