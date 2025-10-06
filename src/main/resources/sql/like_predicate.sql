SELECT
    id_user,
    name,
    email,
    registration_date
FROM
    scoreboard.user
WHERE
    name LIKE 'T%';