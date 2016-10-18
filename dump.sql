CREATE DATABASE `jsock` CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE `jsock`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) NULL,
  `password` VARCHAR(255) NULL,
  `rights` VARCHAR(255) NULL,
  `create_time` INT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `jsock`.`session` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(45) NULL,
  `token` VARCHAR(45) NULL,
  `time` INT NULL,
  `ip` VARCHAR(128),
  PRIMARY KEY (`id`));
INSERT INTO `jsock`.`users` (`email`, `password`, `rights`, `create_time`) VALUES ('', 'jetananas@yandex.ru', 'test', 'admin', '123123');

