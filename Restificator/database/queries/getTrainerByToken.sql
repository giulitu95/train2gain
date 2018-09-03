select 
user.id as user_id,
user.email as user_email,
user.password as user_password,
user.user_type*1 as user_type,
user.registrationDate as user_registrationDate,
user.profile_image_url as user_profileImageUrl,
user.display_name as user_displayName,
trainer.token as trainer_token

from user inner join trainer on user.id = trainer.id 
where token = ? ;