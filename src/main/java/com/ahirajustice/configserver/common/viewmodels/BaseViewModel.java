package com.ahirajustice.configserver.common.viewmodels;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public abstract class BaseViewModel {

    private long id;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

}
