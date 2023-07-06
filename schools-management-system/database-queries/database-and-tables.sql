-- database management

drop database if exists school_management_system;
create database school_management_system;
use school_management_system;

-- tables management

drop table if exists profiles;
create table profiles
(
    id           bigint unique not null auto_increment,
    first_name   varchar(255),
    last_name    varchar(255),
    gender       enum ('MALE', 'FEMALE'),
    phone_number varchar(255),
    address      varchar(255),
    primary key (id)
);

drop table if exists users;
create table users
(
    id         bigint unique not null auto_increment,
    email      varchar(255),
    password   varchar(255),
    is_active  boolean,
    profile_id bigint,
    role       enum ('STUDENT', 'PARENT', 'TEACHER', 'HEAD_TEACHER', 'DIRECTOR'),
    primary key (id),
    foreign key (profile_id) references profiles (id)
);

drop table if exists courses;
create table courses
(
    id          bigint unique not null auto_increment,
    course_name varchar(255),
    primary key (id)
);

drop table if exists grades;
create table grades
(
    id           bigint unique not null auto_increment,
    year_from    int           not null,
    year_to      int           not null,
    grade_number int           not null,
    subgroup     varchar(1),
    primary key (id)
);

drop table if exists marks;
create table marks
(
    id         bigint unique not null auto_increment,
    student_id bigint,
    course_id  bigint,
    teacher_id bigint,
    mark       enum ('ABSENT', 'ONE', 'TWO', 'THREE', 'FOUR', 'FIVE', 'SIX', 'SEVEN', 'EIGHT', 'NINE', 'TEN'),
    created_at timestamp,
    primary key (id),
    foreign key (student_id) references users (id),
    foreign key (course_id) references users (id),
    foreign key (teacher_id) references users (id)
);

drop table if exists parents_students;
create table parents_students
(
    parent_id  bigint,
    student_id bigint,
    foreign key (parent_id) references users (id),
    foreign key (student_id) references users (id)
);


drop table if exists students_groups;
create table students_groups
(
    student_id bigint,
    grade_id   bigint,
    foreign key (student_id) references users (id),
    foreign key (grade_id) references grades (id)
);


drop table if exists users_courses_grades;
create table users_courses_grades
(
    course_id bigint,
    user_id   bigint,
    grade_id  bigint,
    foreign key (course_id) references courses (id),
    foreign key (user_id) references users (id),
    foreign key (grade_id) references grades (id)
);








