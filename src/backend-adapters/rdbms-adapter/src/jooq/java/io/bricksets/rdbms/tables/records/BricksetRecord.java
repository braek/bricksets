/*
 * This file is generated by jOOQ.
 */
package io.bricksets.rdbms.tables.records;


import io.bricksets.rdbms.tables.Brickset;

import java.time.LocalDateTime;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class BricksetRecord extends UpdatableRecordImpl<BricksetRecord> implements Record5<UUID, String, String, LocalDateTime, LocalDateTime> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>sandbox.brickset.id</code>.
     */
    public BricksetRecord setId(UUID value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>sandbox.brickset.id</code>.
     */
    public UUID getId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>sandbox.brickset.number</code>.
     */
    public BricksetRecord setNumber(String value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>sandbox.brickset.number</code>.
     */
    public String getNumber() {
        return (String) get(1);
    }

    /**
     * Setter for <code>sandbox.brickset.title</code>.
     */
    public BricksetRecord setTitle(String value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>sandbox.brickset.title</code>.
     */
    public String getTitle() {
        return (String) get(2);
    }

    /**
     * Setter for <code>sandbox.brickset.created_on</code>.
     */
    public BricksetRecord setCreatedOn(LocalDateTime value) {
        set(3, value);
        return this;
    }

    /**
     * Getter for <code>sandbox.brickset.created_on</code>.
     */
    public LocalDateTime getCreatedOn() {
        return (LocalDateTime) get(3);
    }

    /**
     * Setter for <code>sandbox.brickset.modified_on</code>.
     */
    public BricksetRecord setModifiedOn(LocalDateTime value) {
        set(4, value);
        return this;
    }

    /**
     * Getter for <code>sandbox.brickset.modified_on</code>.
     */
    public LocalDateTime getModifiedOn() {
        return (LocalDateTime) get(4);
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
    public Row5<UUID, String, String, LocalDateTime, LocalDateTime> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    @Override
    public Row5<UUID, String, String, LocalDateTime, LocalDateTime> valuesRow() {
        return (Row5) super.valuesRow();
    }

    @Override
    public Field<UUID> field1() {
        return Brickset.BRICKSET.ID;
    }

    @Override
    public Field<String> field2() {
        return Brickset.BRICKSET.NUMBER;
    }

    @Override
    public Field<String> field3() {
        return Brickset.BRICKSET.TITLE;
    }

    @Override
    public Field<LocalDateTime> field4() {
        return Brickset.BRICKSET.CREATED_ON;
    }

    @Override
    public Field<LocalDateTime> field5() {
        return Brickset.BRICKSET.MODIFIED_ON;
    }

    @Override
    public UUID component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getNumber();
    }

    @Override
    public String component3() {
        return getTitle();
    }

    @Override
    public LocalDateTime component4() {
        return getCreatedOn();
    }

    @Override
    public LocalDateTime component5() {
        return getModifiedOn();
    }

    @Override
    public UUID value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getNumber();
    }

    @Override
    public String value3() {
        return getTitle();
    }

    @Override
    public LocalDateTime value4() {
        return getCreatedOn();
    }

    @Override
    public LocalDateTime value5() {
        return getModifiedOn();
    }

    @Override
    public BricksetRecord value1(UUID value) {
        setId(value);
        return this;
    }

    @Override
    public BricksetRecord value2(String value) {
        setNumber(value);
        return this;
    }

    @Override
    public BricksetRecord value3(String value) {
        setTitle(value);
        return this;
    }

    @Override
    public BricksetRecord value4(LocalDateTime value) {
        setCreatedOn(value);
        return this;
    }

    @Override
    public BricksetRecord value5(LocalDateTime value) {
        setModifiedOn(value);
        return this;
    }

    @Override
    public BricksetRecord values(UUID value1, String value2, String value3, LocalDateTime value4, LocalDateTime value5) {
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
     * Create a detached BricksetRecord
     */
    public BricksetRecord() {
        super(Brickset.BRICKSET);
    }

    /**
     * Create a detached, initialised BricksetRecord
     */
    public BricksetRecord(UUID id, String number, String title, LocalDateTime createdOn, LocalDateTime modifiedOn) {
        super(Brickset.BRICKSET);

        setId(id);
        setNumber(number);
        setTitle(title);
        setCreatedOn(createdOn);
        setModifiedOn(modifiedOn);
    }
}
