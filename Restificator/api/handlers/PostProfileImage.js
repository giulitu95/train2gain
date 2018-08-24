var ApplicationHandlerSkeleton = require('../../ApplicationHandlerSkeleton.js')
var UserHelper = require('../../database/helpers/User.js');
var PostProfileImagePreprocessor = require("../preprocessors/PostProfileImage.js");
var Response = require('./StandardResponse.js');
var fs = require('fs');


module.exports = class PostProfileImage extends ApplicationHandlerSkeleton{
    constructor() {
        var preprocessor = new PostProfileImagePreprocessor();
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
        fs.writeFile('./images/profileImage.jpg', attributes, {encoding:'base64'}, function(err){
            console.log(err);
        })
    }
}