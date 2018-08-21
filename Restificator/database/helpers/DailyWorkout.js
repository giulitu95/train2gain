var Loadable = require('./Loadable.js');
module.exports = class DailyWorkout extends Loadable{
    constructor(){
        super('./database/queries/insertDailyWorkout.sql');
    }
}