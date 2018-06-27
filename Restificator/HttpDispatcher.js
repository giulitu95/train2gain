var express = require('express');
var bodyParser = require('body-parser');
var UpdatesHelper = require('./database/helpers/Updates.js');
var UpdatesHandler = require('./api/handlers/ScheduleUpdates.js');
var ScheduleHelper = require('./database/helpers/Schedule.js');
var NewScheduleHandler = require('./api/handlers/NewSchedule.js');
var LoadsHandler = require('./api/handlers/Loads.js');
var NotesHandler = require('./api/handlers/Notes.js');
var FrequenceHandler = require('./api/handlers/Frequences.js');

var LoadHelper = require('./database/helpers/Load.js');
var fs = require('fs');

var app = express();
app.use(bodyParser.json());
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
//listen in a specific port
app.listen((process.env.PORT || 8080));

//check status
console.log('Server running at http://localhost:8080/');