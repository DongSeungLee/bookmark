package com.example.demo;

import com.example.demo.member.model.MemberEntity;
import com.example.demo.model.BillAddressDto;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
}
