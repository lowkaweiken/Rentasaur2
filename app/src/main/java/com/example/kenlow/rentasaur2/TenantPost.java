package com.example.kenlow.rentasaur2;

/**
 * Created by Ken Low on 28-Mar-18.
 */

//                    tenantMap.put("tenant_monthly_rental",tenantMonthlyRental);
//                            tenantMap.put("tenant_email",tenantEmail);
//                            tenantMap.put("tenant_start_date",tenantStartDate);
//                            tenantMap.put("tenant_end_date",tenantEndDate);

public class TenantPost extends TenantID {

    public String tenant_name;
    public String tenant_phone;
    public String tenant_property;
    public String tenant_monthly_rental;
    public String tenant_email;
    public String tenant_start_date;
    public String tenant_end_date;

    public TenantPost(){


    }

    public TenantPost(String tenant_name, String tenant_phone, String tenant_property, String tenant_monthly_rental, String tenant_email, String tenant_start_date, String tenant_end_date) {
        this.tenant_name = tenant_name;
        this.tenant_phone = tenant_phone;
        this.tenant_property = tenant_property;
        this.tenant_monthly_rental = tenant_monthly_rental;
        this.tenant_email = tenant_email;
        this.tenant_start_date = tenant_start_date;
        this.tenant_end_date = tenant_end_date;
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

    public String getTenant_property() {
        return tenant_property;
    }

    public void setTenant_property(String tenant_property) {
        this.tenant_property = tenant_property;
    }

    public String getTenant_monthly_rental() {
        return tenant_monthly_rental;
    }

    public void setTenant_monthly_rental(String tenant_monthly_rental) {
        this.tenant_monthly_rental = tenant_monthly_rental;
    }

    public String getTenant_email() {
        return tenant_email;
    }

    public void setTenant_email(String tenant_email) {
        this.tenant_email = tenant_email;
    }

    public String getTenant_start_date() {
        return tenant_start_date;
    }

    public void setTenant_start_date(String tenant_start_date) {
        this.tenant_start_date = tenant_start_date;
    }

    public String getTenant_end_date() {
        return tenant_end_date;
    }

    public void setTenant_end_date(String tenant_end_date) {
        this.tenant_end_date = tenant_end_date;
    }



}