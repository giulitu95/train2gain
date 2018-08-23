module.exports = class Note{
    constructor(id, text, scheduleStepId, date, authorId){
        this.id = id;
        this.authorId = authorId,
        this.text = text;
        this.scheduleStepId = scheduleStepId;
        this.date = date;
    }
}