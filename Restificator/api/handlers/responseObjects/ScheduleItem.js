var AbstractResponse = require("./AbstractResponse.js");
module.exports = class ScheduleItem extends AbstractResponse{
    constructor(localId){
        super(localId);
        this.scheduleSetId = null;
    }
    addScheduleSetId(scheduleSetId){
        this.scheduleSetId = scheduleSetId;
    }
}