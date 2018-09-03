var Loadable = require('./Loadable.js');
module.exports = class ScheduleStep extends Loadable{
    constructor(){
        super('./database/queries/insertScheduleStep.sql');
    }
}