package com.epam.jwd.core_final.io;

import com.epam.jwd.core_final.domain.AbstractBaseEntity;
import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.BaseEntity;

import java.io.*;
import java.util.Objects;

public interface WriteStrategy<T extends BaseEntity> {
    void writeEntity(T entity) throws IOException;
}
