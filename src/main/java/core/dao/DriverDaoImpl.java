package core.dao;

import core.db.Storage;
import core.lib.Dao;
import core.model.Driver;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Dao
public class DriverDaoImpl implements DriverDao {
    @Override
    public Driver create(Driver driver) {
        Storage.addDriver(driver);
        return driver;
    }

    @Override
    public Optional<Driver> get(Long id) {
        return Storage.drivers.stream()
                .filter(x -> Objects.equals(x.getId(), id))
                .findFirst();
    }

    @Override
    public List<Driver> getAll() {
        return Storage.drivers;
    }

    @Override
    public Driver update(Driver driver) {
        Driver old = get(driver.getId()).get();
        Storage.drivers.set(Storage.drivers.indexOf(old), driver);
        return old;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.drivers.removeIf(x -> x.getId().equals(id));
    }
}
