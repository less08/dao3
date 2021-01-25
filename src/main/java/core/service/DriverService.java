package core.service;

import core.model.Driver;
import java.util.Optional;

public interface DriverService extends GenericService<Driver, Long> {

    Optional<Driver> findByLogin(String login);
}
