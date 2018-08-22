module.exports = class ScheduleStep{
    constructor(id, restTime, orderNumber, scheduleStepTypeId, dailyWorkoutId){
        this.id = id; 
        this. restTime = restTime;
        this.orderNumber = orderNumber;
        this.scheduleStepTypeId = scheduleStepTypeId;
        this.dailyWorkoutId = dailyWorkoutId;
        this.scheduleSets = [];
    }
}