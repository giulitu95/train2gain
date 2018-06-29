var Loadable = require('./Loadable.js');
module.exports = class ScheduleSet extends Loadable{
    constructor(){
        super('./database/queries/insertScheduleSet.sql');
    }
}