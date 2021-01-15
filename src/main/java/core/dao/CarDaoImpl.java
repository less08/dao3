package core.dao;

import core.db.Storage;
import core.lib.Dao;
import core.model.Car;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Dao
public class CarDaoImpl implements CarDao {
    @Override
    public Car create(Car car) {
        Storage.addCar(car);
        return car;
    }

    @Override
    public Optional<Car> get(Long id) {
        return Storage.cars.stream()
                .filter(c -> Objects.equals(c.getId(), id))
                .findFirst();
    }

    @Override
    public List<Car> getAll() {
        return Storage.cars;
    }

    @Override
    public Car update(Car car) {
        Car old = get(car.getId()).get();
        Storage.cars.set(Storage.cars.indexOf(old), car);
        return old;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.cars.removeIf(c -> c.getId().equals(id));
    }

    @Override
    public List<Car> getAllByDriver(Long driverId) {
        return Storage.cars.stream()
                .filter(x -> x.getDrivers()
                        .stream()
                        .map(d -> d.getId())
                        .collect(Collectors.toList())
                        .contains(driverId))
                .collect(Collectors.toList());
    }
}
