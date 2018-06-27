SELECT 
user.id AS user_id,
user.email AS user_email,
user.password AS user_password,
user.name AS user_name,
user.last_name AS user_lastName,
user.user_type AS user_userType,
user.registrationDate AS user_registrationDate
FROM
user
WHERE 
user.id = ?;