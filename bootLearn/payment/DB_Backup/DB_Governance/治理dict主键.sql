SHOW TABLE STATUS LIKE 'payment_dict';

create table payment_dict_20250501
SELECT *
FROM payment_dict;

ALTER TABLE payment_dict_20250501
    ADD COLUMN isd INT AUTO_INCREMENT PRIMARY KEY FIRST;

UPDATE payment_record rec
    JOIN payment_dict_20250501 dict ON rec.payment_type = dict.payment_type
SET rec.payment_type = dict.isd;

truncate table payment_dict;

INSERT INTO payment_dict (payment_type, key_name, is_income, big_type, color)
SELECT isd, key_name, is_income, big_type, color
FROM payment_dict_20250501;
