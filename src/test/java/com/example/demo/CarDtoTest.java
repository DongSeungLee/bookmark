package com.example.demo;

import com.example.demo.model.CarDto;
import com.example.demo.model.CarMapper;
import com.example.demo.model.CarType;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;
public class CarDtoTest {
    @Test
    public void CarToDtoTest() throws NoSuchMethodException {
        //given
        Car car = new Car( "Morris", 5, CarType.Sedan );

        //when
        CarDto carDto = CarMapper.INSTANCE.carToCarDto( car );

        //then
        assertThat( carDto ).isNotNull();
        assertThat( carDto.getMake() ).isEqualTo( "Morris" );
        assertThat( carDto.getSeatCount() ).isEqualTo( 5 );
        assertThat( carDto.getCarDtoType() ).isEqualTo( "Sedan" );
    }
    @Test
    public void testFlatMap(){
        System.out.println(AddressType.WAREHOUSE.toString());
    }
}
