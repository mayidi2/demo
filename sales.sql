create database superstore;
use superstore;
create table productdetails(
  prod_id int primary key,
  prod_name varchar(50) not null,
  price int not null check(price>0)
);
insert into productdetails
values
(101, 'Laptop',10000),
(102, 'Microphone', 200),
(103, 'HDMI Cable', 50),
(104, 'PS5', 3000),
(105, 'Printer', 1000);

create table invoice(
  inv_num int,
  prod_id int,
  quantity int not null check(quantity>0),
  
  primary key(inv_num, prod_id),
  
  foreign key(prod_id) references productdetails(prod_id)
	on update cascade
    on delete restrict
);

insert into invoice values (5002, 103, 1);

create view Sales as
select i.inv_num, i.prod_id, p.prod_name,
	i.quantity, p.price, (i.quantity * p.price) as amount
from invoice i join productdetails p
on i.prod_id = p.prod_id;

select * from Sales;

