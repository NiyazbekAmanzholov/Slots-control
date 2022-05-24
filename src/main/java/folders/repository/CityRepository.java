package folders.repository;

import folders.configuration.DbConfig;
import folders.model.City;
import folders.model.Slot;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@AllArgsConstructor
@Repository
public class CityRepository {

    private final DbConfig dbConfig;

    private JdbcTemplate jdbcTemplate;

    public List<City> get() {
        String query = "SELECT * FROM city";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper(City.class));
    }

    public void update(String id, String name) {
        String query = "UPDATE city set name = ? where id = ?";
        try {
            PreparedStatement preparedStatement = dbConfig.getConnection().prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(String id) {
        String query = "DELETE FROM city WHERE id = ?";

        try {
            PreparedStatement preparedStatement = dbConfig.getConnection().prepareStatement(query);
            preparedStatement.setString(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void add(String name) {
        String query = "INSERT INTO city (name) VALUES (?)";

        try {
            PreparedStatement preparedStatement = dbConfig.getConnection().prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
