SELECT
schedule.id AS schedule_id,
schedule.athlete_id AS schedule_athleteId, 
schedule.start_date AS schedule_startDate, 
schedule.trainer_id AS schedule_trainerId, 
daily_workout.id AS dailyWorkout_id, 
daily_workout.order_number AS dailyWorkout_orderNumber, 
daily_workout.schedule_id AS dailyWorkout_scheduleId,
schedule_step.id AS scheduleStep_id,
schedule_step.restTime AS scheduleStep_restTime,
schedule_step.orderNum AS scheduleStep_orderNumber,
schedule_step.schedule_step_type_id AS scheduleStep_type,
schedule_step.daily_workout_id AS scheduleStep_dailyWorkoutId,
schedule_set.id AS scheduleSet_id,
schedule_set.order_number AS scheduleSet_orderNumber,
schedule_set.schedule_step_id AS scheduleSet_scheduleStepId,
schedule_item.id AS scheduleItem_id,
schedule_item.reps AS scheduleItem_reps,
schedule_item.order_number AS scheduleItem_orderNumber,
schedule_item.schedule_set_id AS scheduleItem_scheduleSetId,
schedule_item.exercise_id AS scheduleItem_exerciseId,
exercise.id  AS exercise_id,
exercise.name AS exercise_name,
exercise.description AS exercise_description,
exercise.muscle_group_id AS exercise_muscleGroupId,
exercise.image_url AS exercise_imageUrl

FROM 
schedule INNER JOIN daily_workout ON schedule.id = daily_workout.schedule_id 
INNER JOIN schedule_step ON daily_workout.id = schedule_step.daily_workout_id 
INNER JOIN schedule_set ON schedule_step.id = schedule_set.schedule_step_id
INNER JOIN schedule_item ON schedule_set.id = schedule_item.schedule_set_id
INNER JOIN exercise ON schedule_item.exercise_id = exercise.id,
(
	SELECT start_date 
    FROM  schedule 
	WHERE schedule.id = ? 
) AS current_schedule


WHERE 
schedule.athlete_id = ? AND
schedule.start_date = (
	SELECT max(schedule.start_date)
    FROM schedule
    WHERE schedule.athlete_id = ?
) AND

schedule.start_date > current_schedule.start_date
ORDER BY daily_workout.id ASC, schedule_step.id ASC, schedule_set.id ASC, schedule_item.id ASC;

