CREATE SCHEMA `storage` DEFAULT CHARACTER SET utf8 ;
CREATE TABLE `storage`.`manufacturers`
(
  id              INT unsigned NOT NULL AUTO_INCREMENT,
  name            VARCHAR(150) NOT NULL,
  country         VARCHAR(150) NOT NULL,
  deleted         bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY     (id)
);
INSERT INTO `storage`.`manufacturers` ( name, country) VALUES
  ( 'Audi', 'GERMANY' ),
  ( 'Tesla', 'USA'),
  ( 'Chevrolet', 'USA');
