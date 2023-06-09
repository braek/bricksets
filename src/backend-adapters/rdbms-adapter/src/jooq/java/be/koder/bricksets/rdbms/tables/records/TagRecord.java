/*
 * This file is generated by jOOQ.
 */
package be.koder.bricksets.rdbms.tables.records;


import be.koder.bricksets.rdbms.tables.Tag;

import java.util.UUID;

import org.jooq.Field;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TagRecord extends UpdatableRecordImpl<TagRecord> implements Record3<UUID, String, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>sandbox.tag.event_id</code>.
     */
    public TagRecord setEventId(UUID value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>sandbox.tag.event_id</code>.
     */
    public UUID getEventId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>sandbox.tag.type</code>.
     */
    public TagRecord setType(String value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>sandbox.tag.type</code>.
     */
    public String getType() {
        return (String) get(1);
    }

    /**
     * Setter for <code>sandbox.tag.value</code>.
     */
    public TagRecord setValue(String value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>sandbox.tag.value</code>.
     */
    public String getValue() {
        return (String) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record3<UUID, String, String> key() {
        return (Record3) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row3<UUID, String, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    @Override
    public Row3<UUID, String, String> valuesRow() {
        return (Row3) super.valuesRow();
    }

    @Override
    public Field<UUID> field1() {
        return Tag.TAG.EVENT_ID;
    }

    @Override
    public Field<String> field2() {
        return Tag.TAG.TYPE;
    }

    @Override
    public Field<String> field3() {
        return Tag.TAG.VALUE;
    }

    @Override
    public UUID component1() {
        return getEventId();
    }

    @Override
    public String component2() {
        return getType();
    }

    @Override
    public String component3() {
        return getValue();
    }

    @Override
    public UUID value1() {
        return getEventId();
    }

    @Override
    public String value2() {
        return getType();
    }

    @Override
    public String value3() {
        return getValue();
    }

    @Override
    public TagRecord value1(UUID value) {
        setEventId(value);
        return this;
    }

    @Override
    public TagRecord value2(String value) {
        setType(value);
        return this;
    }

    @Override
    public TagRecord value3(String value) {
        setValue(value);
        return this;
    }

    @Override
    public TagRecord values(UUID value1, String value2, String value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TagRecord
     */
    public TagRecord() {
        super(Tag.TAG);
    }

    /**
     * Create a detached, initialised TagRecord
     */
    public TagRecord(UUID eventId, String type, String value) {
        super(Tag.TAG);

        setEventId(eventId);
        setType(type);
        setValue(value);
    }
}
