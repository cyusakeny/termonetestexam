package rw.ac.rca.termOneExam.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.dto.CreateCityDTO;
import rw.ac.rca.termOneExam.repository.ICityRepository;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CityServiceTest {
    @Mock
    private ICityRepository repository;

    @InjectMocks
CityService cityService;
    @Test
    public void GetAllCities(){

        when(repository.findAll()).thenReturn(Arrays.asList(new City("Manchester",43),
                new City("Barcelona",19)));
        assertEquals(43,cityService.getAll().get(0).getWeather());
        assertEquals(19,cityService.getAll().get(1).getWeather());
    }
    @Test
    public void GetById_Success(){
        City city = new City(10,"Paris",34,62);
        when(repository.findById(city.getId())).thenReturn(java.util.Optional.of(city));
        assertEquals(34,cityService.getById(city.getId()).get().getWeather());
    }
    @Test
    public void GetById_failure(){
        City city = new City(10,"Paris",34,62);
        when(repository.findById(city.getId())).thenReturn(java.util.Optional.of(city));
        assertEquals(false,cityService.getById(6).isPresent());
    }
    @Test
    public void SaveCity_success() throws IllegalAccessException, InstantiationException {
        CreateCityDTO createCity = new CreateCityDTO("Paris",34);
        City city = new City(10,"Paris",34,62);
        when(repository.save(any(City.class))).thenReturn(city);
        assertEquals(34,cityService.save(createCity).getWeather());
    }

    @Test
    public void SaveCity_Failure(){
        CreateCityDTO createCity = new CreateCityDTO("Paris",34);
        City city = new City(10,"Paris",34,62);
        when(repository.save(city)).thenReturn(city);
        assertNull(cityService.save(createCity));
    }
    @Test
    public void ExistsByName_Success(){
        City city = new City(10,"Paris",34,62);
        when(repository.existsByName(city.getName())).thenReturn(true);
        assertTrue(cityService.existsByName(city.getName()));
    }
    @Test
    public void ExistsByName_Failure(){
        City city = new City(10,"Paris",34,62);
        when(repository.existsByName(city.getName())).thenReturn(true);
        assertFalse(cityService.existsByName("Madrid"));
    }
}
