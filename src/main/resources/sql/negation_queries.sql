SELECT
    u.id_user,
    u.name,
    u.email
FROM
    scoreboard.user u
LEFT JOIN
    scoreboard.ai_recommendation air ON u.id_user = air.user_id
    AND air.recommendation_date >= CURRENT_DATE - INTERVAL '60' DAY
WHERE
    air.recommendation_id IS NULL;

SELECT
    u.id_user,
    u.name,
    u.email
FROM
    scoreboard.user u
WHERE
    u.id_user NOT IN (
        SELECT DISTINCT user_id
        FROM scoreboard.ai_recommendation
        WHERE recommendation_date >= CURRENT_DATE - INTERVAL '60' DAY
    );

SELECT
    u.id_user,
    u.name,
    u.email
FROM
    scoreboard.user u
WHERE
    NOT EXISTS (
        SELECT 1
        FROM scoreboard.ai_recommendation air
        WHERE air.user_id = u.id_user
          AND air.recommendation_date >= CURRENT_DATE - INTERVAL '60' DAY
    );