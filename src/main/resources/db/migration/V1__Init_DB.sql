create table friendship (
  id integer primary key auto_increment,
  is_confirmed bit not null,
  from_id integer not null,
  to_id integer not null
);

create table message (
  id integer primary key auto_increment,
  content varchar(511) not null,
  sending_time varchar(255) not null,
  from_id integer not null,
  to_id integer not null
);

create table photo (
  id integer primary key auto_increment,
  date_of_post varchar(255),
  img longblob not null,
  is_avatar bit not null,
  was_avatar bit not null,
  user_id integer
);

create table post (
  id integer primary key auto_increment,
  content varchar(511) not null,
  post_time varchar(255) not null,
  author_id integer not null,
  owner_id integer not null
);

create table privacy_settings (
  id integer primary key auto_increment,
  friends char(1) default 'a',
  full_info char(1) default 'a',
  messages char(1) default 'a',
  photos char(1) default 'a',
  post_authors char(1) default 'a',
  user_id integer
);

create table user (
  id integer primary key auto_increment,
  bio varchar(255),
  city varchar(255),
  date_of_birth varchar(255),
  email varchar(255) not null,
  interests varchar(255),
  name varchar(255),
  password varchar(255) not null,
  sex char(1) not null,
  status varchar(255),
  surname varchar(255)
);

alter table friendship
  add constraint friendship_user_fk1
  foreign key (from_id) references user (id);

alter table friendship
  add constraint friendship_user_fk2
  foreign key (to_id) references user (id);

alter table message
  add constraint message_user_fk1
  foreign key (from_id) references user (id);

alter table message
  add constraint message_user_fk2
  foreign key (to_id) references user (id);

alter table photo
  add constraint photo_user_fk
  foreign key (user_id) references user (id);

alter table post
  add constraint post_user_fk1
  foreign key (author_id) references user (id);

alter table post
  add constraint post_user_fk2
  foreign key (owner_id) references user (id);

alter table privacy_settings
  add constraint privacy_settings_user_fk
  foreign key (user_id) references user (id);