/*
 * This file is generated by jOOQ.
 */
package io.bricksets.rdbms.tables;


import io.bricksets.rdbms.Keys;
import io.bricksets.rdbms.Sandbox;
import io.bricksets.rdbms.tables.records.TagsRecord;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row3;
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
public class Tags extends TableImpl<TagsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>sandbox.tags</code>
     */
    public static final Tags TAGS = new Tags();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TagsRecord> getRecordType() {
        return TagsRecord.class;
    }

    /**
     * The column <code>sandbox.tags.event_id</code>.
     */
    public final TableField<TagsRecord, UUID> EVENT_ID = createField(DSL.name("event_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>sandbox.tags.tag_class</code>.
     */
    public final TableField<TagsRecord, String> TAG_CLASS = createField(DSL.name("tag_class"), SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * The column <code>sandbox.tags.tag_value</code>.
     */
    public final TableField<TagsRecord, UUID> TAG_VALUE = createField(DSL.name("tag_value"), SQLDataType.UUID.nullable(false), this, "");

    private Tags(Name alias, Table<TagsRecord> aliased) {
        this(alias, aliased, null);
    }

    private Tags(Name alias, Table<TagsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>sandbox.tags</code> table reference
     */
    public Tags(String alias) {
        this(DSL.name(alias), TAGS);
    }

    /**
     * Create an aliased <code>sandbox.tags</code> table reference
     */
    public Tags(Name alias) {
        this(alias, TAGS);
    }

    /**
     * Create a <code>sandbox.tags</code> table reference
     */
    public Tags() {
        this(DSL.name("tags"), null);
    }

    public <O extends Record> Tags(Table<O> child, ForeignKey<O, TagsRecord> key) {
        super(child, key, TAGS);
    }

    @Override
    public Schema getSchema() {
        return Sandbox.SANDBOX;
    }

    @Override
    public UniqueKey<TagsRecord> getPrimaryKey() {
        return Keys.PK_TAG;
    }

    @Override
    public List<UniqueKey<TagsRecord>> getKeys() {
        return Arrays.<UniqueKey<TagsRecord>>asList(Keys.PK_TAG);
    }

    @Override
    public List<ForeignKey<TagsRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<TagsRecord, ?>>asList(Keys.TAGS__FK_TAG_EVENT);
    }

    private transient Events _events;

    public Events events() {
        if (_events == null)
            _events = new Events(this, Keys.TAGS__FK_TAG_EVENT);

        return _events;
    }

    @Override
    public Tags as(String alias) {
        return new Tags(DSL.name(alias), this);
    }

    @Override
    public Tags as(Name alias) {
        return new Tags(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Tags rename(String name) {
        return new Tags(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Tags rename(Name name) {
        return new Tags(name, null);
    }

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row3<UUID, String, UUID> fieldsRow() {
        return (Row3) super.fieldsRow();
    }
}
