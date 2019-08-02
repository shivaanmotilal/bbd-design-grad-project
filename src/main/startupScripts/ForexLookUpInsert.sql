drop table if exists forex_look_up;
create table forex_look_up( country_id int not null auto_increment,
							country_code char(3),
                            exchange_rate double,
                            primary key ( country_id));

insert into forex_look_up (country_code, exchange_rate) values ("bwp", 0.732232);
insert into forex_look_up (country_code, exchange_rate) values ("GBP", 0.056199);
insert into forex_look_up (country_code, exchange_rate) values ("EUR", 0.061374);
insert into forex_look_up (country_code, exchange_rate) values ("USD", 0.068102);
insert into forex_look_up (country_code, exchange_rate) values ("AUD", 0.100323);