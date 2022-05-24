package folders.controller;

import folders.dto.CityDto;
import folders.dto.SlotDto;
import folders.model.City;
import folders.service.CityService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/city")
public class CityController {

    private final CityService cityService;

    @GetMapping
    @ApiOperation(value = "Получить все города")
    public ResponseEntity<?> get() {
        List<CityDto> dtoList = cityService.get();

        if (!dtoList.isEmpty()) {
            return new ResponseEntity<>(dtoList, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping
    @ApiOperation(value = "Добавить город")
    public ResponseEntity<?> add(@RequestBody CityDto dto) {
        cityService.add(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Oбновить город")
    public ResponseEntity<?> update(
            @ApiParam(value = "Id города")
            @PathVariable String id,
            @RequestBody CityDto dto) {
        cityService.update(id,dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Удаление города")
    public void delete(
            @ApiParam(value = "Id города")
            @PathVariable String id) {
        cityService.delete(id);
    }
}
