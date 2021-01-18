package core;

import core.lib.Injector;
import core.model.Car;
import core.model.Driver;
import core.model.Manufacturer;
import core.service.CarService;
import core.service.DriverService;
import core.service.ManufacturerService;

public class Application {
    private static Injector injector = Injector.getInstance("core");

    public static void main(String[] args) {
        ManufacturerService manufacturerService = (ManufacturerService) injector
                .getInstance(ManufacturerService.class);
        Manufacturer manufacturer1 = new Manufacturer("Audi", "German");
        Manufacturer manufacturer2 = new Manufacturer("Tesla", "USA");
        Manufacturer manufacturer3 = new Manufacturer("Chevrolet", "USA");
        manufacturerService.create(manufacturer1);
        manufacturerService.create(manufacturer2);
        manufacturerService.create(manufacturer3);
        System.out.println(manufacturerService.getAll());
        Manufacturer updated1 = manufacturerService.get(1L);
        updated1.setName("BMW");
        manufacturerService.update(updated1);
        System.out.println(manufacturerService.getAll());
        System.out.println(manufacturerService.get(3L));
        manufacturerService.delete(2L);
        System.out.println(manufacturerService.getAll());

        DriverService driverService = (DriverService) injector.getInstance(DriverService.class);

        Driver driver1 = new Driver("Dean", "DW1991");
        Driver driver2 = new Driver("Leslie", "LB1993");
        Driver driver3 = new Driver("Harry", "HP1993");
        driverService.create(driver1);
        driverService.create(driver2);
        Driver driver = driverService.create(driver3);
        Driver updateDriver = driverService.get(driver.getId());
        updateDriver.setName("Keith");
        driverService.update(updateDriver);
        System.out.println(driverService.getAll());
        driverService.delete(3L);
        System.out.println(driverService.getAll());

        CarService carService = (CarService) injector.getInstance(CarService.class);
        Car car1 = new Car("A4", manufacturer1);
        Car car2 = new Car("Cybertruck", manufacturer2);
        Car car3 = new Car("Impala", manufacturer3);

        carService.create(car1);
        carService.create(car2);
        carService.create(car3);
        System.out.println(carService.getAll());
        Car updateCar = carService.get(1L);
        updateCar.setModel("R8");
        carService.update(updateCar);
        System.out.println(carService.getAll());
        carService.addDriverToCar(driver1, car3);
        carService.addDriverToCar(driver1, car2);
        carService.addDriverToCar(driver2, car1);
        carService.addDriverToCar(driver2, car3);
        System.out.println(carService.getAll());
    }
}
