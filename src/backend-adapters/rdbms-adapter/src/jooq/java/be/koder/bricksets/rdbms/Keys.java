/*
 * This file is generated by jOOQ.
 */
package be.koder.bricksets.rdbms;


import be.koder.bricksets.rdbms.tables.Brickset;
import be.koder.bricksets.rdbms.tables.Event;
import be.koder.bricksets.rdbms.tables.FlywaySchemaHistory;
import be.koder.bricksets.rdbms.tables.Tag;
import be.koder.bricksets.rdbms.tables.records.BricksetRecord;
import be.koder.bricksets.rdbms.tables.records.EventRecord;
import be.koder.bricksets.rdbms.tables.records.FlywaySchemaHistoryRecord;
import be.koder.bricksets.rdbms.tables.records.TagRecord;

import org.jooq.ForeignKey;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables in 
 * sandbox.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<BricksetRecord> PK_BRICKSET = Internal.createUniqueKey(Brickset.BRICKSET, DSL.name("pk_brickset"), new TableField[] { Brickset.BRICKSET.ID }, true);
    public static final UniqueKey<BricksetRecord> UC_NUMBER = Internal.createUniqueKey(Brickset.BRICKSET, DSL.name("uc_number"), new TableField[] { Brickset.BRICKSET.NUMBER }, true);
    public static final UniqueKey<EventRecord> PK_EVENT = Internal.createUniqueKey(Event.EVENT, DSL.name("pk_event"), new TableField[] { Event.EVENT.ID }, true);
    public static final UniqueKey<EventRecord> UNIQUE_POSITION = Internal.createUniqueKey(Event.EVENT, DSL.name("unique_position"), new TableField[] { Event.EVENT.POSITION }, true);
    public static final UniqueKey<FlywaySchemaHistoryRecord> FLYWAY_SCHEMA_HISTORY_PK = Internal.createUniqueKey(FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY, DSL.name("flyway_schema_history_pk"), new TableField[] { FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY.INSTALLED_RANK }, true);
    public static final UniqueKey<TagRecord> PK_TAG = Internal.createUniqueKey(Tag.TAG, DSL.name("pk_tag"), new TableField[] { Tag.TAG.EVENT_ID, Tag.TAG.CLAZZ, Tag.TAG.VALUE }, true);

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<TagRecord, EventRecord> TAG__FK_TAG_EVENT = Internal.createForeignKey(Tag.TAG, DSL.name("fk_tag_event"), new TableField[] { Tag.TAG.EVENT_ID }, Keys.PK_EVENT, new TableField[] { Event.EVENT.ID }, true);
}
