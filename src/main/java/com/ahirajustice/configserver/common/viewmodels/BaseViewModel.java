package com.ahirajustice.configserver.common.viewmodels;

import java.time.LocalDateTime;

public abstract class BaseViewModel {

    private long id;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

}
