var AbstractResponse = require("./AbstractResponse.js");
module.exports = class DailyWorkout extends AbstractResponse{
    constructor(localId){
        super(localId);
        this.scheduleId = null;
        this.scheduleSteps = [];
    }

    addScheduleStep(scheduleStep){
        this.scheduleSteps.push(scheduleStep);
    }
    addScheduleId(scheduleId){
        this.scheduleId = scheduleId;
    }
}