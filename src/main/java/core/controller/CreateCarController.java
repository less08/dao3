package core.controller;

import core.lib.Injector;
import core.model.Car;
import core.service.CarService;
import core.service.ManufacturerService;
import java.io.IOException;
import java.util.NoSuchElementException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateCarController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("core");
    private final CarService carService =
            (CarService) injector.getInstance(CarService.class);
    private final ManufacturerService manufacturerService =
            (ManufacturerService) injector.getInstance(ManufacturerService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/cars/create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        try {
            String model = req.getParameter("model");
            String manufacturer = req.getParameter("manufacturer");
            Long manufacturerId = Long.parseLong(manufacturer);
            Car car = new Car(model, manufacturerService.get(manufacturerId));
            carService.create(car);
            resp.sendRedirect(req.getContextPath() + "/");
        } catch (NoSuchElementException e) {
            req.setAttribute("message", "Incorrect data input. "
                    + "Check if manufacturer with provided id exists");
            req.getRequestDispatcher("/WEB-INF/views/cars/create.jsp").forward(req, resp);
        }
    }
}
