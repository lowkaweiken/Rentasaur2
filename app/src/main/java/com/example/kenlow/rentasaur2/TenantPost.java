package com.example.kenlow.rentasaur2;

/**
 * Created by Ken Low on 28-Mar-18.
 */

public class TenantPost {

    public String tenant_name;
    public String tenant_phone;

    public TenantPost(){

    }

    public TenantPost(String tenant_name, String tenant_phone) {
        this.tenant_name = tenant_name;
        this.tenant_phone = tenant_phone;
    }

    public String getTenant_name() {
        return tenant_name;
    }

    public void setTenant_name(String tenant_name) {
        this.tenant_name = tenant_name;
    }

    public String getTenant_phone() {
        return tenant_phone;
    }

    public void setTenant_phone(String tenant_phone) {
        this.tenant_phone = tenant_phone;
    }




}
