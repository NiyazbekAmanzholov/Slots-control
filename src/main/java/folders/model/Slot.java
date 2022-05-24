package folders.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class Slot {
    private Long id;
    private String timeStart;
    private String timeStop;
    private Long kbt;
    private Long notKbt;
    private Long reserved_kbt;
    private Long reserved_not_kbt;
    private Long city_id;
}
