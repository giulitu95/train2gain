var Loadable = require('./Loadable.js');
module.exports = class ScheduleItem extends Loadable{
    constructor(){
        super('./database/queries/insertScheduleItem.sql');
    }
}