package org.echocat.kata.java.part1.model.impl.classpath;

import net.jcip.annotations.ThreadSafe;
import org.echocat.kata.java.part1.model.*;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.function.Supplier;
import java.util.stream.Stream;

@ThreadSafe
@Component
public class ClasspathBasedPublicationRepository implements PublicationRepository {

    @Nonnull
    private final Supplier<Stream<? extends Book>> bookSupplier = new EntityStreamSupplier<>(
        "org/echocat/kata/java/part1/data/books.csv", Book.class
    );
    @Nonnull
    private final Supplier<Stream<? extends Magazine>> magazineSupplier = new EntityStreamSupplier<>(
        "org/echocat/kata/java/part1/data/magazines.csv", Magazine.class
    );

    @Nonnull
    @Override
    public Stream<? extends Publication> findBy(@Nonnull PublicationQuery query) {
        return Stream.concat(
            bookSupplier.get(),
            magazineSupplier.get()
        )
            .filter(query);
    }

}
