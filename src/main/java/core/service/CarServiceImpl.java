package core.service;

import core.dao.CarDao;
import core.db.Storage;
import core.lib.Inject;
import core.lib.Service;
import core.model.Car;
import core.model.Driver;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {
    @Inject
    private CarDao carDao;

    @Override
    public Car create(Car car) {
        return carDao.create(car);
    }

    @Override
    public Car get(Long id) {
        return carDao.get(id).get();
    }

    @Override
    public List<Car> getAll() {
        return carDao.getAll();
    }

    @Override
    public Car update(Car car) {
        return carDao.update(car);
    }

    @Override
    public boolean delete(Long id) {
        return carDao.delete(id);
    }

    @Override
    public void addDriverToCar(Driver driver, Car car) {
        carDao.get(car.getId()).get().getDrivers().add(driver);
    }

    @Override
    public void removeDriverFromCar(Driver driver, Car car) {
        carDao.get(car.getId()).get().getDrivers().remove(driver);
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
