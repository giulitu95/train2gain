var ApplicationHandlerSkeleton = require('../../ApplicationHandlerSkeleton.js')
var PostScheduleUpdatesPreprocessor = require("../preprocessors/PostScheduleUpdates.js");

module.exports = class Loads extends ApplicationHandlerSkeleton{
    constructor() {
        var preprocessor = new PostScheduleUpdatesPreprocessor();
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
    processRequest(res, updates){
        for(var i = 0; i < updates.length; i++){
            
        }
    }
}