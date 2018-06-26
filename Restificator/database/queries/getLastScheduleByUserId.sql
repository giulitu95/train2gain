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
schedule inner join daily_workout on schedule.id = daily_workout.schedule_id
inner join schedule_step on schedule_step.daily_workout_id = daily_workout.id
inner join schedule_set on schedule_set.schedule_step_id = schedule_step.id
inner join schedule_item on schedule_item.schedule_set_id = schedule_set.id
inner join exercise on exercise.id = schedule_item.exercise_id

WHERE
 schedule.start_date = (
	select max(start_date)
    from schedule
    where schedule.athlete_id = ?)
    
order by daily_workout.id, schedule_step.id, schedule_set.id, schedule_item.id;