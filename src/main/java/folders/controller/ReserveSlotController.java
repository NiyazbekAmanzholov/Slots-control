package folders.controller;


import folders.dto.SlotDto;
import folders.service.ReserveSlotService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/slots")
public class ReserveSlotController {

    private final ReserveSlotService reserveSlotService;

    @GetMapping
    @ApiOperation(value = "Получение свободного слота")
    public ResponseEntity<?> getFreeSlots(
            @ApiParam(value = "Город слота")
            @RequestParam String city,
            @ApiParam(value = "Дата слота")
            @RequestParam String date,
            @ApiParam(value = "Размер товара")
            @RequestParam Double value) {
        List<SlotDto> slotDtoList = reserveSlotService.getFreeSlots(city, date, value);

        if (!slotDtoList.isEmpty()) {
            return new ResponseEntity<>(slotDtoList, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{slot_id}")
    @ApiOperation(value = "Бронирование слота")
    public void bookSlot(
            @ApiParam(value = "Дата слота")
            @RequestParam Date date,
            @ApiParam(value = "Id слота")
            @PathVariable Integer slot_id,
            @ApiParam(value = "Размер слота")
            @RequestParam Double value
    ) {
        reserveSlotService.bookSlot(date, slot_id, value);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Удаление резервированного слота")
    public void delete(
            @ApiParam(value = "Id резервированного слота")
            @PathVariable String id) {
        reserveSlotService.delete(id);
    }
}
