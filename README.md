# Таблица для базы данных

<img width="1166" height="852" alt="uml" src="https://github.com/user-attachments/assets/4ace3668-87d4-4979-a3ae-43829079a56c" />


Получение всех пользователей
```
SELECT *
FROM users AS u
LEFT JOIN friends AS lf ON lf.user_id = u.id
JOIN users AS f ON lf.user_id = f.id
```
