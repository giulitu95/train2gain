SELECT 
trainer.id AS trainer_id,
gym.name AS gym_name 
FROM trainer inner join gym ON trainer.gym_id = gym.id
WHERE trainer.id = ?;