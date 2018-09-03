module.exports = class StandardResponse{
    constructor(currentDate, content){
        this.currentDate = currentDate;
        this.content = content;
    }

    getCurrentDate(){
        return this.currentDate;
    }
    setCurrentDate(currentDate){
        this.currentDate = currentDate;
    }
    getUpdates(){
        return this.content;
    }
    setUpdates(content){
        this.content = content;
    }
}