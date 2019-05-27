ALTER TABLE `task`
CHANGE COLUMN `id` `id` VARCHAR(255) NOT NULL ,
ADD COLUMN `state` VARCHAR(45) NULL AFTER `visit_date`,
ADD COLUMN `tag` VARCHAR(45) NULL AFTER `state`,
ADD COLUMN `date_time_from` DATETIME NULL AFTER `tag`,
ADD COLUMN `date_time_to` DATETIME NULL AFTER `date_time_from`,
ADD COLUMN `whole_day` BIT(1) NULL AFTER `date_time_to`,
ADD COLUMN `allocated` BIT(1) NULL AFTER `whole_day`,
ADD UNIQUE INDEX `id_UNIQUE` (`id` ASC);
