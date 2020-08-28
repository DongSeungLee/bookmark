package com.example.demo.model;


import lombok.Builder;
import lombok.Getter;

@Getter
public class BillAddressDto {
    private Integer wid;
    private String vendorAddress;
    private String phone;
    private String fax;
    private String streetNo1;
    private String state;
    private String city;
    private String zipCode;

    public BillAddressDto() {

    }

    public BillAddressDto(Integer wid, String streetNo1, String state,
                          String city, String zipCode, String phone,
                          String fax) {
        this.wid = wid;
        this.streetNo1 = streetNo1;
        this.state = state;
        this.city = city;
        this.zipCode = zipCode;
        this.phone = phone;
        this.fax = fax;
    }

    @Builder
    public BillAddressDto(Integer wid,
                          String vendorAddress, String phone, String fax, String streetNo1, String state,
                          String city, String zipCode) {
        this.wid = wid;
        this.vendorAddress = vendorAddress;
        this.phone = phone;
        this.fax = fax;
        this.streetNo1 = streetNo1;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    public String getVendorAddress() {
        if (vendorAddress != null)
            return vendorAddress;
        StringBuilder stringBuilder = new StringBuilder();
        if (streetNo1 != null) {
            stringBuilder.append(streetNo1);
        }
        stringBuilder.append(", ");
        if (city != null) {
            stringBuilder.append(city);
        }
        stringBuilder.append(", ");
        if (state != null) {
            stringBuilder.append(state);
        }
        stringBuilder.append(", ");
        if (zipCode != null) {
            stringBuilder.append(zipCode);
        }
        return vendorAddress = stringBuilder.toString();
    }
}

