SELECT 
exercise.id AS exercise_id,
exercise.name AS exercise_name,
exercise.description AS exercise_description,
exercise.muscle_group_id as exercise_muscleGroup,
exercise.image_url AS exercise_imageUrl,
exercise.insert_date AS exercise_insertDate
FROM
exercise
WHERE
exercise.insert_date > ?