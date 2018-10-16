# This file is used by DatabaseUtilityTest to test the DatabaseUtility class.

USE stocks;

DROP TABLE IF EXISTS test;

CREATE TABLE test(
id INT NOT NULL AUTO_INCREMENT,
PRIMARY KEY ( id )
);
