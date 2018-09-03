SELECT 
schedule.id AS schedule_id,
schedule.trainer_id AS schedule_trainerId,
schedule.athlete_id AS schedule_athleteId,
schedule.start_date AS schedule_startDate
FROM schedule 
WHERE athlete_id = ? AND
start_date > ?;