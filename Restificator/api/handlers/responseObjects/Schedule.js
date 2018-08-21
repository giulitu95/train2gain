var AbstractResponse = require("./AbstractResponse.js");
module.exports = class Schedule extends AbstractResponse    {
    constructor(localId){
        super(localId);
        this.dailyWorkouts = [];
    }

    addDailyWorkout(dailyWorkout){
        this.dailyWorkouts.push(dailyWorkout);
    }
}