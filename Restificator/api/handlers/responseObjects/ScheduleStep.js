var AbstractResponse = require("./AbstractResponse.js");
module.exports = class ScheduleStep extends AbstractResponse{
    constructor(localId){
        super(localId);
        this.dailyWorkoutId = null;
        this.scheduleSets = [];
    }

    addScheduleSet(scheduleSet){
        this.scheduleSets.push(scheduleSet);
    }
    addDailyWorkoutId(dailyWorkoutId){
        this.dailyWorkoutId = dailyWorkoutId;
    }
}