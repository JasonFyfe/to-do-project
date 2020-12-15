drop table if exists `NOTE` CASCADE;
drop table if exists `TODO` CASCADE;
 
create table if not exists NOTE (ID bigint PRIMARY KEY AUTO_INCREMENT, TITLE varchar(255) not null);
create table if not exists TODO(ID bigint PRIMARY KEY AUTO_INCREMENT, BODY varchar(255) not null, NOTE_ID bigint);