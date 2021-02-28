package com.epam.jwd.core_final.domain;

import com.epam.jwd.core_final.domain.BaseEntity;

public interface EntityFactory<T extends BaseEntity> {
    T create(Object... args) throws ClassCastException, IllegalArgumentException;
}
