package org.echocat.kata.java.part1.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import net.jcip.annotations.Immutable;

import javax.annotation.Nonnull;
import java.util.regex.Pattern;

@Immutable
public class EmailAddress extends SimpleValue {

    protected static final Pattern RFC5322_PATTERN = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])");

    @Nonnull
    @JsonCreator
    public static EmailAddress emailAddressOf(@Nonnull String plainValue) throws IllegalArgumentException {
        if (!RFC5322_PATTERN.matcher(plainValue).matches()) {
            throw new IllegalArgumentException("Illegal email address: " + plainValue);
        }
        return new EmailAddress(plainValue);
    }

    protected EmailAddress(@Nonnull String value) {
        super(value);
    }

}
