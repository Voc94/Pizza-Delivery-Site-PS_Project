package com.masinite.masinite.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


public enum RoleName {
    ROLE_USER,
    ROLE_ADMIN;

}