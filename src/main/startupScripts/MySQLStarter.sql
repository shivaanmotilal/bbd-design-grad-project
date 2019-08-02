ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'root';
FLUSH privileges;

DROP DATABASE test_wallet;
CREATE DATABASE IF NOT EXISTS test_wallet;
USE test_wallet;
SHOW TABLES;