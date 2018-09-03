var ApplicationHandlerSkeleton = require('../../ApplicationHandlerSkeleton.js')
var ExerciseHelper = require('../../database/helpers/Exercise.js');
var ExerciseListPreprocessor = require("../preprocessors/ExerciseList.js");
var Response = require('./StandardResponse.js');


module.exports = class ExerciseList extends ApplicationHandlerSkeleton{
    constructor() {
        var preprocessor = new ExerciseListPreprocessor();
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
        var exerciseHelper = new ExerciseHelper();
        var self = this;
        exerciseHelper.getExerciseListByLastUpdate( attributes.getLastUpdate()).then(function(exerciseList){
            var httpResponse = new Response(new Date().getTime(), exerciseList);
            var json = JSON.stringify(httpResponse);
            res.writeHead(200, {'Content-Type': 'application/json', 'Accept': 'application/json'});
            res.end(json);
        }, function(error){
            self.processFailure(error);
        });
    }
}