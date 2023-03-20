CREATE SEQUENCE product_id_seq;
CREATE TABLE "product"
(
    "id"            BIGINT PRIMARY KEY DEFAULT nextval('product_id_seq'),
    "name"          VARCHAR(20) NOT NULL,
    "description"   VARCHAR(20) NOT NULL
);