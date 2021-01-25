package core.controller;

import core.lib.Injector;
import core.model.Driver;
import core.service.DriverService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateDriverController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("core");
    private final DriverService driverService =
            (DriverService) injector.getInstance(DriverService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/drivers/create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String name = req.getParameter("name");
        String licenceNumber = req.getParameter("licenceNumber");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        driverService.create(new Driver(name, licenceNumber, login, password));
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
