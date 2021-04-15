INSERT INTO managers(id, name, plan, salary, department, boss_id)
VALUES
(1, 'Vasya', 0, 200000, NULL, NULL),
(2, 'Petya', 300000, 150000, 'bar', 1),
(3, 'Vanya', 200000, 100000, 'bar', 2),
(4, 'Dasha', 300000, 150000, 'rest', 1),
(5, 'Sasha', 300000, 130000, 'rest', 4),
(6, 'Masha', 300000, 100000, 'rest', 5);

INSERT INTO products(id, name, price, qty)
VALUES
(1, 'Big Mac', 200, 10),
(2, 'Chicken Mac', 150, 5),
(3, 'Burger', 100, 2),
(4, 'Tea', 50, 10),
(5, 'Cola', 50, 5);

INSERT INTO sales(id, manager_id)
VALUES
-- Vasya - big mac 10, 200
(1, 1),
-- Petya - chicken mac 10, 125
(2, 2),
-- Vanya - burger, 10, 90
(3, 3),
-- Dasha - big mac, 10, 210
-- Dasha - cola, 10, 55
(4, 4),
-- Dasha - tea, 10, 55
(5, 4),
-- Sasha - tea, 10, 50
(6, 5);


INSERT INTO sale_details(sale_id, id, product_id, name, price, qty)
VALUES
-- Vasya - 1 big mac 10, 200
(1, 1, 1, 'Big Mac', 200, 10),
-- Petya - 2 chicken mac 10, 125
(2, 2, 2, 'Chicken Mac', 125, 10),
-- Vanya - 3 burger, 10, 90
(3, 3, 3, 'Burger', 90, 10),
-- Dasha - 1 big mac, 10, 210
-- Dasha - 5 cola, 10, 55
(4, 4, 1, 'Big Mac', 210, 10),
(4, 5, 5, 'Cola', 55, 10),
-- Dasha - 4 tea, 10, 55
(5, 6, 4, 'Tea', 55, 10),
-- Sasha - 4 tea, 10, 50
(6, 7, 4, 'Tea', 50, 10);


