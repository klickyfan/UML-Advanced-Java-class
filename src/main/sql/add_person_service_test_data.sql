# This file is used by DatabaseStockServiceTest to test the DatabaseStockService class.

USE stocks;

INSERT INTO person (first_name, last_name, birth_date) VALUES('Kim', 'Jones', STR_TO_DATE('3/11/1968', '%m/%d/%Y'));
INSERT INTO person (first_name, last_name, birth_date) VALUES('Rob', 'Jones', STR_TO_DATE('1/19/1968', '%m/%d/%Y'));
INSERT INTO person (first_name, last_name, birth_date) VALUES('Michaela', 'Jones', STR_TO_DATE('4/6/2000', '%m/%d/%Y'));
INSERT INTO person (first_name, last_name, birth_date) VALUES('Ron', 'Swanson', STR_TO_DATE('4/2/1962', '%m/%d/%Y'));
INSERT INTO person (first_name, last_name, birth_date) VALUES('Leslie', 'Knope', STR_TO_DATE('1/18/1975', '%m/%d/%Y'));

INSERT INTO person_stock (person_id, stock_symbol) VALUES(1, 'GOOG');
INSERT INTO person_stock (person_id, stock_symbol) VALUES(1, 'AAPL');
INSERT INTO person_stock (person_id, stock_symbol) VALUES(1, 'NFLX');

INSERT INTO person_stock (person_id, stock_symbol) VALUES(2, 'RTN');
INSERT INTO person_stock (person_id, stock_symbol) VALUES(2, 'BMY');

INSERT INTO person_stock (person_id, stock_symbol) VALUES(3, 'INTC');

INSERT INTO person_stock (person_id, stock_symbol) VALUES(4, 'NFLX');

INSERT INTO person_stock (person_id, stock_symbol) VALUES(5, 'NFLX');
