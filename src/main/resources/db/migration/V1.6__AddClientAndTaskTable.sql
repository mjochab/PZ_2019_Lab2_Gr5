CREATE TABLE `client` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `phone_number` int(11) NOT NULL,
  `street` varchar(255) NOT NULL,
  `house_number` int(6) NOT NULL,
  `apartment_number` int(6) DEFAULT NULL,
  `city` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `task` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `leader_id` int(11) DEFAULT NULL,
  `tag` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `client_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`client_id`) references `client`(`id`) on delete no action on update cascade,
  FOREIGN KEY (`leader_id`) references `user`(`id`) on delete no action on update cascade
);