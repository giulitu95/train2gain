SELECT 
user.id AS user_id,
user.email AS user_email,
user.password AS user_password,
user.displayName AS user_displayName,
user.user_type+0 AS user_userType,
user.registrationDate AS user_registrationDate,
user.profile_image_url AS user_profileImageUrl
FROM
trainer INNER JOIN athlete ON trainer.id = athlete.trainer_id
INNER JOIN user ON athlete.id = user.id
WHERE 
trainer.id = ? AND athlete.insert_date > ?;