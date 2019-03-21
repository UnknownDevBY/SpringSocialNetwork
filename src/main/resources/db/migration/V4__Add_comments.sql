create table comment(
	id int primary key auto_increment,
	  content varchar(255) not null,
	  user_id int,
    photo_id int,
    post_id int,
    foreign key (user_id) references user(id),
    foreign key (photo_id) references photo(id),
    foreign key (post_id) references post(id)
);