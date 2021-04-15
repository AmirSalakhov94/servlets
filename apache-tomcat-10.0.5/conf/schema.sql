CREATE TABLE managers
(
    id         INTEGER PRIMARY KEY AUTO_INCREMENT,
    name       TEXT    NOT NULL,
    salary     INTEGER NOT NULL CHECK ( salary > 0 ),
    department TEXT,
    plan       INTEGER NOT NULL,
    boss_id    INTEGER REFERENCES managers
);

CREATE TABLE products
(
    id    INTEGER PRIMARY KEY AUTO_INCREMENT,
    name  TEXT    NOT NULL UNIQUE,
    price INTEGER NOT NULL CHECK ( price > 0 ),
    qty   INTEGER NOT NULL CHECK ( qty >= 0 ) DEFAULT 0
);

CREATE TABLE sales
(
    id         INTEGER PRIMARY KEY AUTO_INCREMENT,
    manager_id INTEGER NOT NULL REFERENCES managers
);

CREATE TABLE sale_details
(
    id         INTEGER PRIMARY KEY AUTO_INCREMENT,
    sale_id    INTEGER NOT NULL REFERENCES sales,
    product_id INTEGER NOT NULL REFERENCES products,
    name       TEXT    NOT NULL,
    price      INTEGER NOT NULL CHECK ( price >= 0 ),
    qty        INTEGER NOT NULL CHECK ( qty > 0 )
);
