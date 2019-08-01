CREATE DEFINER=`root`@`localhost` PROCEDURE `GetInternationalBalance`(in country_code char (3), in account_num varchar(255))
BEGIN
		select currency_convertor(available_balance, country_code) from account
        where account_number = account_num;
        
END