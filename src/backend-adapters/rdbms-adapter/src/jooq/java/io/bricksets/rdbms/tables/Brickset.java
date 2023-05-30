/*
 * This file is generated by jOOQ.
 */
package io.bricksets.rdbms.tables;


import io.bricksets.rdbms.Keys;
import io.bricksets.rdbms.Sandbox;
import io.bricksets.rdbms.tables.records.BricksetRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.ForeignKey;
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
public class Brickset extends TableImpl<BricksetRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>sandbox.brickset</code>
     */
    public static final Brickset BRICKSET = new Brickset();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<BricksetRecord> getRecordType() {
        return BricksetRecord.class;
    }

    /**
     * The column <code>sandbox.brickset.id</code>.
     */
    public final TableField<BricksetRecord, UUID> ID = createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>sandbox.brickset.number</code>.
     */
    public final TableField<BricksetRecord, String> NUMBER = createField(DSL.name("number"), SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * The column <code>sandbox.brickset.title</code>.
     */
    public final TableField<BricksetRecord, String> TITLE = createField(DSL.name("title"), SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * The column <code>sandbox.brickset.created_on</code>.
     */
    public final TableField<BricksetRecord, LocalDateTime> CREATED_ON = createField(DSL.name("created_on"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "");

    /**
     * The column <code>sandbox.brickset.modified_on</code>.
     */
    public final TableField<BricksetRecord, LocalDateTime> MODIFIED_ON = createField(DSL.name("modified_on"), SQLDataType.LOCALDATETIME(6), this, "");

    private Brickset(Name alias, Table<BricksetRecord> aliased) {
        this(alias, aliased, null);
    }

    private Brickset(Name alias, Table<BricksetRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>sandbox.brickset</code> table reference
     */
    public Brickset(String alias) {
        this(DSL.name(alias), BRICKSET);
    }

    /**
     * Create an aliased <code>sandbox.brickset</code> table reference
     */
    public Brickset(Name alias) {
        this(alias, BRICKSET);
    }

    /**
     * Create a <code>sandbox.brickset</code> table reference
     */
    public Brickset() {
        this(DSL.name("brickset"), null);
    }

    public <O extends Record> Brickset(Table<O> child, ForeignKey<O, BricksetRecord> key) {
        super(child, key, BRICKSET);
    }

    @Override
    public Schema getSchema() {
        return Sandbox.SANDBOX;
    }

    @Override
    public UniqueKey<BricksetRecord> getPrimaryKey() {
        return Keys.PK_BRICKSET;
    }

    @Override
    public List<UniqueKey<BricksetRecord>> getKeys() {
        return Arrays.<UniqueKey<BricksetRecord>>asList(Keys.PK_BRICKSET, Keys.UC_NUMBER);
    }

    @Override
    public Brickset as(String alias) {
        return new Brickset(DSL.name(alias), this);
    }

    @Override
    public Brickset as(Name alias) {
        return new Brickset(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Brickset rename(String name) {
        return new Brickset(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Brickset rename(Name name) {
        return new Brickset(name, null);
    }

    // -------------------------------------------------------------------------
    // Row5 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row5<UUID, String, String, LocalDateTime, LocalDateTime> fieldsRow() {
        return (Row5) super.fieldsRow();
    }
}
