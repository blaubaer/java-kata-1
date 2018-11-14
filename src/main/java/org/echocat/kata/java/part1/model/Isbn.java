package org.echocat.kata.java.part1.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import net.jcip.annotations.Immutable;

import javax.annotation.Nonnull;
import java.util.regex.Pattern;

@Immutable
public class Isbn extends SimpleValue {

    protected static final Pattern PATTERN = Pattern.compile("[\\d]{4}-[\\d]{4}-[\\d]{4}");

    @Nonnull
    @JsonCreator
    public static Isbn isbnOf(@Nonnull String plainValue) throws IllegalArgumentException {
        if (!PATTERN.matcher(plainValue).matches()) {
            throw new IllegalArgumentException("Illegal ISBN: " + plainValue);
        }
        return new Isbn(plainValue);
    }

    protected Isbn(@Nonnull String value) {
        super(value);
    }

}
