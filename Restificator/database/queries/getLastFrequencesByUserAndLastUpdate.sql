SELECT
id AS frequence_id,
date AS frequence_date,
daily_workout_id AS frequence_dailyWorkoutId,
athlete_id AS frequence_athleteId
FROM
frequence
WHERE
athlete_id = ? AND
date > ? AND
date >= DATE_SUB(NOW(), INTERVAL 90	 DAY);