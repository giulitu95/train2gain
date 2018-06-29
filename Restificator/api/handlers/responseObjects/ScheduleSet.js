var AbstractResponse = require("./AbstractResponse.js");
module.exports = class ScheduleSet extends AbstractResponse{
    constructor(localId){
        super(localId);
        this.scheduleStepId = null;
        this.scheduleItems = [];
    }

    addScheduleItem(scheduleItem){
        this.scheduleItems.push(scheduleItem);
    }
    addScheduleStepId(scheduleStepId){
        this.scheduleStepId = scheduleStepId;
    }
}