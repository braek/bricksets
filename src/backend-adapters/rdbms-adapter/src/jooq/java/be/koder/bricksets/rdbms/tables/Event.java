/*
 * This file is generated by jOOQ.
 */
package be.koder.bricksets.rdbms.tables;


import be.koder.bricksets.rdbms.Keys;
import be.koder.bricksets.rdbms.Sandbox;
import be.koder.bricksets.rdbms.tables.records.EventRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.JSONB;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row5;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Event extends TableImpl<EventRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>sandbox.event</code>
     */
    public static final Event EVENT = new Event();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<EventRecord> getRecordType() {
        return EventRecord.class;
    }

    /**
     * The column <code>sandbox.event.id</code>.
     */
    public final TableField<EventRecord, UUID> ID = createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>sandbox.event.position</code>.
     */
    public final TableField<EventRecord, Long> POSITION = createField(DSL.name("position"), SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>sandbox.event.occurred_on</code>.
     */
    public final TableField<EventRecord, LocalDateTime> OCCURRED_ON = createField(DSL.name("occurred_on"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "");

    /**
     * The column <code>sandbox.event.type</code>.
     */
    public final TableField<EventRecord, String> TYPE = createField(DSL.name("type"), SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * The column <code>sandbox.event.content</code>.
     */
    public final TableField<EventRecord, JSONB> CONTENT = createField(DSL.name("content"), SQLDataType.JSONB.nullable(false), this, "");

    private Event(Name alias, Table<EventRecord> aliased) {
        this(alias, aliased, null);
    }

    private Event(Name alias, Table<EventRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>sandbox.event</code> table reference
     */
    public Event(String alias) {
        this(DSL.name(alias), EVENT);
    }

    /**
     * Create an aliased <code>sandbox.event</code> table reference
     */
    public Event(Name alias) {
        this(alias, EVENT);
    }

    /**
     * Create a <code>sandbox.event</code> table reference
     */
    public Event() {
        this(DSL.name("event"), null);
    }

    public <O extends Record> Event(Table<O> child, ForeignKey<O, EventRecord> key) {
        super(child, key, EVENT);
    }

    @Override
    public Schema getSchema() {
        return Sandbox.SANDBOX;
    }

    @Override
    public Identity<EventRecord, Long> getIdentity() {
        return (Identity<EventRecord, Long>) super.getIdentity();
    }

    @Override
    public UniqueKey<EventRecord> getPrimaryKey() {
        return Keys.PK_EVENT;
    }

    @Override
    public List<UniqueKey<EventRecord>> getKeys() {
        return Arrays.<UniqueKey<EventRecord>>asList(Keys.PK_EVENT, Keys.UNIQUE_POSITION);
    }

    @Override
    public Event as(String alias) {
        return new Event(DSL.name(alias), this);
    }

    @Override
    public Event as(Name alias) {
        return new Event(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Event rename(String name) {
        return new Event(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Event rename(Name name) {
        return new Event(name, null);
    }

    // -------------------------------------------------------------------------
    // Row5 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row5<UUID, Long, LocalDateTime, String, JSONB> fieldsRow() {
        return (Row5) super.fieldsRow();
    }
}
