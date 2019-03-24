create table photo_album(
  id int primary key auto_increment,
  title varchar(63) not null,
  user_id int,
  foreign key (user_id) references user (id)
);

alter table photo
  add album_id int;

alter table photo
  add constraint photo_album_fk
  foreign key (album_id) references photo_album (id);