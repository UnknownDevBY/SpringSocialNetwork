create table hashtag(
  id int primary key auto_increment,
  content varchar(63) not null,
  post_id int,
  comment_id int,
  foreign key (post_id) references post(id),
  foreign key (comment_id) references comment(id)
);