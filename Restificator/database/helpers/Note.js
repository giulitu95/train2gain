var pool = require("./pool.js");
var HttpStatus = require("../../common/HttpStatus.js");
var ErrorType = require("../../common/ErrorType");
var Error = require("../../common/Error.js");
var NoteEntity = require("../entities/Note.js");
var FileReader = require('fs');


module.exports = class Note{
    getLastNotesByQuery(result){
        if(result.length == 0){
            return null
        } else{
            var notes = [];
            for(var i = 0; i < result.length; i++){
                var note = new NoteEntity(result[i].note_id, result[i].note_text, result[i].note_scheduleStepId, new Date(result[i].note_date).getTime(), result[i].note_authorId);
                notes.push(note);
            }
            //console.log(JSON.stringify(loads, null, 4));
            return notes;
        }
    }
    getNotesByScheduleAndLastUpdate(scheduleId, lastUpdate){
        var self = this;
        var promiseFunction = function(resolve, reject){

            pool.getConnection(function(err, connection){
                if(err){
                    var error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.DB_CONNECTION_ERROR);
                    reject(error);   
                } else{
                    //read query sql from file
                    FileReader.readFile('./database/queries/getLastNotesByScheduleAndDate.sql', 'utf8', function(err, sqlQuery){
                        if(err){
                            var error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.DB_CONNECTION_ERROR);
                            reject(error);
                        } else{
                            connection.query(sqlQuery, [lastUpdate, scheduleId], function(err, result){
                                
                                //console.log(result);
                                if(err){
                                    var error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.DB_CONNECTION_ERROR);
                                    connection.release();
                                    reject(error);
                                } else {
                                    console.log(scheduleId);
                                    console.log(lastUpdate);
                                    connection.release();
                                    resolve(self.getLastNotesByQuery(result));
                                }
                            });
                        }
                    });
                }
            });
        }
        return new Promise(promiseFunction);
    }

}