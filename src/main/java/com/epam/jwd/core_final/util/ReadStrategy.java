package com.epam.jwd.core_final.util;

import com.epam.jwd.core_final.domain.AbstractBaseEntity;

import java.io.File;
import java.util.Collection;

public interface ReadStrategy {
    Collection<? extends AbstractBaseEntity> readEntities(File input);
}
