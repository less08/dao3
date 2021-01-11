package core;

import core.lib.Injector;
import core.model.Manufacturer;
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
    }
}
