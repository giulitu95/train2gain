var ApplicationHandlerSkeleton = require('../../ApplicationHandlerSkeleton.js')
var LoadHelper = require('../../database/helpers/Load.js');
var LoadsPreprocessor = require("../preprocessors/LoadsAndNotes.js");
var Response = require('./StandardResponse.js');


module.exports = class Loads extends ApplicationHandlerSkeleton{
    constructor() {
        var preprocessor = new LoadsPreprocessor();
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
        var loadHelper = new LoadHelper();
        var self = this;
        loadHelper.getLoadByScheduleAndLastUpdate(attributes.getScheduleId(), attributes.getLastUpdate()).then(function(loads){
            var httpResponse = new Response(new Date().getTime(), loads);
            var json = JSON.stringify(httpResponse);
            res.writeHead(200, {'Content-Type': 'application/json', 'Accept': 'application/json'});
            res.end(json);
        }, function(error){
            self.processFailure(error);
        });
    }
}