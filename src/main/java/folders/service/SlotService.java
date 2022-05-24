package folders.service;

import folders.model.Slot;
import folders.dto.SlotDto;
import folders.repository.SlotRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class SlotService {
    private final SlotRepository slotRepository;
    private final ReserveSlotService reserveSlotService;

    public List<SlotDto> get() {
        List<Slot> slot = slotRepository.get();
        List<SlotDto> slotDtosRes = new ArrayList<>();

        SlotDto dto = null;
        for (int i = 0; i < slot.size(); i++) {
            dto = new SlotDto();

            dto.setId(slot.get(i).getId());
            dto.setTimeStart(reserveSlotService.getTime(slot.get(i).getTimeStart()));
            dto.setTimeStop(reserveSlotService.getTime(slot.get(i).getTimeStop()));
            dto.setCapKbt(slot.get(i).getKbt());
            dto.setCapNotKbt(slot.get(i).getNotKbt());
            slotDtosRes.add(dto);
        }

        return slotDtosRes;
    }

    public void delete(Integer id) {
        slotRepository.delete(id);
    }

    public void add(SlotDto dto) {
        slotRepository.add(dto.getTimeStart(), dto.getTimeStop(), dto.getCapKbt(), dto.getCapNotKbt(), dto.getCityId());
    }

    public void update(SlotDto dto, String id) {
        slotRepository.update(dto, id);
    }
}
