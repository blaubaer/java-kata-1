package org.echocat.kata.java.part1.model;

import org.echocat.repo4j.ReadableRepository;

@FunctionalInterface
public interface AuthorRepository extends ReadableRepository<Author, AuthorQuery> {}
