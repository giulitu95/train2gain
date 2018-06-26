SELECT 
updates.id updates_id,
updates.type+0 AS updates_type, 
updates.schedule_step_id AS updates_scheduleStepId,
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
schedule INNER JOIN daily_workout on schedule.id = daily_workout.schedule_id 
INNER JOIN updates on daily_workout.id = updates.daily_workout_id 
INNER JOIN ( 
	SELECT schedule_step_id, MAX(date) AS maxDate
	FROM 	updates
	GROUP BY schedule_step_id
) AS dates ON updates.date= dates.maxDate AND updates.schedule_step_id = dates.schedule_step_id 
LEFT JOIN schedule_step on updates.schedule_step_id = schedule_step.id 
LEFT JOIN schedule_set on schedule_set.schedule_step_id = schedule_step.id
LEFT JOIN schedule_item on schedule_item.schedule_set_id = schedule_set.id
LEFT JOIN exercise on schedule_item.exercise_id = exercise.id 	

WHERE 
updates.date > ? AND	
schedule.id = ? AND
schedule.athlete_id = ?
ORDER BY updates.id, schedule_set.id, schedule_item.id;