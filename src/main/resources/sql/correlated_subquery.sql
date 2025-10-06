WITH UserTopicCorrectCounts AS (
    SELECT
        q.topic_id,
        rpq.user_id,
        COUNT(rpq.result_id) AS correct_answers_count
    FROM
        scoreboard.question q
    JOIN
        scoreboard.result_per_question rpq ON q.question_id = rpq.question_id
    WHERE
        rpq.result_correct = TRUE
    GROUP BY
        q.topic_id, rpq.user_id
),
MaxCorrectPerTopic AS (
    SELECT
        topic_id,
        MAX(correct_answers_count) AS max_correct_count
    FROM
        UserTopicCorrectCounts
    GROUP BY
        topic_id
)
SELECT
    t.topic_name,
    u.name AS top_user_name,
    utcc.correct_answers_count AS correct_answers_in_topic
FROM
    scoreboard.topic t
JOIN
    UserTopicCorrectCounts utcc ON t.topic_id = utcc.topic_id
JOIN
    MaxCorrectPerTopic mct ON utcc.topic_id = mct.topic_id
                               AND utcc.correct_answers_count = mct.max_correct_count
JOIN
    scoreboard.user u ON utcc.user_id = u.id_user
ORDER BY
    t.topic_name, utcc.correct_answers_count DESC;