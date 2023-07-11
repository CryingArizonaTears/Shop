use
shop;

CREATE TABLE warehouse
(
    id      BIGINT      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    address VARCHAR(50) NOT NULL

);

CREATE TABLE product
(
    id           BIGINT      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name         VARCHAR(50) NOT NULL,
    type         enum('FOOD', 'NON_FOOD') NOT NULL,
    price        DECIMAL     NOT NULL,
    exp_date     INT,
    warehouse_id BIGINT      NOT NULL
);


ALTER TABLE product
    ADD FOREIGN KEY (warehouse_id)
        REFERENCES warehouse (id)
        ON DELETE CASCADE;


CREATE TABLE currency
(
    id         BIGINT      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name       VARCHAR(50) NOT NULL UNIQUE,
    multiplier DECIMAL     NOT NULL
);

CREATE TABLE bucket
(
    id          BIGINT  NOT NULL PRIMARY KEY AUTO_INCREMENT,
    total_price DECIMAL NOT NULL
);

CREATE TABLE user_profile
(
    id                  BIGINT      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    user_credentials_id BIGINT      NOT NULL UNIQUE,
    role_id             BIGINT      NOT NULL,
    bucket_id           BIGINT      NOT NULL UNIQUE,
    full_name           VARCHAR(50) NOT NULL,
    address             VARCHAR(50),
    email               VARCHAR(50),
    phone               VARCHAR(50),
    CONSTRAINT follower_fk
        FOREIGN KEY (bucket_id)
            REFERENCES bucket (id) ON DELETE CASCADE
);

CREATE TABLE user_credentials
(
    id       BIGINT       NOT NULL PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50)  NOT NULL UNIQUE,
    password VARCHAR(512) NOT NULL
);

CREATE TABLE role
(
    id   BIGINT      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE
);


ALTER TABLE user_profile
    ADD FOREIGN KEY (user_credentials_id)
        REFERENCES user_credentials (id)
        ON DELETE CASCADE;

ALTER TABLE user_profile
    ADD FOREIGN KEY (role_id)
        REFERENCES role (id)
        ON DELETE CASCADE;

ALTER TABLE user_profile
    ADD FOREIGN KEY (bucket_id)
        REFERENCES bucket (id)
        ON DELETE CASCADE;

CREATE TABLE bucket_product
(
    product_id BIGINT NOT NULL,
    bucket_id  BIGINT NOT NULL,
    CONSTRAINT fk_bucket_product_product
        FOREIGN KEY (product_id) REFERENCES product (id),
    CONSTRAINT fk_bucket_product_bucket
        FOREIGN KEY (bucket_id) REFERENCES bucket (id)
);

CREATE TABLE order_
(
    id          BIGINT  NOT NULL PRIMARY KEY AUTO_INCREMENT,
    user_id     BIGINT  NOT NULL,
    date        DATE    NOT NULL,
    currency_id BIGINT  NOT NULL,
    processed   BOOLEAN NOT NULL,
    total_price DECIMAL NOT NULL
);

ALTER TABLE order_
    ADD FOREIGN KEY (user_id)
        REFERENCES user_profile (id)
        ON DELETE CASCADE;

ALTER TABLE order_
    ADD FOREIGN KEY (currency_id)
        REFERENCES currency (id)
        ON DELETE CASCADE;


CREATE TABLE product_order
(
    product_id BIGINT NOT NULL,
    order_id   BIGINT NOT NULL,
    CONSTRAINT fk_product_order_product
        FOREIGN KEY (product_id) REFERENCES product (id),
    CONSTRAINT fk_product_order_order
        FOREIGN KEY (order_id) REFERENCES order_ (id)
);