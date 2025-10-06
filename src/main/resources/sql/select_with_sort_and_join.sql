SELECT
    u.name AS user_name,
    qrs.total_correct_questions,
    qrs.id AS quiz_summary_id
FROM
    scoreboard.user u
JOIN
    scoreboard.quiz_result_summary qrs ON u.id_user = qrs.user_id
WHERE
    u.name = 'Tankgewehr'
ORDER BY
    qrs.total_correct_questions DESC;