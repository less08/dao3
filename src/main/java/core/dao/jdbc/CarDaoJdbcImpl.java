package core.dao.jdbc;

import core.dao.CarDao;
import core.exception.DataProcessingException;
import core.lib.Dao;
import core.model.Car;
import core.model.Driver;
import core.model.Manufacturer;
import core.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class CarDaoJdbcImpl implements CarDao {
    @Override
    public Car create(Car car) {
        String query = "INSERT INTO cars (model, manufacturer_id) VALUES(?,?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement =
                         connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, car.getModel());
            statement.setLong(2, car.getManufacturer().getId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                car.setId(resultSet.getObject(1, Long.class));
            }
            return car;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create car id: "
                + car.getId() + " model: "
                + car.getModel() + " manufacturer_id: "
                + car.getManufacturer().getId(), e);
        }
    }

    @Override
    public Optional<Car> get(Long id) {
        String query = "SELECT c.id, c.model, m.id AS manufacturer_id, m.name, m.country "
                + "FROM cars c "
                + "INNER JOIN manufacturers m ON c.manufacturer_id = m.id "
                + "WHERE c.id = ? AND c.deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Car car = null;
            while (resultSet.next()) {
                car = createCar(connection, resultSet);
            }
            return Optional.ofNullable(car);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get car with id:" + id, e);
        }
    }

    @Override
    public List<Car> getAll() {
        String query = "SELECT c.id, c.model, m.id AS manufacturer_id, m.name, m.country "
                + "FROM cars c "
                + "INNER JOIN manufacturers m ON c.manufacturer_id = m.id "
                + "WHERE c.deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            List<Car> carList = new ArrayList<>();
            while (resultSet.next()) {
                carList.add(createCar(connection, resultSet));
            }
            return carList;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get cars ", e);
        }
    }

    @Override
    public Car update(Car car) {
        String deleteQuery = "DELETE FROM cars_drivers WHERE car_id = ?";
        String insertQuery = "INSERT INTO cars_drivers (driver_id, car_id) VALUES (?, ?)";
        String updateQuery = "UPDATE cars SET manufacturer_id = ?, model = ? "
                + "WHERE id = ? AND deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
                 PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                 PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {

            updateStatement.setLong(1, car.getManufacturer().getId());
            updateStatement.setString(2, car.getModel());
            updateStatement.setLong(3, car.getId());
            updateStatement.executeUpdate();

            deleteStatement.setLong(1, car.getId());
            deleteStatement.executeUpdate();

            for (Driver driver : car.getDrivers()) {
                insertStatement.setLong(1, driver.getId());
                insertStatement.setLong(2, car.getId());
                insertStatement.executeUpdate();
            }

            return car;

        } catch (SQLException e) {
            throw new DataProcessingException("Can't update car with id=" + car.getId(), e);
        }

    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE cars SET deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete car with id:" + id, e);
        }
    }

    @Override
    public List<Car> getAllByDriver(Long driverId) {
        String query = "SELECT c.car_id, c.model, m.manufacturer_id, "
                + "m.manufacturer_name, m.manufacturer_country, "
                + "d.driver_id, d.driver_name, d.licence_number FROM cars c "
                + "JOIN cars_drivers cd ON c.car_id = cd.car_id "
                + "JOIN drivers d ON d.driver_id = cd.driver_id "
                + "JOIN manufacturers m ON cars.manufacturer_id = m.manufacturer_id "
                + "WHERE c.deleted = FALSE AND d.id=?";
        List<Car> cars = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getFilteredStatement = connection.prepareStatement(query)) {
            getFilteredStatement.setLong(1, driverId);
            ResultSet resultSet = getFilteredStatement.executeQuery();
            while (resultSet.next()) {
                cars.add(createCar(connection, resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get cars by driver " + driverId, e);
        }
        return cars;
    }

    private Car createCar(Connection connection, ResultSet resultSet) throws SQLException {
        String name = resultSet.getString("name");
        String country = resultSet.getString("country");
        Manufacturer manufacturer = new Manufacturer(name, country);
        manufacturer.setId(resultSet.getObject("manufacturer_id", Long.class));
        String model = resultSet.getString("model");
        Car car = new Car(model, manufacturer);
        car.setId(resultSet.getObject("id", Long.class));
        car.setDrivers(getAllDriversForCar(connection,car.getId()));
        return car;
    }

    private List<Driver> getAllDriversForCar(Connection connection, Long carId) {
        List<Driver> drivers = new ArrayList<>();
        String query = "SELECT d.driver_id, d.driver_name, d.driver_license_number "
                + "FROM cars_drivers cd "
                + "JOIN drivers d on d.driver_id = cd.driver_id "
                + "WHERE cd.cars_id = ? AND d.deleted = FALSE";
        try (PreparedStatement statement2 = connection.prepareStatement(query)) {
            statement2.setLong(1, carId);
            ResultSet resultSet = statement2.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String licenceNumber = resultSet.getString("licence_number");
                Driver driver = new Driver(name, licenceNumber);
                driver.setId(resultSet.getObject("id", Long.class));
                drivers.add(driver);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return drivers;
    }
}
