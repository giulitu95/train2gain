module.exports = class ScheduleSet{
    constructor(id, orderNumber, scheduleStepId){
        this.id = id; 
        this.orderNumber = orderNumber;
        this.scheduleStepId = scheduleStepId;
        this.scheduleItems = [];
    }
}