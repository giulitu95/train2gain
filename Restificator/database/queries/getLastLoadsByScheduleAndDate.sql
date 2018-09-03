SELECT
weight.id AS weight_id, 
weight.weight AS weight_weight,
weight.schedule_item_id AS weight_scheduleItemId, 
weight.date AS weight_date


FROM
schedule inner join daily_workout on schedule.id = daily_workout.schedule_id
inner join schedule_step on schedule_step.daily_workout_id = daily_workout.id
inner join schedule_set on schedule_set.schedule_step_id = schedule_step.id
inner join schedule_item on schedule_item.schedule_set_id = schedule_set.id
INNER JOIN weight ON schedule_item.id = weight.schedule_item_id

WHERE
schedule.id = ? AND
weight.date > ?