insert into user (id, first_name, last_name, email, password, company_name, id_role)
values (1, 'Patryk', 'Kleczkowski', 'admin@o2.pl', 'lol123', 'Dajszmal SA', 1);


insert into client(id, first_name, last_name, phone_number, street, house_number, apartment_number, city)
values (1, 'Janusz', 'Tracz', 788345612,'Sezamkowa', 20, 67, 'Orzeszkowo');

insert into client(id, first_name, last_name, phone_number, street, house_number, apartment_number, city)
values (2, 'John', 'Snow', 783454442,'Jakastam', 1, 1, 'Winterfell');

insert into client(id, first_name, last_name, phone_number, street, house_number, apartment_number, city)
values (3, 'Adam', 'Krupa', 1234567687,'ruhapsajaksra', 2, 50, 'Rzeszow');


insert into task(id, author_id, team_leader_id, title, details, client_id, status, creation_date, visit_date)
values (1, 1, null, 'Montaż', 'Montaż światłowodu u pana Janusza hehe', 1, NULL, '2019-01-16', '2019-05-5');

insert into task(id, author_id, team_leader_id, title, details, client_id, status, creation_date, visit_date)
values (2, 1, null, 'Awaria', 'Cos sie zpesulo i nie dziala', 2, NULL, '2019-04-28', '2019-04-29');