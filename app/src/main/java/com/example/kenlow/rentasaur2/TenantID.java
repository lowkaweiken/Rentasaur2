package com.example.kenlow.rentasaur2;

import io.reactivex.annotations.NonNull;

/**
 * Created by Ken Low on 28-Mar-18.
 */

public class TenantID {

    public String tenantId;

    public <T extends TenantID> T withId(@NonNull final String id) {
        this.tenantId = id;
        return (T) this;
    }

}
