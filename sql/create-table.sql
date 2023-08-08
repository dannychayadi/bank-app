-- create table

create table members (
	id int auto_increment primary key,
	username varchar(255),
	password varchar(70),
	first_name varchar(255),
	last_name varchar(255),
	email varchar(255),
	created_date datetime
);

create table roles (
    id int auto_increment primary key,
    member_id int not null,
	role varchar(255)
);
create table accounts (
	id int auto_increment primary key,
	member_id int not null,
	account_number varchar(30),
	balance varchar(255),
	created_date datetime
);

create table transaction_history (
    id int auto_increment primary key,
    member_id int not null,
    amount varchar(255),
    recipient_account_number varchar(30),
    description varchar(255) not null,
    created_date datetime
);