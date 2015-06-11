drop database if exists car2saledb;
create database car2saledb;
 
use car2saledb;
 
create table users (
	imagen		varchar(50) not null,
	username	varchar(20) not null primary key,
	password	char(32) not null,
	name		varchar(30) not null,
	email		varchar(30) not null
	
);
 

create table user_roles (
	username			varchar(20) not null,
	rolename 			varchar(20) not null,
	foreign key(username) references users(username) on delete cascade,
	primary key (username, rolename)
);
 
create table anuncio (
	idanuncio 			int not null auto_increment primary key,
	imagen		    	varchar(50) not null,
	cont 				int not null,
	username			varchar(20) not null,
	titulo 				varchar(100) not null,
	descripcion			varchar(2000) not null,
	marca				varchar(20) not null,
	modelo				varchar(40) not null,
	km					int not null,
	precio				int not null,
	provincia			varchar(20) not null,
	last_modified			timestamp default current_timestamp ON UPDATE CURRENT_TIMESTAMP,
	creation_timestamp		datetime not null default current_timestamp,
	
	foreign key(username) references users(username)
);

create table favoritos(
username 	varchar(20) not null,
idanuncio	int not null,
last_modified			timestamp default current_timestamp ON UPDATE CURRENT_TIMESTAMP,
creation_timestamp		datetime not null default current_timestamp,
primary key (username, idanuncio),
foreign key(username) references users(username),
foreign key(idanuncio) references anuncio(idanuncio) on delete cascade

);

create table mensajes(
idmensaje int not null auto_increment primary key,
usuarioenvia varchar(20) not null,
usuariorecibe varchar(20) not null,
anuncio int not null,
mensaje varchar(500) not null,
last_modified			timestamp default current_timestamp ON UPDATE CURRENT_TIMESTAMP,
creation_timestamp		datetime not null default current_timestamp,
foreign key(anuncio) references anuncio(idanuncio) on delete cascade
);