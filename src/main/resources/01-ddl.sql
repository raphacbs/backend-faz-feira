CREATE TABLE tb_user (
  id UUID NOT NULL,
   email VARCHAR(255) NOT NULL,
   password VARCHAR(255) NOT NULL,
   role VARCHAR(255) NOT NULL,
   created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   first_name VARCHAR(255) NOT NULL,
   last_name VARCHAR(255) NOT NULL,
   is_active BOOLEAN NOT NULL,
   CONSTRAINT pk_tb_user PRIMARY KEY (id)
);

ALTER TABLE tb_user ADD CONSTRAINT uc_tb_user_email UNIQUE (email);

CREATE TABLE unit (
  id UUID NOT NULL,
   description VARCHAR(255) NOT NULL,
   initials VARCHAR(255) NOT NULL,
   CONSTRAINT pk_unit PRIMARY KEY (id)
);

ALTER TABLE unit ADD CONSTRAINT uc_unit_description UNIQUE (description);

ALTER TABLE unit ADD CONSTRAINT uc_unit_initials UNIQUE (initials);

CREATE TABLE supermarket (
  id UUID NOT NULL,
   name VARCHAR(255),
   country VARCHAR(255),
   region VARCHAR(255),
   state VARCHAR(255),
   state_code VARCHAR(255),
   city VARCHAR(255),
   municipality VARCHAR(255),
   postcode VARCHAR(255),
   district VARCHAR(255),
   neighbourhood VARCHAR(255),
   suburb VARCHAR(255),
   street VARCHAR(255),
   longitude DOUBLE PRECISION NOT NULL,
   latitude DOUBLE PRECISION NOT NULL,
   address VARCHAR(255),
   place_id VARCHAR(255),
   CONSTRAINT pk_supermarket PRIMARY KEY (id)
);

CREATE TABLE shopping_list (
  id UUID NOT NULL,
   description VARCHAR(255) NOT NULL,
   supermarket_id UUID,
   created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   status VARCHAR(255),
   user_id UUID,
   CONSTRAINT pk_shopping_list PRIMARY KEY (id)
);

ALTER TABLE shopping_list ADD CONSTRAINT FK_SHOPPING_LIST_ON_SUPERMARKET FOREIGN KEY (supermarket_id) REFERENCES supermarket (id);

ALTER TABLE shopping_list ADD CONSTRAINT FK_SHOPPING_LIST_ON_USER FOREIGN KEY (user_id) REFERENCES tb_user (id);

CREATE TABLE product (
  code VARCHAR(255) NOT NULL,
   description VARCHAR(255) NOT NULL,
   brand VARCHAR(255),
   thumbnail VARCHAR(255),
   created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   unit_id UUID,
   CONSTRAINT pk_product PRIMARY KEY (code)
);

ALTER TABLE product ADD CONSTRAINT FK_PRODUCT_ON_UNIT FOREIGN KEY (unit_id) REFERENCES unit (id);

CREATE TABLE price_history (
  id UUID NOT NULL,
   price DOUBLE PRECISION NOT NULL,
   product_code VARCHAR(255),
   supermarket_id UUID,
   created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   shopping_list_id UUID,
   item_id UUID,
   CONSTRAINT pk_price_history PRIMARY KEY (id)
);


ALTER TABLE price_history ADD CONSTRAINT FK_PRICE_HISTORY_ON_PRODUCT_CODE FOREIGN KEY (product_code) REFERENCES product (code);

ALTER TABLE price_history ADD CONSTRAINT FK_PRICE_HISTORY_ON_SHOPPING_LIST FOREIGN KEY (shopping_list_id) REFERENCES shopping_list (id);

ALTER TABLE price_history ADD CONSTRAINT FK_PRICE_HISTORY_ON_SUPERMARKET FOREIGN KEY (supermarket_id) REFERENCES supermarket (id);

CREATE TABLE item (
  id UUID NOT NULL,
   note VARCHAR(255),
   quantity INTEGER NOT NULL,
   price DOUBLE PRECISION NOT NULL,
   per_unit DOUBLE PRECISION NOT NULL,
   is_added BOOLEAN NOT NULL,
   product_code VARCHAR(255),
   shopping_list_id UUID,
   created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   CONSTRAINT pk_item PRIMARY KEY (id)
);

ALTER TABLE item ADD CONSTRAINT FK_ITEM_ON_PRODUCT_CODE FOREIGN KEY (product_code) REFERENCES product (code);

ALTER TABLE item ADD CONSTRAINT FK_ITEM_ON_SHOPPING_LIST FOREIGN KEY (shopping_list_id) REFERENCES shopping_list (id);

ALTER TABLE price_history ADD CONSTRAINT FK_PRICE_HISTORY_ON_ITEM FOREIGN KEY (item_id) REFERENCES item (id);
