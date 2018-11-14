package org.echocat.kata.java.part1.model;

import org.echocat.repo4j.ReadableRepository;

@FunctionalInterface
public interface PublicationRepository extends ReadableRepository<Publication, PublicationQuery> {}
