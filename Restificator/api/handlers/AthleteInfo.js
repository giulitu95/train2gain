var ApplicationHandlerSkeleton = require('../../ApplicationHandlerSkeleton.js')
var UserHelper = require('../../database/helpers/User.js');
var ProfilePreprocessor = require("../preprocessors/Profile.js");
var Response = require('./StandardResponse.js');


module.exports = class AthleteInfo extends ApplicationHandlerSkeleton{
    constructor() {
        var preprocessor = new ProfilePreprocessor();
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
        userHelper.getAthleteById(attributes.getUserId()).then(function(athleteInfo){
            var httpResponse = new Response(new Date().getTime(), athleteInfo);
            var json = JSON.stringify(httpResponse);
            res.writeHead(200, {'Content-Type': 'application/json', 'Accept': 'application/json'});
            res.end(json);
        }, function(error){
            self.processFailure(error);
        });
    }
}