package core.controller;

import core.lib.Injector;
import core.model.Car;
import core.model.Driver;
import core.service.CarService;
import core.service.DriverService;
import java.io.IOException;
import java.util.NoSuchElementException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddDriverToCarController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("core");
    private final CarService carService =
            (CarService) injector.getInstance(CarService.class);
    private final DriverService driverService =
            (DriverService) injector.getInstance(DriverService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/cars/drivers/add.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        try {
            Long carId = Long.valueOf(req.getParameter("car_id"));
            Long driverId = Long.valueOf(req.getParameter("driver_id"));
            Car car = carService.get(carId);
            Driver driver = driverService.get(driverId);
            carService.addDriverToCar(driver, car);
            resp.sendRedirect(req.getContextPath() + "/");
        } catch (NoSuchElementException e) {
            req.setAttribute("message", "Incorrect data input. "
                    + "Check if driver/car with provided id exists");
            req.getRequestDispatcher("/WEB-INF/views/cars/drivers/add.jsp").forward(req, resp);
        }
    }
}
