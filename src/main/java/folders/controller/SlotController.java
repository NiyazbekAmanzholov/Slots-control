package folders.controller;

import folders.dto.SlotDto;
import folders.service.SlotService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/slots/limit")
public class SlotController {

    private final SlotService slotService;

    @GetMapping
    @ApiOperation(value = "Получение всех слотов с их лимитом и id города")
    public ResponseEntity<?> get() {
        List<SlotDto> dto = slotService.get();

        if (!dto.isEmpty()) {
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping
    @ApiOperation(value = "Добавление нового слота с лимитом")
    public ResponseEntity<?> add(@RequestBody SlotDto dto) {
        slotService.add(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Обновление слота")
    public ResponseEntity<?> update(
            @ApiParam(value = "Id слота")
            @PathVariable String id,
            @RequestBody SlotDto dto) {
        slotService.update(dto, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Удаление слота")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        slotService.delete(id);
        return ResponseEntity.ok().build();
    }
}
