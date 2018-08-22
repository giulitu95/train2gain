var enumify = require('enumify');

class ErrorType extends enumify.Enum {};
ErrorType.initEnum({
    DB_CONNECTION_ERROR: {
        errorDescription: "Database connection error"
    },
    DB_QUERY_ERROR: {
        errorDescription: "Database query error"
    },
    DB_INTERNAL_ERROR: {
        errorDescription: "Database internal error"
    },
    DAY_ERROR: {
        errorDescription: "Sorry, this is an invalid day"
    },
    NO_SCHEDULE_ERROR: {
        errorDescription: "Sorry, there aren't schedules for those parameters"
    },
    
    SCHEDULE_ID_ERROR: {
        errorDescription: "Sorry, you must select a valid id"
    },
    DATE_ERROR:{
        errorDescription: "Sorry, you must select a valid timestamp date"
    }
});

module.exports = ErrorType;
