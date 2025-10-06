SELECT
    result_id,
    user_id,
    question_id,
    result_correct,
    answer_time
FROM
    scoreboard.result_per_question
WHERE
    answer_time BETWEEN '2025-05-01 00:00:00' AND '2025-05-31 23:59:59';