-- Insert role(s)
insert into role (name) values ('ROLE_USER');
insert into role (name) values ('ROLE_ADMIN');

-- Insert three users, one admin, and two regular users
insert into user (username, enabled, password) values ('admin', true, 'admin');
insert into user (username, enabled, password) values ('user', true, 'password');
insert into user (username, enabled, password) values ('user2', true, 'password');

-- Assign roles to users, admin gets 1 and 2 (USER and ADMIN), user and user2 get 2 only (USER)
insert into user_role (user_id, role_id) values (1, 1);
insert into user_role (user_id, role_id) values (1, 2);
insert into user_role (user_id, role_id) values (2, 1);
insert into user_role (user_id, role_id) values (3, 1);

-- Insert flash cards
insert into flashcard (term, definition) values ('JDK', 'Java Development Kit');
insert into flashcard (term, definition) values ('YAGNI', 'You Ain''t Gonna Need It');
insert into flashcard (term, definition) values ('Java SE', 'Java Standard Edition');
insert into flashcard (term, definition) values ('Java EE', 'Java Enterprise Edition');
insert into flashcard (term, definition) values ('JRE', 'Java Runtime Environment');
insert into flashcard (term, definition) values ('JCL', 'Java Class Library');
insert into flashcard (term, definition) values ('JVM', 'Java Virtual Machine');
insert into flashcard (term, definition) values ('SDK', 'Software Development Kit');
