package rw.ac.rca.termOneExam.controller;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.dto.CreateCityDTO;
import rw.ac.rca.termOneExam.utils.APICustomResponse;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CityControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void GetById_Success() {
        ResponseEntity<City> city = this.restTemplate.getForEntity("/api/cities/id/101", City.class);
        assertTrue(city.getStatusCode().is2xxSuccessful());
        assertEquals("Kigali", city.getBody().getName());
        assertEquals(24, city.getBody().getWeather());
    }

    @Test
    public void GetById_Failure() {
        ResponseEntity<APICustomResponse> response =
                this.restTemplate.getForEntity("/api/cities/id/1", APICustomResponse.class);

        Assert.assertEquals(404, response.getStatusCodeValue());
        assertFalse(response.getBody().isStatus());
        assertEquals("City not found with id " + 1, response.getBody().getMessage());
    }

    @Test
    public void GetAll_Success() throws JSONException {
        String response = this.restTemplate.getForObject("/api/cities/all", String.class);
        JSONAssert.assertEquals("[{id:101},{id:102},{id:103},{id:104}]", response, false);
    }
@Test
    public void create_success() {
        CreateCityDTO request = new CreateCityDTO("Biryogo",35);
        ResponseEntity<City> response = this.restTemplate.postForEntity("/api/cities/add", request, City.class);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Biryogo", response.getBody().getName());
    }
    @Test
    public void create_failure(){
        CreateCityDTO request = new CreateCityDTO("Kigali",35);
        ResponseEntity<APICustomResponse> response = this.restTemplate.postForEntity("/api/cities/add", request, APICustomResponse.class);
        assertEquals(400, response.getStatusCodeValue());
        assertFalse(response.getBody().isStatus());
        assertEquals("City name "+request.getName()+" is registered already", response.getBody().getMessage());
    }
}