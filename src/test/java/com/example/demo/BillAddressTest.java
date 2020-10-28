package com.example.demo;

import com.example.demo.member.model.MemberEntity;
import com.example.demo.model.BillAddressDto;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

public class BillAddressTest {

    private BillAddressDto billAddressDto;

    @Before
    public void setUp() throws Exception {
        billAddressDto = BillAddressDto.builder()
                .streetNo1("hoho")
                .city("Los Angeles")
                .state("CA")
                .zipCode("9005")
                .build();
    }

    @Test
    public void getBillAddressTest() {
        System.out.println(billAddressDto.getVendorAddress());
        String vendorAddress = billAddressDto.getVendorAddress();
        byte[] str = vendorAddress.getBytes();
        for (int i = 0, len = vendorAddress.length(); i < len; i++) {
            System.out.println((char) str[i]);
        }
    }

    @Test
    public void testMethodInvocation() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        MemberEntity m1 = new MemberEntity();
        m1.setName("hoho");
        // method name으로 getName얻어도고
        Method m = m1.getClass().getMethod("getName");
        // 이거 실행한다.
        System.out.println(m.invoke(m1));
        // setName을 실행하는데 parameter로는 String class하나 있는 것을 선택한다.
        Method methodSetName = m1.getClass().getMethod("setName", String.class);
        // 당근 method 찾았으니 parameter로 String.class하나 있는 것을 안 상태이다.
        methodSetName.invoke(m1, "hehe");
        // getName으로 확인
        System.out.println(m.invoke(m1));
        // hehe 확인!
        assertThat(m.invoke(m1)).isEqualTo("hehe");
    }

    // 중복 제거
    @Test
    public void test_erase_duplicated_String() {
        List<String> list = new ArrayList();
        list.add("DS1");
        list.add("DS2");
        list.add("DS1");
        list.add("DS2");
        List<String> ret = new LinkedHashSet<>(list).stream().collect(Collectors.toList());
        System.out.println(ret);
        String[] arr = {"DS1", "DS2", "DS1", "DS2", null};
        List<String> beforeSortedList = Arrays.asList(arr);
        List<String> sorted = beforeSortedList.stream().sorted(Comparator.nullsFirst(Comparator.reverseOrder())).collect(Collectors.toList());
        List<Integer> intList = IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toList());
        // o1, o2 를 parameter로 받아서 양수면 swap한다. 그래서 o2가 더크면 swap을 하니 descending ordered sort한다.
        Comparator revseredOrder = (o1, o2) -> (Integer) o2 - (Integer) o1;
        intList = (List<Integer>) intList.stream().sorted(revseredOrder).collect(Collectors.toList());
        System.out.println(intList);
    }

    @Test
    public void test_descending_order() {
        List<Integer> list = IntStream.rangeClosed(1, 11).boxed().collect(Collectors.toList());
        list = list.stream().sorted((o1, o2) -> o2 - o1).collect(Collectors.toList());
        System.out.println(list);
    }

    @Test
    public void test_id_ascending_name_descending_order() {
        Student s1 = Student.builder().id(1).name("AAA").build();
        Student s2 = Student.builder().id(2).name("AAA").build();
        Student s3 = Student.builder().id(1).name("BBB").build();
        Student s4 = Student.builder().id(2).name("BBB").build();
        Student s5 = Student.builder().id(2).name("CCC").build();
        List<Student> list = Arrays.asList(s1, s2, s3, s4, s5);
        // Collections.sort(list);
        // System.out.println(list);
        // Student에 있는 Comparable<Student>의 interface compareTo와 동일한 기능이다.
        // id로 먼저 ascending order하고 같다면 reversedOrder로 name의 역순으로 sorting한다.
        Comparator<Student> reversedOrder = Comparator.comparing(Student::getName).reversed();
        list = list.stream().sorted(Comparator.comparingInt(Student::getId)
                .thenComparing(reversedOrder)).collect(Collectors.toList());
        System.out.println(list);
    }

    @Test(expected = NullPointerException.class)
    public void test_Objects_requireNonNull() {
        Integer a = null;
        // throw NPE
        Objects.requireNonNull(a);
        System.out.println("hoho");
    }

    @Test
    public void test_IndustryConverter() {
        System.out.println(VendorIndustryType.getNum("Women's Clothing"));
        System.out.println(VendorIndustryType.getNum("Man's Clothing"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_IndustryConverter_throwException() {
        System.out.println(VendorIndustryType.getNum("Women1"));
        System.out.println(VendorIndustryType.getNum("Men2"));
    }

    // to lower case!
    @Test
    public void test_Industry_String_array() {
        String[] arr = {"Shoes", "Accessories", "Handbag"};
        List<Integer> ret = Arrays.asList(arr).stream().map(entity -> VendorIndustryType.getNum(entity)).collect(Collectors.toList());
        System.out.println(ret);
    }
}
