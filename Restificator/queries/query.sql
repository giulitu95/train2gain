SELECT
schedule.id as scheduleId,
schedule.athlete_id as scheduleAthleteId, 
schedule.start_date as scheduleStartDate, 
schedule.end_date as scheduleEndDate, 
schedule.trainer_id as scheduleTrainerId, 
daily_workout.id as dailyWorkoutId, 
daily_workout.order_number as dailyWorkoutOrderNumber, 
daily_workout.schedule_id as dailyWorkoutScheduleId,
schedule_step.id as scheduleStepId, 
schedule_step.restTime as scheduleStepRestTime, 
schedule_step.orderNum as scheduleStepOrderNumber, 
schedule_step.schedule_step_type_id as scheduleStepTypeId, 
schedule_step.daily_workout_id as scheduleStepDailyWorkoutId,
schedule_set.id as scheduleSetId, 
schedule_set.order_number as scheduleSetOrderNumber, 
schedule_set.schedule_step_id as scheduleSetScheduleStepId,
schedule_item.id as scheduleItemId, 
schedule_item.exercise_id as scheduleItemExerciseId, 
schedule_item.order_number as scheduleItemOrderNumber,
schedule_item.weight as scheduleItemWeight, 
schedule_item.reps as scheduleItemReps,
schedule_item.schedule_set_id as scheduleItemScheduleSetId
FROM 
schedule inner join daily_workout on schedule.id = daily_workout.schedule_id 
inner join schedule_step on daily_workout.id = schedule_step.daily_workout_id
inner join schedule_set on schedule_step.id = schedule_set.schedule_step_id
inner join schedule_item on schedule_set.id = schedule_item .schedule_set_id

WHERE 
schedule.athlete_id = 1 AND
schedule.start_date < '2018-6-13' AND schedule.end_date > '2018-6-13'

ORDER BY daily_workout.id ASC, schedule_step.id ASC, schedule_set.id ASC, schedule_item.id ASC;