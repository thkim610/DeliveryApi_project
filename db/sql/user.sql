-- 회원 테이블 생성
CREATE TABLE IF NOT EXISTS `user` (
                                      `id` BIGINT(32) NOT NULL AUTO_INCREMENT,
                                      `name` VARCHAR(50) NOT NULL,
                                      `email` VARCHAR(100) NOT NULL,
                                      `password` VARCHAR(100) NOT NULL,
                                      `status` VARCHAR(50) NOT NULL,
                                      `address` VARCHAR(150) NOT NULL,
                                      `registered_at` DATETIME NULL,
                                      `unregistered_at` DATETIME NULL,
                                      `last_login_at` DATETIME NULL,
                                      PRIMARY KEY (`id`))
    ENGINE = InnoDB;