CREATE TABLE `device` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) DEFAULT NULL,
    `type` VARCHAR(50) DEFAULT NULL,
    `location` VARCHAR(255) DEFAULT NULL,
    `user_id` BIGINT DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `idx_device_user_id` (`user_id`),
    CONSTRAINT `fk_device_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
