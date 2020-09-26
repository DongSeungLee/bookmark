package com.example.demo;

import com.example.demo.junit.Point;
import com.example.demo.junit.Rectangle;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;


public class RectangleTest {
    private Rectangle rectangle;


    @Test
    public void answersArea() {
        rectangle = new Rectangle(new Point(5, 5), new Point(15, 10));
        assertThat(rectangle.area(), equalTo(50));
    }

    @Test
    public void allowsDynamicallyChangingSize() {
        rectangle = new Rectangle(new Point(5, 5));
        rectangle.setOppositeCorner(new Point(130, 130));
        assertThat(rectangle.area(), equalTo(15625));
    }

    @Test
    public void testList() {
        List<Integer> list = mock(List.class, RETURNS_SMART_NULLS);
        when(list.get(0)).thenReturn(10);
        when(list.size()).thenReturn(10);


        System.out.println(list.get(0));
        System.out.println(list.size());
        System.out.println(list.get(2));
        // 빈 list에 대해서 크기 0인 배열을 만들어 준다.
        System.out.println(list.toArray());
        // 그냥 any()라고 하면 안됨. Object가 맞지 않아서 그런 것으로 보임.

        verify(list, times(2)).get(anyInt());
        verify(list, times(1)).size();
    }

    @Test
    public void testHOHO() {
        Boolean a = true;
        Integer b = null;
        if (a && 1 == b) {
            System.out.println("HOHO");
        }
    }
}
