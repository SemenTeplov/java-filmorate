# Таблица для базы данных

<img width="1404" height="736" alt="2025-09-23_20-43-46" src="https://github.com/user-attachments/assets/760cad66-8cf5-4620-a501-a791d592644b" />

Получение всех пользователей
```
SELECT *
FROM users AS u
LEFT JOIN friends AS lf ON lf.user_id = u.id
JOIN users AS f ON lf.user_id = f.id
```
Получение всех фильмов
```
SELECT *
FROM films AS f
JOIN generse AS g ON g.film_id = f.id
LEFT JOIN likes AS l ON l.film_id = f.id
```
