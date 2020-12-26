SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'TRADITIONAL,ALLOW_INVALID_DATES';


CREATE DATABASE /*!32312 IF NOT EXISTS */`bank_transaction` /*!40100 DEFAULT CHARACTER SET utf8 */;

-- USE `trucker911_test`;
USE `bank_transaction`;

/*Table structure for table `ACCOUNT` */
DROP TABLE IF EXISTS `ACCOUNT`;

CREATE TABLE `ACCOUNT`
(
    `ID`           BIGINT(20)   NOT NULL AUTO_INCREMENT,
    `UUID`         VARCHAR(100) NOT NULL,
    `EMAIL`        VARCHAR(200) NOT NULL,
    `PASSWORD`     VARCHAR(200) NOT NULL,
    `FIRST_NAME`   VARCHAR(32)  NOT NULL,
    `LAST_NAME`    VARCHAR(32)  NOT NULL,
    `AVATAR`       VARCHAR(1000)          DEFAULT NULL,
    `GENDER`       ENUM ('MALE','FEMALE') DEFAULT NULL,
    `CREATED_DATE` DATETIME     NOT NULL,
    PRIMARY KEY (`ID`),
    UNIQUE KEY `UUID_UNIQUE` (`UUID`),
    UNIQUE KEY `EMAIL_UNIQUE` (`EMAIL`)
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;

/*Table structure for table `BANK_ACCOUNT` */
DROP TABLE IF EXISTS `BANK_ACCOUNT`;

CREATE TABLE `BANK_ACCOUNT`
(
    `ID`           BIGINT(20)   NOT NULL AUTO_INCREMENT,
    `ACCOUNT_ID`   BIGINT(20)   NOT NULL,
    `UUID`         VARCHAR(100) NOT NULL,
    `NUMBER`       VARCHAR(16)  NOT NULL,
    `AMOUNT`       DECIMAL(10, 2) NOT NULL,
    `CURRENCY`     ENUM ('EURO','USD', 'GBP') NOT NULL,
    `CREATED_DATE` DATETIME     NOT NULL,
    PRIMARY KEY (`ID`),
    UNIQUE KEY `UUID_UNIQUE` (`UUID`),
    UNIQUE KEY `NUMBER_UNIQUE` (`NUMBER`),
    KEY `fk_BANK_ACCOUNT_ACCOUNT_ID_idx` (`ACCOUNT_ID`),
    CONSTRAINT `fk_BANK_ACCOUNT_ACCOUNT_ID_idx` FOREIGN KEY (`ACCOUNT_ID`) REFERENCES `ACCOUNT` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;

/*Table structure for table `TRANSACTION` */
DROP TABLE IF EXISTS `TRANSACTION`;

CREATE TABLE `TRANSACTION`
(
    `ID`                   BIGINT(20)     NOT NULL AUTO_INCREMENT,
    `UUID`                 VARCHAR(100)   NOT NULL,
    `NUMBER`               VARCHAR(16)    NOT NULL,
    `FROM_BANK_ACCOUNT_ID` BIGINT(20)     NOT NULL,
    `TO_BANK_ACCOUNT_ID`   BIGINT(20)     NOT NULL,
    `AMOUNT`               DECIMAL(10, 2) NOT NULL,
    `TYPE`                 ENUM ('DEPOSIT', 'WITHDRAWAL', 'TRANSFER')           NOT NULL,
    `STATUS`               ENUM ('PENDING', 'ACCEPTED', 'REJECTED', 'CANCELED') NOT NULL,
    `CREATED_DATE`         DATETIME       NOT NULL,
    PRIMARY KEY (`ID`),
    UNIQUE KEY `UUID_UNIQUE` (`UUID`),
    KEY `fk_TRANSACTION_FROM_BANK_ACCOUNT_ID_idx` (`FROM_BANK_ACCOUNT_ID`),
    CONSTRAINT `fk_TRANSACTION_FROM_BANK_ACCOUNT_ID_idx` FOREIGN KEY (`FROM_BANK_ACCOUNT_ID`) REFERENCES `BANK_ACCOUNT` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
    KEY `fk_TRANSACTION_TO_BANK_ACCOUNT_ID_idx` (`TO_BANK_ACCOUNT_ID`),
    CONSTRAINT `fk_TRANSACTION_TO_BANK_ACCOUNT_ID_idx` FOREIGN KEY (`TO_BANK_ACCOUNT_ID`) REFERENCES `BANK_ACCOUNT` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;

/*Table structure for table `CURRENCY_RATE` */
DROP TABLE IF EXISTS `CURRENCY_RATE`;

CREATE TABLE `CURRENCY_RATE`
(
    `ID`           BIGINT(20)     NOT NULL AUTO_INCREMENT,
    `CURRENCY`     ENUM ('EURO', 'GBP') NOT NULL,
    `RATE`         DECIMAL(10, 2) NOT NULL,
    `CREATED_DATE` DATETIME       NOT NULL,
    PRIMARY KEY (`ID`)
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;

SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;