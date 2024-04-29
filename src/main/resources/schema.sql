create table if not exists coupon(
    id int primary key,
    product_name varchar(50),
    description varchar(50),
    amount int
);
