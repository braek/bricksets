/*
 * This file is generated by jOOQ.
 */
package be.koder.bricksets.rdbms.tables.records;


import be.koder.bricksets.rdbms.tables.Event;

import java.time.LocalDateTime;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.JSONB;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class EventRecord extends UpdatableRecordImpl<EventRecord> implements Record5<UUID, Long, LocalDateTime, String, JSONB> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>sandbox.event.id</code>.
     */
    public EventRecord setId(UUID value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>sandbox.event.id</code>.
     */
    public UUID getId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>sandbox.event.position</code>.
     */
    public EventRecord setPosition(Long value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>sandbox.event.position</code>.
     */
    public Long getPosition() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>sandbox.event.occurred_on</code>.
     */
    public EventRecord setOccurredOn(LocalDateTime value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>sandbox.event.occurred_on</code>.
     */
    public LocalDateTime getOccurredOn() {
        return (LocalDateTime) get(2);
    }

    /**
     * Setter for <code>sandbox.event.type</code>.
     */
    public EventRecord setType(String value) {
        set(3, value);
        return this;
    }

    /**
     * Getter for <code>sandbox.event.type</code>.
     */
    public String getType() {
        return (String) get(3);
    }

    /**
     * Setter for <code>sandbox.event.content</code>.
     */
    public EventRecord setContent(JSONB value) {
        set(4, value);
        return this;
    }

    /**
     * Getter for <code>sandbox.event.content</code>.
     */
    public JSONB getContent() {
        return (JSONB) get(4);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<UUID> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row5<UUID, Long, LocalDateTime, String, JSONB> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    @Override
    public Row5<UUID, Long, LocalDateTime, String, JSONB> valuesRow() {
        return (Row5) super.valuesRow();
    }

    @Override
    public Field<UUID> field1() {
        return Event.EVENT.ID;
    }

    @Override
    public Field<Long> field2() {
        return Event.EVENT.POSITION;
    }

    @Override
    public Field<LocalDateTime> field3() {
        return Event.EVENT.OCCURRED_ON;
    }

    @Override
    public Field<String> field4() {
        return Event.EVENT.TYPE;
    }

    @Override
    public Field<JSONB> field5() {
        return Event.EVENT.CONTENT;
    }

    @Override
    public UUID component1() {
        return getId();
    }

    @Override
    public Long component2() {
        return getPosition();
    }

    @Override
    public LocalDateTime component3() {
        return getOccurredOn();
    }

    @Override
    public String component4() {
        return getType();
    }

    @Override
    public JSONB component5() {
        return getContent();
    }

    @Override
    public UUID value1() {
        return getId();
    }

    @Override
    public Long value2() {
        return getPosition();
    }

    @Override
    public LocalDateTime value3() {
        return getOccurredOn();
    }

    @Override
    public String value4() {
        return getType();
    }

    @Override
    public JSONB value5() {
        return getContent();
    }

    @Override
    public EventRecord value1(UUID value) {
        setId(value);
        return this;
    }

    @Override
    public EventRecord value2(Long value) {
        setPosition(value);
        return this;
    }

    @Override
    public EventRecord value3(LocalDateTime value) {
        setOccurredOn(value);
        return this;
    }

    @Override
    public EventRecord value4(String value) {
        setType(value);
        return this;
    }

    @Override
    public EventRecord value5(JSONB value) {
        setContent(value);
        return this;
    }

    @Override
    public EventRecord values(UUID value1, Long value2, LocalDateTime value3, String value4, JSONB value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached EventRecord
     */
    public EventRecord() {
        super(Event.EVENT);
    }

    /**
     * Create a detached, initialised EventRecord
     */
    public EventRecord(UUID id, Long position, LocalDateTime occurredOn, String type, JSONB content) {
        super(Event.EVENT);

        setId(id);
        setPosition(position);
        setOccurredOn(occurredOn);
        setType(type);
        setContent(content);
    }
}
