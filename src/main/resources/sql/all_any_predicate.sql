SELECT
    u.name AS user_name,
    qrs.total_correct_questions,
    qrs.id AS quiz_summary_id
FROM
    scoreboard.user u
JOIN
    scoreboard.quiz_result_summary qrs ON u.id_user = qrs.user_id
WHERE
    qrs.total_correct_questions > ALL (
        SELECT
            qrs_sub.total_correct_questions
        FROM
            scoreboard.quiz_result_summary qrs_sub
        JOIN
            scoreboard.user u_sub ON qrs_sub.user_id = u_sub.id_user
        WHERE
            YEAR(u_sub.registration_date) = 2025
    )
ORDER BY
    qrs.total_correct_questions DESC;