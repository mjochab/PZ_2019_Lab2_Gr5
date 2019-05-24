ALTER TABLE `notification`
ADD COLUMN `user_id` INT(11) NULL AFTER `viewed`,
ADD INDEX `fk_notification_1_idx` (`user_id` ASC);
ALTER TABLE `notification`
ADD CONSTRAINT `fk_notification_1`
  FOREIGN KEY (`user_id`)
  REFERENCES `user` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;
