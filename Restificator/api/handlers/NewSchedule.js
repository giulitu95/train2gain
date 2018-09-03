var ApplicationHandlerSkeleton = require('../../ApplicationHandlerSkeleton.js')
var ScheduleHelper = require('../../database/helpers/Schedule.js');
var NewSchedulePreprocessor = require("../preprocessors/NewSchedule.js");
var Response = require('./StandardResponse.js');


module.exports = class  NewSchedule extends ApplicationHandlerSkeleton{
    constructor() {
        var preprocessor = new NewSchedulePreprocessor();
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
        if(attributes.getLastScheduleId() === null){
            scheduleHelper.getLastScheduleByUserId(attributes.getUserId()).then(function(schedule){
                var httpResponse = new Response(new Date().getTime(), schedule);
                var json = JSON.stringify(httpResponse);
                res.writeHead(200, {'Content-Type': 'application/json', 'Accept': 'application/json'});
                res.end(json);
            }, function(error){
                self.processFailure(error);
            });
        } else {
            scheduleHelper.getNewScheduleByLastScheduleAndUserId(attributes.getLastScheduleId(), attributes.getUserId()).then(function(schedule){
                var httpResponse = new Response(new Date().getTime(), schedule);
                var json = JSON.stringify(httpResponse);
                res.writeHead(200, {'Content-Type': 'application/json', 'Accept': 'application/json'});
                res.end(json);
            }, function(error){
                self.processFailure(error);
            });
        }
    }
}