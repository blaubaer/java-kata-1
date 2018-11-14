package org.echocat.kata.java.part1.model.impl.classpath;

import net.jcip.annotations.ThreadSafe;
import org.echocat.kata.java.part1.model.Author;
import org.echocat.kata.java.part1.model.AuthorQuery;
import org.echocat.kata.java.part1.model.AuthorRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.function.Supplier;
import java.util.stream.Stream;

@ThreadSafe
@Component
public class ClasspathBasedAuthorRepository implements AuthorRepository {

    @Nonnull
    private final Supplier<Stream<? extends Author>> supplier = new EntityStreamSupplier<>(
        "org/echocat/kata/java/part1/data/authors.csv", Author.class
    );

    @Nonnull
    @Override
    public Stream<? extends Author> findBy(@Nonnull AuthorQuery query) {
        return supplier.get()
            .filter(query);
    }

}
