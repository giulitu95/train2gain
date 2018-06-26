module.exports = class ScheduleItem{
    constructor(id, exercise, orderNumber, load, reps, scheduleSetId, exerciseId){
        this.id = id;
        this.orderNumber = orderNumber;
        this.load = load;
        this.reps = reps;
        this.scheduleSetId = scheduleSetId;
        this.exerciseId = exerciseId;
        this.exercise = exercise;
    }
}