CREATE DEFINER=`root`@`localhost` PROCEDURE `GetTransactionHistory`(in account_number varchar(255))
BEGIN
   select p.amount*-1 as amount, p.date_initiation, c.first_name, c.surname, p.to_account_number as Account_Number 
   from 
		payment as p,
        customer_accounts as ca,
        customer as c
   where p.from_account_number = account_number and p.to_account_number = ca.accounts_account_number and c.customer_id = ca.customer_entity_customer_id
   
   union
   
   select p.amount, p.date_initiation, c.first_name, c.surname, p.from_account_number as Account_Number
   from 
		payment as p,
        customer_accounts as ca,
        customer as c
   where p.to_account_number = account_number and p.from_account_number = ca.accounts_account_number and c.customer_id = ca.customer_entity_customer_id;
END
