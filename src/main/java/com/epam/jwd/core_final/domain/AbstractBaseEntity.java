package com.epam.jwd.core_final.domain;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Expected fields:
 * <p>
 * id {@link Long} - entity id
 * name {@link String} - entity name
 */
public abstract class AbstractBaseEntity implements BaseEntity {
    private static Long counter = 0L;
    private final Long id;
    private final String name;

    public AbstractBaseEntity(@NotNull String name) {
        this.name = name;
        this.id = ++counter;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractBaseEntity that = (AbstractBaseEntity) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
