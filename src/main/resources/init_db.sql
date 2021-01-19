CREATE SCHEMA `storage` DEFAULT CHARACTER SET utf8 ;
CREATE TABLE `storage`.`manufacturers`
(
  id              INT unsigned NOT NULL AUTO_INCREMENT,
  name            VARCHAR(150) NOT NULL,
  country         VARCHAR(150) NOT NULL,
  deleted         bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY     (id)
);

CREATE TABLE `storage`.`drivers`
(
  id              INT unsigned NOT NULL AUTO_INCREMENT,
  name            VARCHAR(150) NOT NULL,
  licence_number  VARCHAR(150) NOT NULL,
  deleted         bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY     (id)
);

  CREATE TABLE `storage`.`cars`
(
  id              INT unsigned NOT NULL AUTO_INCREMENT,
  model           VARCHAR(150) NOT NULL,
  manufacturer_id INT unsigned,
  deleted         bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY     (id)
  FOREIGN KEY (manufacturer_id) REFERENCES manufacturers(id)
);

  CREATE TABLE `storage`.`cars_drivers`
(
  id              INT unsigned NOT NULL AUTO_INCREMENT,
  car_id          INT unsigned,
  driver_id       INT unsigned,
  deleted         bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY     (id)
  FOREIGN KEY (car_id) REFERENCES cars(id)
  FOREIGN KEY (driver_id) REFERENCES drivers(id)
);
