package folders.repository;

import folders.configuration.DbConfig;
import folders.model.Slot;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Repository
public class ReserveSlotRepository {

    private final DbConfig dbConfig;
    private JdbcTemplate jdbcTemplate;

    public List<Slot> getFreeSlots(String city, String date) {
        String query = "select t2.id, t2.time_start, t2.time_stop, t2.kbt, t2.not_kbt, t1.reserved_kbt, t1.reserved_not_kbt, t2.city_id from " +
                "reserved_slots t1 inner join slots t2 on t1.slot_id = t2.id inner join city t3 on t2.city_id=t3.id where t3.name = " +
                "'" + city + "' and t2.time_start like '" + date + "%'";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper(Slot.class));
    }

    public void bookKbtSlot(String date, String slotId) {
        String query = "UPDATE reserved_slots SET reserved_kbt = reserved_kbt + 1 where delivery_date like ? AND slot_id = ?";

        try {
            PreparedStatement preparedStatement = dbConfig.getConnection().prepareStatement(query);
            preparedStatement.setString(1, date + "%");
            preparedStatement.setString(2, slotId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void bookNotKbtSlot(String date, String slotId) {
        String query = "UPDATE reserved_slots SET reserved_not_kbt = reserved_not_kbt + 1 where delivery_date like ? AND slot_id = ?";

        try {
            PreparedStatement preparedStatement = dbConfig.getConnection().prepareStatement(query);
            preparedStatement.setString(1, date + "%");
            preparedStatement.setString(2, slotId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, Integer> getCapacityReservedKpdAndNotKpd(Date date, Integer slotId) {

        Map<String, Integer> map = new HashMap<>();
        String query = "select reserved_kbt, reserved_not_kbt from reserved_slots where delivery_date like ? and slot_id = ?";

        try {

            PreparedStatement preparedStatement = dbConfig.getConnection().prepareStatement(query);
            preparedStatement.setString(1, date.toString() + "%");
            preparedStatement.setString(2, slotId.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                map.put("reserved_kbt", resultSet.getInt( "reserved_kbt"));
                map.put("reserved_not_kbt", resultSet.getInt( "reserved_not_kbt"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return map;
    }

    public Map<String, Integer> getCapacityStaticKpdAndNotKpd(Date date, Integer slotId){
        Map<String, Integer> map = new HashMap<>();
        String query = "select kbt, not_kbt from slots where time_start like ? and id = ?";

        try {
            PreparedStatement preparedStatement = dbConfig.getConnection().prepareStatement(query);
            preparedStatement.setString(1, date.toString() + "%");
            preparedStatement.setString(2, slotId.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                map.put("kbt", resultSet.getInt( "kbt"));
                map.put("not_kbt", resultSet.getInt( "not_kbt"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return map;
    }

    public void delete(String id) {
        String query = "DELETE FROM reserved_slots WHERE id = ?";

        try {
            PreparedStatement preparedStatement = dbConfig.getConnection().prepareStatement(query);
            preparedStatement.setString(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
