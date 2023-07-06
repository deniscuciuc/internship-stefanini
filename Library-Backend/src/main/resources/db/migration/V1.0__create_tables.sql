

create table profile (
  id int not null
  primary key
  auto_increment,
  firstName varchar(255) null,
  lastName varchar(255) null,
  phoneNumber varchar(255) null
);


create table user (
  id int not null
  primary key
  auto_increment,
  created time not null,
  email varchar(255) null
  unique key,
  hasTemporaryPassword tinyint default 0,
  isConfirmedByEmail tinyint default 0,
  last_update datetime(6) null,
  password varchar(255) null,
  profile_id int null,
  foreign key  (profile_id) references  profile (id)
);

create table book (
  id int not null
  primary key
  auto_increment,
  createdOn datetime(6)  null,
  description varchar(255)  null,
  shelfNumber varchar(255)  null,
  bookStatus varchar(255)  null,
  title varchar(255)  null,
  user_id int null,
  foreign key  (user_id) references  user (id)
);

create table author (
  id int not null
  primary key
  auto_increment,
  biography varchar(1000) null,
  birthDate datetime(6) null,
  firstName varchar(255) null,
  lastName varchar(255) null
);

create table book_author (
  book_id int not null,
  author_id int not null,
  foreign key  (author_id) references  author (id),
  foreign key (book_id) references  book (id)
);

create table category (
  id int not null
  auto_increment
  primary key,
  title varchar(255) null
  unique key
);

create table book_category (
  book_id int null,
  category_id int null,
  foreign key (category_id) references  category (id),
  foreign key  (book_id) references  book (id)
);

create table history (
  id int not null
  primary key
  auto_increment,
  actionName varchar(255) null,
  date datetime(6) null,
  book_id int null,
  user_id int null,
  foreign key  (user_id) references  user (id),
  foreign key  (book_id) references  book (id)
);


create table user_role (
  user_id int not null,
  roles varchar(255) not null,
  foreign key  (user_id) references  user (id)
);

create table confirmation_token (
  id int not null
  auto_increment
  primary key,
  confirmedAt datetime(6)  null,
  createdAt datetime(6)  null,
  expiresAt datetime(6)  null,
  status varchar(255) null,
  token varchar(255)  null,
  user_id int not null,
  foreign key (user_id) references  user (id)
);
INSERT INTO profile (firstName, lastName, phoneNumber)
VALUES ('John', 'Smith', '+3736901254'),
       ('Charlotte', 'Brown', '+3736912455');

INSERT INTO user (created, email, isConfirmedByEmail, last_update, password, profile_id)
VALUES (NOW(), 'admin@gmail.com', 1,  NOW(), '$2a$12$d0xSkVIXVYUiRB8tYvHhVuNNnaDHknkjO453z6yeQDRPcnB5CKE72', 1),
       (NOW(), 'librarian@gmail.com', 1, NOW(), '$2a$12$B3oQ6bIBSCTnYd98uQfaAe3f1HGXGvJjYzCc0Sa7Q1TC9w4Dm8zbS', 2);


INSERT INTO user_role VALUES (1, 'USER'),
                             (1, 'ADMIN'),
                             (2, 'USER'),
                             (2, 'LIBRARIAN');

INSERT INTO book (createdOn, description, shelfNumber, bookStatus, title)
VALUES  (NOW(), 'Murder on the Orient Express is a work of detective fiction by English writer Agatha Christie featuring the Belgian detective Hercule Poirot.',
             "00001", 'AVAILABLE', 'Murder on the Orient Express'),
        (NOW(), 'Death on the Nile is a work of detective fiction by English writer Agatha Christie published in the UK by the Collins Crime Club on 1 November 1937.',
             "00002", 'AVAILABLE', 'Death on the Nile'),
        (NOW(), 'Moby Dick is an 1851 novel by American writer Herman Melville.',
             "00003", 'AVAILABLE', 'Moby Dick') ,
        (NOW(), 'A delight for readers of Whered You Go, Bernadette,this book features the singular voice of Elizabeth Zott, a scientist whose career takes a detour when she becomes the star of a beloved TV cooking show.',
             "00004", 'AVAILABLE', 'Lessons in Chemistry'),

        (NOW(), 'After her mother dies, Katie decides to take the trip to Italy that they were suppressed to take together. The relationship between mothers and daughters is so complicated and this book reflects that.',
             "00005", 'AVAILABLE', 'One Italian Summer: A Novel'),

        (NOW(), 'From Colleen Hoover, the #1 New York Times bestselling author of It Ends with Us, aheart-wrenching love story that proves attraction at first sight can be messy.',
             "00006", 'AVAILABLE', 'Ugly Love');

INSERT INTO author (biography, birthDate, firstName, lastName)
VALUES   ('Agatha Mary Clarissa Miller was born on 15 September 1890, into a wealthy upper middle class family in Torquay, Devon. She was the youngest of three children born to Frederick Alvah Miller, "a gentleman of substance", and his wife Clarissa Margaret "Clara" Miller, née Boehmer.',
             "1890-09-15", 'Agatha', 'Christie'),

         ('Herman Melville was an American novelist, short story writer, and poet of the American Renaissance period. Among his best-known works are Moby-Dick (1851); Typee (1846), a romanticized account of his experiences in Polynesia; and Billy Budd, Sailor, a posthumously published novella. Although his reputation was not high at the time of his death, the 1919 centennial of his birth was the starting point of a Melville revival, and Moby-Dick grew to be considered one of the great American novels.',
             "1819-10-1", 'Herman', 'Melville'),

         ('Bonnie Garmus is a copywriter and creative director who has worked for a wide range of clients, in the US and abroad, focusing primarily on technology, medicine, and education. ',
            "1957-06-20", 'Bonnie', 'Garmus'),

         (' Rebecca Serle Rebecca Serle is an American author and television writer. Her novel In Five Years was a New York Times best seller, and her Famous in Love series was adapted into a young adult television series on Freeform. ',
            "1984-02-01", ' Rebecca', 'Serle');

INSERT INTO book_author VALUES (1, 1),
                               (2, 1),
                               (3, 2),
                               (4, 3),
                               (5, 4),
                               (6, 4);

insert into category (title) values ('Detective'),
                                    ('Crime fiction'),
                                    ('Adventure fiction'),
                                    ('Historical fiction'),
                                    ('Contemporary romance');

insert into book_category values (1, 1), (2, 2), (3, 3),(4, 4),(5, 5),(6, 5);