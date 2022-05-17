
CREATE TABLE beer(
id varchar(100) NOT NULL PRIMARY KEY, beer_name VARCHAR(100), beer_style VARCHAR(100), created_date DATE,
last_modified_date DATE, min_on_hand INT, quantity_to_brew INT,
price DECIMAL(4,2),
upc VARCHAR(100),
version INT
);