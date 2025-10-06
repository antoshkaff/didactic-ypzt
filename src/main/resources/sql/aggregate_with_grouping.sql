SELECT
    am.model_name,
    COUNT(aqh.id_query) AS total_queries_processed
FROM
    scoreboard.ai_model am
JOIN
    scoreboard.ai_query_history aqh ON am.model_id = aqh.model_id
GROUP BY
    am.model_name
ORDER BY
    total_queries_processed DESC;