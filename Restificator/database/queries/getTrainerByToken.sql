select 
user.id as user_id,
user.email as user_email,
user.password as user_password,
user.user_type*1 as user_type,
user.registrationDate as user_registrationDate,
user.profile_image_url as user_profileImageUrl,
user.display_name as user_displayName,
gym.id as gym_id,
gym.name as gym_name,
gym.logo_url as gym_logoUrl

from user inner join trainer on user.id = trainer.id 
inner join gym on trainer.gym_id = gym.id
where token = ? ;