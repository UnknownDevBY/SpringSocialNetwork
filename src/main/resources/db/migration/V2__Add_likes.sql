create table likes(
	id int primary key auto_increment,
    user_id int,
    post_id int,
    photo_id int,
    foreign key (user_id) references user(id),
	  foreign key (post_id) references post(id),
    foreign key (photo_id) references photo(id)
);