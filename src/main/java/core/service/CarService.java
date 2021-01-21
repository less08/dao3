package core.service;

import core.model.Car;
import core.model.Driver;
import java.util.List;

public interface CarService extends GenericService<Car, Long> {

    void addDriverToCar(Driver driver, Car car);

    void removeDriverFromCar(Driver driver, Car car);

    List<Car> getAllByDriver(Long driverId);
}
