package folders.service;

import folders.dto.SlotDto;
import folders.model.Slot;
import folders.repository.ReserveSlotRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class ReserveSlotService {

    private final ReserveSlotRepository reserveSlotRepository;
    private static final Double STATIC_VALUE = 0.2D;

    private static Integer kbtS = null;
    private static Integer not_kbtS = null;
    private static Integer r_kbt = null;
    private static Integer r_not_kbt = null;

    public List<SlotDto> getFreeSlots(String city, String date, Double value) {
        List<Slot> slot = reserveSlotRepository.getFreeSlots(city, date);
        List<SlotDto> slotDtoRes = new ArrayList<>();

        SlotDto dto;
        for (int i = 0; i < slot.size(); i++) {
            dto = new SlotDto();
            boolean checkCapacityNotKpd = slot.get(i).getReserved_not_kbt() < slot.get(i).getNotKbt() && value < STATIC_VALUE;
            boolean checkCapacityKpd = slot.get(i).getReserved_kbt() < slot.get(i).getKbt() && value > STATIC_VALUE;

            if (checkCapacityNotKpd) {
                dto.setId(slot.get(i).getId());
                dto.setTimeStart(slot.get(i).getTimeStart().substring(11, 16));
                dto.setTimeStop(getTime(slot.get(i).getTimeStop()));
                dto.setCapKbt(slot.get(i).getKbt() - slot.get(i).getReserved_kbt());
                dto.setCapNotKbt(slot.get(i).getNotKbt() - slot.get(i).getReserved_not_kbt());
                dto.setCityId(slot.get(i).getCity_id());

                slotDtoRes.add(dto);
            }

            if (checkCapacityKpd) {
                dto.setId(slot.get(i).getId());
                dto.setTimeStart(getTime(slot.get(i).getTimeStart()));
                dto.setTimeStop(getTime(slot.get(i).getTimeStop()));
                dto.setCapKbt(slot.get(i).getKbt() - slot.get(i).getReserved_kbt());
                dto.setCapNotKbt(slot.get(i).getNotKbt() - slot.get(i).getReserved_not_kbt());
                dto.setCityId(slot.get(i).getCity_id());

                slotDtoRes.add(dto);
            }
        }

        return slotDtoRes;
    }

    public void bookSlot(Date date, Integer slot_id, Double value) {
        distributeKeys(date, slot_id);

        if (value > STATIC_VALUE && r_kbt + 1 <= kbtS) {
            reserveSlotRepository.bookKbtSlot(date.toString(), slot_id.toString());
        }
        if (value < STATIC_VALUE && r_not_kbt + 1 <= not_kbtS) {
            reserveSlotRepository.bookNotKbtSlot(date.toString(), slot_id.toString());
        }
    }

    public void distributeKeys(Date date, Integer slot_id) {
        Map<String, Integer> allStaticKpd = reserveSlotRepository.getCapacityStaticKpdAndNotKpd(date, slot_id);
        for (Map.Entry<String, Integer> data : allStaticKpd.entrySet()) {

            if (data.getKey().equals("kbt")) {
                kbtS = data.getValue();
            }
            if (data.getKey().equals("not_kbt")) {
                not_kbtS = data.getValue();
            }
        }

        Map<String, Integer> allNotStaticKpd = reserveSlotRepository.getCapacityReservedKpdAndNotKpd(date, slot_id);
        for (Map.Entry<String, Integer> data : allNotStaticKpd.entrySet()) {

            if (data.getKey().equals("reserved_kbt")) {
                r_kbt = data.getValue();
            }
            if (data.getKey().equals("reserved_not_kbt")) {
                r_not_kbt = data.getValue();
            }
        }
    }

    public String getTime(String time) {
        return time.substring(11, 16);
    }

    public void delete(String id) {
        reserveSlotRepository.delete(id);
    }
}
