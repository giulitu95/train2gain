SELECT 
id AS athlete_id,
weight AS athlete_weight,
height AS athlete_height,
first_workout_date AS athlete_firstWorkoutDate,
trainer_id AS athlete_trainerId
FROM athlete
where id = ?;