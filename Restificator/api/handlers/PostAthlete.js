var ApplicationHandlerSkeleton = require('../../ApplicationHandlerSkeleton.js')
var AthleteHelper = require('../../database/helpers/Athlete.js');
var PostAthletePreprocessor = require("../preprocessors/PostAthleteInfo.js");
var Response = require('./StandardResponse.js');


module.exports = class PostAthlete extends ApplicationHandlerSkeleton{
    constructor() {
        var preprocessor = new PostAthletePreprocessor();
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
        var athleteHelper = new AthleteHelper();
        var self = this;
        athleteHelper.insert([[attributes.id, attributes.weight, attributes.height, new Date(attributes.first_workout_date), attributes.trainer_id, new Date()]]).then(function(){
            var httpResponse = new Response(new Date().getTime(), null);
            var json = JSON.stringify(httpResponse);
            res.writeHead(200, {'Content-Type': 'application/json', 'Accept': 'application/json'});
            res.end(json);
        }, function(error){
            self.processFailure(res, error)
        });
    }
}