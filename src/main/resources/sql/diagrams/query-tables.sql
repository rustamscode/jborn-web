SELECT * FROM accounts
JOIN users
ON accounts.user_id = users.id;

SELECT * FROM transactions
JOIN accounts
ON transactions.account_id = accounts.id
WHERE accounts.date < CURRENT_DATE - INTERVAL '1 day';

SELECT u.email, SUM(balance) FROM accounts a
JOIN users u
ON a.user_id = u.id
GROUP BY u.email;