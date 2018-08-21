var ApplicationHandlerSkeleton = require('../../ApplicationHandlerSkeleton.js')
var ScheduleHelper = require('../../database/helpers/Schedule.js');
var ScheduleListPreprocessor = require("../preprocessors/ScheduleList.js");
var Response = require('./StandardResponse.js');


module.exports = class ScheduleList extends ApplicationHandlerSkeleton{
    constructor() {
        var preprocessor = new ScheduleListPreprocessor();
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
        var scheduleHelper = new ScheduleHelper();
        var self = this;
        scheduleHelper.getScheduleListByUserIdAndLastUpdate(attributes.getUserId(), attributes.getLastUpdate()).then(function(scheduleList){
            var httpResponse = new Response(new Date().getTime(), scheduleList);
            var json = JSON.stringify(httpResponse);
            res.writeHead(200, {'Content-Type': 'application/json', 'Accept': 'application/json'});
            res.end(json);
        }, function(error){
            self.processFailure(error);
        });
    }
}