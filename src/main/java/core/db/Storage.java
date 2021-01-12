package core.db;

import core.model.Manufacturer;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    public static Long manufacturerId = 0L;
    public static final List<Manufacturer> manufacturers = new ArrayList<>();

    public static void addManufacturer(Manufacturer manufacturer) {
        manufacturerId++;
        manufacturer.setId(manufacturerId);
        manufacturers.add(manufacturer);
    }
}
