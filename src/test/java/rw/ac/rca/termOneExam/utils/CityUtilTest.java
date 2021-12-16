package rw.ac.rca.termOneExam.utils;

import org.hamcrest.core.StringContains;
import org.junit.Assert;
import org.junit.Test;
import rw.ac.rca.termOneExam.domain.City;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.*;

public class CityUtilTest {
    City city = spy(City.class);
    @Test
    public void Lessthan_40Degrees(){
city.setWeather(24);
        Assert.assertTrue(city.getWeather()<40);
    }
    @Test
    public void greaterThan_10Degrees(){
city.setWeather(10.1);
    Assert.assertTrue(city.getWeather()>10);
    }
    @Test
    public void ContainsMusanze_Kigali(){
        city.setName(" Kigali via Musanze");
        Assert.assertThat(city.getName(), StringContains.containsString("Musanze"));
        Assert.assertThat(city.getName(), StringContains.containsString("Kigali"));
    }
  @Test
    public void Spying(){
      ArrayList<City> arrayListSpy = spy(ArrayList.class);
      City city = new City(10,"Paris",34,62);
      arrayListSpy.add(city);
      System.out.println(arrayListSpy.get(0).getWeather());//Test0
      System.out.println(arrayListSpy.size());//1

      arrayListSpy.add(city);
      arrayListSpy.add(city);
      System.out.println(arrayListSpy.size());//3

      when(arrayListSpy.size()).thenReturn(5);
      System.out.println(arrayListSpy.size());//5
      //now call is lost so 5 will be returned no matter what

      arrayListSpy.add(city);
      System.out.println(arrayListSpy.size());
  }
  @Test
    public void Mocking(){
      City city = new City(10,"Paris",34,62);
      List<City> mockList = mock(List.class);
      when(mockList.size()).thenReturn(5);
      assertEquals(5, mockList.size());
      when(mockList.get(0)).thenReturn(city);
      assertEquals("Paris", mockList.get(0).getName());
      assertEquals(null, mockList.get(1));
      verify(mockList,atLeast(1)).get(anyInt());
   }
}
