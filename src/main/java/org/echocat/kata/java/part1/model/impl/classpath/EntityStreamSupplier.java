package org.echocat.kata.java.part1.model.impl.classpath;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;
import org.echocat.repo4j.entity.Entity;

import javax.annotation.Nonnull;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Stream.generate;

@Immutable
@ThreadSafe
public class EntityStreamSupplier<T extends Entity> implements Supplier<Stream<? extends T>> {

    @Nonnull
    protected static final DateTimeFormatter FORMATTER = ofPattern("dd.MM.yyyy");

    @Nonnull
    private final CsvMapper mapper = (CsvMapper) new CsvMapper()
        .registerModule(createJavaTimeModule());

    @Nonnull
    protected SimpleModule createJavaTimeModule() {
        return new JavaTimeModule()
            .addDeserializer(LocalDate.class, new LocalDateDeserializer());
    }

    @Nonnull
    private final URL resourceUrl;
    @Nonnull
    private final Class<T> expectedType;
    @Nonnull
    private final CsvSchema schema;

    public EntityStreamSupplier(
        @Nonnull String resourcePath,
        @Nonnull Class<T> expectedType
    ) {
        this.resourceUrl = requireNonNull(EntityStreamSupplier.class.getClassLoader().getResource(resourcePath), "illegal path");
        this.expectedType = expectedType;

        this.schema = mapper.schemaFor(expectedType)
            .withColumnSeparator(';')
            .withHeader()
            .withColumnReordering(true)
            .withArrayElementSeparator(",")
        ;
    }

    @Override
    @Nonnull
    public Stream<? extends T> get() {
        try {
            boolean success = false;
            final var is = resourceUrl.openStream();
            try {
                final var iterator = mapper
                    .readerFor(expectedType)
                    .with(schema)
                    .readValues(is);
                final var stream = generate(() -> iterator)
                    .takeWhile(MappingIterator::hasNext)
                    .map(MappingIterator::next)
                    .map(expectedType::cast)
                    .onClose(() -> closeQuietly(is));
                success = true;
                return stream;
            } finally {
                if (!success) {
                    closeQuietly(is);
                }
            }
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Nonnull
    protected Optional<T> readValue(@Nonnull ObjectReader reader, @Nonnull InputStream from) {
        try {
            return Optional.of(reader.readValue(from));
        } catch (final EOFException ignored) {
            return Optional.empty();
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    protected static void closeQuietly(@Nonnull AutoCloseable what) {
        try {
            what.close();
        } catch (final Exception ignored) {}
    }

    protected static class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

        @Override
        public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            return LocalDate.parse(p.getValueAsString(), FORMATTER);
        }

    }

}
