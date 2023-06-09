/*
 * This file is generated by jOOQ.
 */
package be.koder.bricksets.rdbms.tables;


import be.koder.bricksets.rdbms.Keys;
import be.koder.bricksets.rdbms.Sandbox;
import be.koder.bricksets.rdbms.tables.records.BricksetViewRecord;

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
public class BricksetView extends TableImpl<BricksetViewRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>sandbox.brickset_view</code>
     */
    public static final BricksetView BRICKSET_VIEW = new BricksetView();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<BricksetViewRecord> getRecordType() {
        return BricksetViewRecord.class;
    }

    /**
     * The column <code>sandbox.brickset_view.id</code>.
     */
    public final TableField<BricksetViewRecord, UUID> ID = createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>sandbox.brickset_view.number</code>.
     */
    public final TableField<BricksetViewRecord, String> NUMBER = createField(DSL.name("number"), SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * The column <code>sandbox.brickset_view.title</code>.
     */
    public final TableField<BricksetViewRecord, String> TITLE = createField(DSL.name("title"), SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * The column <code>sandbox.brickset_view.created_on</code>.
     */
    public final TableField<BricksetViewRecord, LocalDateTime> CREATED_ON = createField(DSL.name("created_on"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "");

    /**
     * The column <code>sandbox.brickset_view.modified_on</code>.
     */
    public final TableField<BricksetViewRecord, LocalDateTime> MODIFIED_ON = createField(DSL.name("modified_on"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "");

    private BricksetView(Name alias, Table<BricksetViewRecord> aliased) {
        this(alias, aliased, null);
    }

    private BricksetView(Name alias, Table<BricksetViewRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>sandbox.brickset_view</code> table reference
     */
    public BricksetView(String alias) {
        this(DSL.name(alias), BRICKSET_VIEW);
    }

    /**
     * Create an aliased <code>sandbox.brickset_view</code> table reference
     */
    public BricksetView(Name alias) {
        this(alias, BRICKSET_VIEW);
    }

    /**
     * Create a <code>sandbox.brickset_view</code> table reference
     */
    public BricksetView() {
        this(DSL.name("brickset_view"), null);
    }

    public <O extends Record> BricksetView(Table<O> child, ForeignKey<O, BricksetViewRecord> key) {
        super(child, key, BRICKSET_VIEW);
    }

    @Override
    public Schema getSchema() {
        return Sandbox.SANDBOX;
    }

    @Override
    public UniqueKey<BricksetViewRecord> getPrimaryKey() {
        return Keys.PK_BRICKSET;
    }

    @Override
    public List<UniqueKey<BricksetViewRecord>> getKeys() {
        return Arrays.<UniqueKey<BricksetViewRecord>>asList(Keys.PK_BRICKSET, Keys.UNIQUE_NUMBER);
    }

    @Override
    public BricksetView as(String alias) {
        return new BricksetView(DSL.name(alias), this);
    }

    @Override
    public BricksetView as(Name alias) {
        return new BricksetView(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public BricksetView rename(String name) {
        return new BricksetView(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public BricksetView rename(Name name) {
        return new BricksetView(name, null);
    }

    // -------------------------------------------------------------------------
    // Row5 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row5<UUID, String, String, LocalDateTime, LocalDateTime> fieldsRow() {
        return (Row5) super.fieldsRow();
    }
}
