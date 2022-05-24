package folders.repository;

import folders.configuration.DbConfig;
import folders.dto.SlotDto;
import folders.model.Slot;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@AllArgsConstructor
@Repository
public class SlotRepository {

    private final DbConfig dbConfig;
    private JdbcTemplate jdbcTemplate;

    public List<Slot> get() {
        String query = "SELECT * FROM slots";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper(Slot.class));
    }

    public void delete(Integer id) {
        String query = "DELETE FROM slots WHERE id = ?";
        try {
            PreparedStatement preparedStatement = dbConfig.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void add(String timeStart, String timeStop, Long kbt, Long notKbt, Long cityId) {
        String slots = "INSERT INTO slots (time_start, time_stop, kbt, not_kbt, city_id) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(slots, timeStart, timeStop, kbt, notKbt, cityId);

        String getId = "SELECT id FROM slots WHERE time_start='%s' AND time_stop='%s';";
        Integer id = jdbcTemplate.queryForObject(String.format(getId, timeStart, timeStop), Integer.class);

        String reserved_slots = "INSERT INTO reserved_slots (delivery_date, slot_id) VALUES (?, ?)";
        jdbcTemplate.update(reserved_slots, timeStart, id);
    }

    public void update(SlotDto dto, String id) {
        String slots = "UPDATE slots SET time_start = ?, time_stop =?, kbt = ?, not_kbt = ? where id = ?";
        String reserved_slots = "UPDATE reserved_slots SET delivery_date = ? where slot_id = ?";

        jdbcTemplate.update(slots, dto.getTimeStart(), dto.getTimeStop(), dto.getCapKbt(), dto.getCapNotKbt(), id);
        jdbcTemplate.update(reserved_slots, dto.getTimeStart(), id);
    }
}