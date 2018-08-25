var express = require('express');
var bodyParser = require('body-parser');
var HttpStatus = require("./common/HttpStatus.js");
var ErrorType = require("./common/ErrorType");
var UpdatesHandler = require('./api/handlers/ScheduleUpdates.js');
var NewScheduleHandler = require('./api/handlers/NewSchedule.js');
var LoadsHandler = require('./api/handlers/Loads.js');
var NotesHandler = require('./api/handlers/Notes.js');
var FrequenceHandler = require('./api/handlers/Frequences.js');
var UserProfileHandler = require("./api/handlers/UserProfile.js");
var AthleteInfoHandler = require("./api/handlers/AthleteInfo.js");
var TrainerInfoHandler = require('./api/handlers/TrainerInfo.js');
var PostSchedulePreprocessor = require('./api/preprocessors/PostSchedule.js');
var Schedule = require('./database/helpers/Schedule.js');
var PostScheduleHandler = require('./api/handlers/PostSchedule.js');
var PostScheduleUpdates = require('./api/handlers/PostScheduleUpdates.js');
var ScheduleListHandler = require('./api/handlers/ScheduleList.js');
var ExerciseListHandler = require('./api/handlers/Exercise.js');
var UserProfileListHandler = require('./api/handlers/UserProfileList.js');
var PostUserInfo = require("./api/handlers/PostUser");
var PostAthleteHandler = require("./api/handlers/PostAthlete")
var fs = require('fs');
var TrainerByToken = require("./api/handlers/TrainerByToken");
var PostProfileImage = require("./api/handlers/PostProfileImage");
var app = express();
var multer = require('multer');
var path = require('path');
var md5 = require('md5');
//catch sintax request error

var storage = multer.diskStorage({
    destination: function (req, file, cb) {
      cb(null, './images/profileImage')
    },
    filename: function (req, file, cb) {
      cb(null, md5(file.originalname) + path.extname(file.originalname));
    }
  })
  
var upload = multer({ 
    storage: storage,
    fileFilter: function(req, file, callback){
        if(file.mimetype == "image/jpeg" || file.mimetype == "image/png"){
            callback(null, true);
        } else {
            callback(new Error('invalid extension'));
        }
    }
}).single('image');
app.use(bodyParser.json());
app.use(function(err, req, res, next) {
    if (err instanceof SyntaxError && err.status === 400) {
        res.status(err.status).send(ErrorType.SINTAX_REQUEST_ERROR.errorDescription);
    }   
});
/*
 Specifying userId and id of last schedule retrives if exist the last schedule created by trainer, if I pass null in lastScheduleId 
 the api retrives the last schedule
*/ 
app.get('/api/newSchedule/userId/:userId/lastScheduleId/:lastScheduleId', function(req, res){
    var newScheduleHandler = new NewScheduleHandler(req);
    newScheduleHandler.dispatch(req, res);
});
/*
 Retrives last updates done by the trainer
*/
app.get('/api/updates/userId/:userId/scheduleId/:scheduleId/lastUpdate/:lastUpdate', function(req, res){
    var updatesHandler = new UpdatesHandler(req);
    updatesHandler.dispatch(req, res);
});
/*
 Retrives loads giving the last local date updates
*/
app.get('/api/loads/userId/:userId/scheduleId/:scheduleId/lastUpdate/:lastUpdate', function(req, res){
    var loadsHandler = new LoadsHandler(req);
    loadsHandler.dispatch(req, res);
});
app.get('/api/notes/userId/:userId/scheduleId/:scheduleId/lastUpdate/:lastUpdate', function(req, res){
    var notesHandler = new NotesHandler(req);
    notesHandler.dispatch(req, res);
});

app.get('/api/trainingFrequencesList/userId/:userId/lastUpdate/:lastUpdate', function(req, res){
    var frequenceHandler = new FrequenceHandler(req);
    frequenceHandler.dispatch(req,res);
});

app.get('/api/userProfile/userId/:userId', function(req, res){
    var userProfileHandler = new UserProfileHandler(req);
    userProfileHandler.dispatch(req, res);
});

app.get('/api/athleteInfo/userId/:userId', function(req, res){
    var athleteInfoHandler = new AthleteInfoHandler(req);
    athleteInfoHandler.dispatch(req, res);
});

app.get('/api/trainerInfo/userId/:userId', function(req, res){
    var trainerInfoHandler = new TrainerInfoHandler(req);
    trainerInfoHandler.dispatch(req, res);
});


app.get('/api/scheduleList/userId/:userId/lastUpdate/:lastUpdate', function(req, res){
    var scheduleList = new ScheduleListHandler(req);
    scheduleList.dispatch(req, res);
});

app.get('/api/exerciseList/lastUpdate/:lastUpdate', function(req, res){
    var exerciseListHandler = new ExerciseListHandler(req);
    exerciseListHandler.dispatch(req, res); 
});
app.get('/api/userProfileList/trainerId/:trainerId/lastUpdate/:lastUpdate', function(req, res){
    var userProfileList = new UserProfileListHandler(req);  
    userProfileList.dispatch(req, res);
});
/*+++++++++++++++++++++++++++++++++++++ POST METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++ */


app.post('/api/schedule', function(req, res){
    var postScheduleHandler = new PostScheduleHandler();
    postScheduleHandler.dispatch(req, res);
});

app.post('/api/updates/userId/:userId/scheduleId/:scheduleId', function(req, res){
    var postScheduleUpdates = new PostScheduleUpdates();
    postScheduleUpdates.dispatch(req, res);
});

app.post('/api/userProfile', function(req, res){
    var postUserInfo = new PostUserInfo();
    postUserInfo.dispatch(req, res);
});

app.post('/api/athleteInfo', function(req, res){
    var postAthleteHandler = new PostAthleteHandler();
    postAthleteHandler.dispatch(req, res);
});

app.get('/api/trainer/token/:token', function(req, res){
    var trainerByToken = new TrainerByToken();
    trainerByToken.dispatch(req, res);
})


//listen in a specific port 
app.listen((process.env.PORT || 8080));

//check status
console.log('Server running at http://localhost:8080/');