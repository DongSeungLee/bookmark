package com.example.demo;

import com.example.demo.junit.Point;
import com.example.demo.junit.Rectangle;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    @Test
    public void testLocalDateTime() {
        LocalDateTime here = LocalDateTime.now();
        // null이면 NPE 발생!
        // 여기서 isAfter로 하면 false다. method가 나중에 호출되어서 더 늦은 시각이다.
        // 놀랍게도 here가 null 아닐 때 orElse는 반드시 한번 실행된다.
        // 그렇기 때문에 왠만하면 lambda로 하는 것이 효율적이다.
        // 생각해 보니 당연하다. 왜냐하면 parsing할 때 method가 argument로 있으면 먼저 실행한다.
        // method가 inner로 존재하고 있다면 이것을 먼저 실행하는 것이 language grammar였다.
        System.out.println(LocalDateTime.now().isBefore(Optional.ofNullable(here)
                .orElse(getNow())));

    }

    private LocalDateTime getNow() {
        System.out.println("getNow method is called");
        return LocalDateTime.now();
    }

    @Test
    public void testBooleanNull() {
        Boolean active = null;
        System.out.println(active == null ? "Y" : "N");
        Optional.ofNullable(active)
                .ifPresent(entity->{
                    System.out.println("exists!");
                    System.out.println(active);
                });
    }
}
