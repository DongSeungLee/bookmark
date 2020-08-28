package com.example.demo;

import com.example.demo.junit.Point;
import com.example.demo.junit.Rectangle;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.*;
import static org.junit.Assert.assertThat;
import static com.example.demo.ConstrainsSidesTo.constrainsSidesTo;
public class RectangleTest {
    private Rectangle rectangle;

    @After
    public void ensureInvariant() {
        assertThat(rectangle, constrainsSidesTo(100));
    }

    @Test
    public void answersArea() {
        rectangle = new Rectangle(new Point(5, 5), new Point (15, 10));
        assertThat(rectangle.area(), equalTo(50));
    }

    @Test
    public void allowsDynamicallyChangingSize() {
        rectangle = new Rectangle(new Point(5, 5));
        rectangle.setOppositeCorner(new Point(130, 130));
        assertThat(rectangle.area(), equalTo(15625));
    }
}
