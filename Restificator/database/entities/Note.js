module.exports = class Note{
    constructor(id, text, scheduleStepId, date){
        this.id = id;
        this.text = text;
        this.scheduleStepId = scheduleStepId;
        this.date = date;
    }
}