SELECT
    u.id_user,
    u.name,
    'Дуже активний користувач' AS user_status
FROM
    scoreboard.user u
JOIN
    (SELECT user_id, COUNT(id) AS quiz_count FROM scoreboard.quiz_result_summary GROUP BY user_id) AS user_quiz_counts
    ON u.id_user = user_quiz_counts.user_id
WHERE
    user_quiz_counts.quiz_count >= 5
UNION ALL
SELECT
    u.id_user,
    u.name,
    'Середній користувач' AS user_status
FROM
    scoreboard.user u
JOIN
    (SELECT user_id, COUNT(id) AS quiz_count FROM scoreboard.quiz_result_summary GROUP BY user_id) AS user_quiz_counts
    ON u.id_user = user_quiz_counts.user_id
WHERE
    user_quiz_counts.quiz_count BETWEEN 1 AND 4
UNION ALL
SELECT
    u.id_user,
    u.name,
    'Новий / Неактивний користувач' AS user_status
FROM
    scoreboard.user u
LEFT JOIN
    scoreboard.quiz_result_summary qrs ON u.id_user = qrs.user_id
WHERE
    qrs.id IS NULL;