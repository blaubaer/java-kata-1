package org.echocat.kata.java.part1.model;

import com.fasterxml.jackson.annotation.JsonValue;
import net.jcip.annotations.Immutable;

import javax.annotation.Nonnull;
import java.util.Objects;

@Immutable
public class SimpleValue {

    @Nonnull
    private final String value;

    protected SimpleValue(@Nonnull String value) {
        this.value = value;
    }

    @Nonnull
    @JsonValue
    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return value();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || !getClass().equals(o.getClass())) { return false; }
        final SimpleValue that = (SimpleValue) o;
        return Objects.equals(value(), that.value());
    }

    @Override
    public int hashCode() {
        return Objects.hash(value());
    }

}
