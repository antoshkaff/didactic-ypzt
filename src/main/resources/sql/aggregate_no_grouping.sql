SELECT
    COUNT(report_id) AS ai_reports_in_last_30_days
FROM
    scoreboard.ai_report
WHERE
    creation_date >= CURRENT_DATE - INTERVAL '30' DAY;