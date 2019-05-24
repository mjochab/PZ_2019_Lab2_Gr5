CREATE TABLE `notification` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `creation_date` DATETIME NOT NULL,
  `title` VARCHAR(255) NOT NULL,
  `description` VARCHAR(255) NULL,
  `viewed` BIT(1) NOT NULL,
  PRIMARY KEY (`id`));
