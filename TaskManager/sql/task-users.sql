create table users (
	user_id int not null auto_increment,
    firstName varchar(20) NOT NULL,
    lastName varchar(20) NOT NULL,
    userName varchar(20) NOT NULL UNIQUE,
    primary key(user_id)
);

create table tasks (
	id int NOT NULL auto_increment,
    user_id int NOT NULL,
    title tinytext NOT NULL,
    description mediumtext NOT NULL,
    primary key(id),
    KEY user_id(user_id),
    constraint users_ibfk_1 foreign key(user_id)
    references users(user_id)
);