# This file enables the creation of the stocks database tables needed for proper functioning of StockQuoteApplication.

USE stocks;

DROP TABLE IF EXISTS quotes CASCADE;

DROP TABLE IF EXISTS person_stock CASCADE;

DROP TABLE IF EXISTS person CASCADE;

CREATE TABLE quotes(
  id INT NOT NULL AUTO_INCREMENT,
  symbol VARCHAR(4) NOT NULL,
  time DATETIME NOT NULL,
  price DECIMAL(10, 2) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE person(
  id INT NOT NULL AUTO_INCREMENT,
  username VARCHAR(256) NOT NULL,
  first_name VARCHAR(256) NOT NULL,
  last_name VARCHAR(256) NOT NULL,
  birth_date DATETIME NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE person_stock(
  id INT NOT NULL AUTO_INCREMENT,
  person_id INT NOT NULL,
  stock_symbol VARCHAR(4) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (person_id) REFERENCES person (id)
);




