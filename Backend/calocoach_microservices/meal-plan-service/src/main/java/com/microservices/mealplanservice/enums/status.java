package com.microservices.mealplanservice.enums;

import lombok.Getter;

@Getter
public enum status {
    ACCEPTED,
    REJECTED,
    BLOCKED,
    IGNORED,
    PENDING
}
