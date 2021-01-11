package core.db;

import core.model.Manufacturer;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    public static long manufacturerId = 0;
    public static List<Manufacturer> manufacturers = new ArrayList<>();

    public static void addManufacturer(Manufacturer manufacturer) {
        manufacturerId++;
        manufacturer.setId(manufacturerId);
        manufacturers.add(manufacturer);
    }
}
