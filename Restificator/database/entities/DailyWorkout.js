module.exports = class DailyWorkout{
    constructor(id, orderNumber, scheduleId){
        this.id = id; 
        this.orderNumber = orderNumber;
        this.scheduleId = scheduleId;
        this.scheduleSteps = [];
    }
}