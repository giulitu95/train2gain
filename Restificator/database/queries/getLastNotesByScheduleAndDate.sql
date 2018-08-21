SELECT
note.id AS note_id, 
note.text AS note_text, 
note.schedule_step_id AS note_scheduleStepId, 
note.date AS note_date,
note.author_id AS note_authorId

FROM
schedule INNER JOIN daily_workout ON schedule.id = daily_workout.schedule_id
INNER JOIN schedule_step ON daily_workout.id = schedule_step.daily_workout_id
INNER JOIN note ON schedule_step.id = note.schedule_step_id

WHERE
note.date > ?
AND schedule.id = ?