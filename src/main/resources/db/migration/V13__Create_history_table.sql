create table history
(
	id bigint auto_increment,
	user_id bigint not null,
	question_id bigint not null,
	title varchar(50) not null,
	when bigint not null
);