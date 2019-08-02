CREATE DEFINER=`root`@`localhost` FUNCTION `currency_convertor`(available_balance double, country_code char(3)) RETURNS int(11)
    DETERMINISTIC
BEGIN   
RETURN  (select exchange_rate*available_balance from forex_look_up where country_code = country_code limit 1);
END