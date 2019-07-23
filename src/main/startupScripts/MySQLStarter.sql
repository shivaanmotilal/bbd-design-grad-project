ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'root';
FLUSH privileges;


CREATE DATABASE IF NOT EXISTS test; 
USE test;
SHOW TABLES;