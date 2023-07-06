create database task_management;
use task_management;

create table Users (
  UserName VARCHAR(30),
  FirstName VARCHAR(30),
  LastName VARCHAR(30)
);

create table Tasks(
  Title tinytext,
  Describtion mediumtext,
  performerUserName VARCHAR(30)
);

