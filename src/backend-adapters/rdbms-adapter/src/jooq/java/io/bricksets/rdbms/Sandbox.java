/*
 * This file is generated by jOOQ.
 */
package io.bricksets.rdbms;


import io.bricksets.rdbms.tables.Events;
import io.bricksets.rdbms.tables.FlywaySchemaHistory;
import io.bricksets.rdbms.tables.Tags;

import java.util.Arrays;
import java.util.List;

import org.jooq.Catalog;
import org.jooq.Sequence;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Sandbox extends SchemaImpl {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>sandbox</code>
     */
    public static final Sandbox SANDBOX = new Sandbox();

    /**
     * The table <code>sandbox.events</code>.
     */
    public final Events EVENTS = Events.EVENTS;

    /**
     * The table <code>sandbox.flyway_schema_history</code>.
     */
    public final FlywaySchemaHistory FLYWAY_SCHEMA_HISTORY = FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY;

    /**
     * The table <code>sandbox.tags</code>.
     */
    public final Tags TAGS = Tags.TAGS;

    /**
     * No further instances allowed
     */
    private Sandbox() {
        super("sandbox", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Sequence<?>> getSequences() {
        return Arrays.<Sequence<?>>asList(
            Sequences.EVENTS_POSITION_SEQ);
    }

    @Override
    public final List<Table<?>> getTables() {
        return Arrays.<Table<?>>asList(
            Events.EVENTS,
            FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY,
            Tags.TAGS);
    }
}
