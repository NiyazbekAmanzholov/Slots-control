package folders.dto;

import lombok.Data;

@Data
public class SlotDto {
    private Long id;
    private String timeStart;
    private String timeStop;
    private Long capKbt;
    private Long capNotKbt;
    private Long cityId;
}
