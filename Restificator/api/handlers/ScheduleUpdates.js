var ApplicationHandlerSkeleton = require('../../ApplicationHandlerSkeleton.js')
var UpdatesHelper = require('../../database/helpers/Updates.js');
var ScheduleUpdatesPreprocessor = require("../preprocessors/ScheduleUpdates.js");
var Response = require('./StandardResponse.js');


module.exports = class scheduleUpdates extends ApplicationHandlerSkeleton{
    constructor() {
        var preprocessor = new ScheduleUpdatesPreprocessor();
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
        var updatesHelper = new UpdatesHelper();
        var self = this;
        updatesHelper.getUpdatesByScheduleAndDate(attributes.getScheduleId(), attributes.getLastUpdate(), attributes.getUserId()).then(function(updates){
            var httpResponse = new Response(new Date().getTime(), updates);
            var json = JSON.stringify(httpResponse);
            res.writeHead(200, {'Content-Type': 'application/json', 'Accept': 'application/json'});
            res.end(json);
        }, function(error){
            self.processFailure(error);
        });
    }
}