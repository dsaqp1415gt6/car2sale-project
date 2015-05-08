drop user 'car2sale'@'localhost';
create user 'car2sale'@'localhost' identified by 'car2sale'; 
grant all privileges on car2saledb.* to 'car2sale'@'localhost';
flush privileges;