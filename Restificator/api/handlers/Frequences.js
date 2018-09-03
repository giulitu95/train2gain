var ApplicationHandlerSkeleton = require('../../ApplicationHandlerSkeleton.js')
var FrequenceHelper = require('../../database/helpers/Frequence.js');
var FrequencesPreprocessor = require("../preprocessors/Frequences.js");
var Response = require('./StandardResponse.js');


module.exports = class Frequences extends ApplicationHandlerSkeleton{
    constructor() {
        var preprocessor = new FrequencesPreprocessor();
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
        var frequenceHelper = new FrequenceHelper();
        var self = this;
        frequenceHelper.getFrequencesByUserAndLastUpdate(attributes.getUserId(), attributes.getLastUpdate()).then(function(frequences){
            var httpResponse = new Response(new Date().getTime(), frequences);
            var json = JSON.stringify(httpResponse);
            res.writeHead(200, {'Content-Type': 'application/json', 'Accept': 'application/json'});
            res.end(json);
        }, function(error){
            self.processFailure(error);
        });
    }
}