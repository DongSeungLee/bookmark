package com.example.demo;

import com.example.demo.model.BillAddressDto;
import org.junit.Test;

public class BillAddressTest {
    @Test
    public void getBillAddressTest() {
        BillAddressDto billAddressDto = BillAddressDto.builder()
                .streetNo1("hoho")
                .city("Los Angeles")
                .state("CA")
                .zipCode("9005")
                .build();
        System.out.println(billAddressDto.getVendorAddress());
        String vendorAddress = billAddressDto.getVendorAddress();
        byte[] str = vendorAddress.getBytes();
        for(int i=0,len = vendorAddress.length();i<len;i++){
            System.out.println((char)str[i]);
        }
    }
}
