package folders.service;

import folders.dto.CityDto;
import folders.dto.SlotDto;
import folders.model.City;
import folders.model.Slot;
import folders.repository.CityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class CityService {

    private final CityRepository cityRepository;

    public List<CityDto> get() {
        List<City> cities = cityRepository.get();
        List<CityDto> cityDtoRes = new ArrayList<>();

        CityDto dto = null;
        for (City city : cities) {
            dto = new CityDto();
            dto.setId(city.getId().toString());
            dto.setName(city.getName());

            cityDtoRes.add(dto);
        }

        return cityDtoRes;
    }

    public void update(String id, CityDto dto) {
        cityRepository.update(id, dto.getName());
    }

    public void delete(String id) {
        cityRepository.delete(id);
    }

    public void add(CityDto dto) {
        cityRepository.add(dto.getName());
    }
}
