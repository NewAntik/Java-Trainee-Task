CREATE TABLE users(
    id BIGINT NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(20) NOT NULL,
    last_name VARCHAR(20) NOT NULL,
    email VARCHAR(200) NOT NULL,
    birthday TIMESTAMP NOT NULL,
    address VARCHAR(100),
    phone VARCHAR(13),
    PRIMARY KEY (id)
);