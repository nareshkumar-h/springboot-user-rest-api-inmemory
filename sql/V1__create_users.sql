
drop table taskapp_users;
create table taskapp_users(
id number not null,
name varchar2(100) not null,
email varchar2(100) not null,
password varchar2(100) not null,
role varchar(10)  default 'USER',
active number default 1,
unique(email),
check ( role in ('USER','ADMIN'))
);

create sequence taskapp_users_id_seq start with 1 increment by 1;

insert into taskapp_users(id,name,email,password) values ( taskapp_users_id_seq.nextval, 'Naresh','n@gmail.com','pass123');

commit;
delete from taskapp_users;

select * from taskapp_users;