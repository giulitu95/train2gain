
module.exports = class Schedule{

    
    constructor(id, startDate, trainerId, athleteId){
        this.id = id; 
        this.athleteId = athleteId;
        this.startDate = startDate;
        this.trainerId = trainerId;
        this.dailyWorkouts = [];
    }

}