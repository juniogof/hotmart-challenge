CREATE TABLE IF NOT EXISTS seller (
	id int NOT NULL AUTO_INCREMENT,
	name varchar(50) NOT NULL,
	PRIMARY KEY (id)
); 

CREATE TABLE IF NOT EXISTS buyer (
	id int NOT NULL AUTO_INCREMENT,
	name varchar(50) NOT NULL,
	PRIMARY KEY (id)
); 

CREATE TABLE IF NOT EXISTS product_category (
	id int NOT NULL,
	name varchar(50) NOT NULL,
	PRIMARY KEY (id)
); 

CREATE TABLE IF NOT EXISTS category_news (
	id int NOT NULL AUTO_INCREMENT,
	total_results int NOT NULL,
	news_date date NOT NULL,
	update_at time NOT NULL,
	product_category_id int NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (product_category_id) REFERENCES product_category(id)
);

CREATE TABLE IF NOT EXISTS product (
	id int NOT NULL AUTO_INCREMENT,
	name varchar(50) NOT NULL,
	description varchar(100) NOT NULL,
	creation_date timestamp NOT NULL,
	product_category_id int NOT NULL,
	score DECIMAL(10,2),
	PRIMARY KEY (id),
	FOREIGN KEY (product_category_id) REFERENCES product_category(id)
);

CREATE TABLE IF NOT EXISTS sale (
	id int NOT NULL AUTO_INCREMENT,
	seller_id int NOT NULL,
	buyer_id int NOT NULL,
	product_id int NOT NULL,
	user_score int,
	sale_date timestamp NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (seller_id) REFERENCES seller(id),
	FOREIGN KEY (buyer_id) REFERENCES buyer(id),
	FOREIGN KEY (product_id) REFERENCES product(id)
);