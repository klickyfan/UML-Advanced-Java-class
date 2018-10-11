# This file enables the creation of the stocks database tables needed for proper functioning of StockQuoteApplication.

USE stocks;

DROP TABLE IF EXISTS quotes;

CREATE TABLE quotes(
id INT NOT NULL AUTO_INCREMENT,
symbol VARCHAR(4) NOT NULL,
time DATETIME NOT NULL,
price DECIMAL(10, 2) NOT NULL,
PRIMARY KEY ( id )
);


