SELECT 
trainer.id AS trainer_id,
trainer.gym_id AS trainer_gymId,
gym.id AS gym_id,
gym.name AS gym_name,
gym.logo_url as gym_logoUrl 
FROM trainer left join gym ON trainer.gym_id = gym.id
WHERE trainer.id = ?;