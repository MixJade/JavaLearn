CREATE TABLE payment_record_20250430
SELECT *
FROM payment_record;

truncate table payment_record;

INSERT INTO payment_record (payment_type, is_income, money, pay_date, remark)
SELECT payment_type, is_income, money, pay_date, remark
FROM payment_record_20250430
ORDER BY pay_date;
