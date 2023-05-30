package io.bricksets.rdbms.view;

import io.bricksets.query.brickset.BricksetView;
import io.bricksets.rdbms.Tables;
import io.bricksets.vocabulary.brickset.BricksetId;
import io.bricksets.vocabulary.brickset.BricksetListItem;
import io.bricksets.vocabulary.brickset.BricksetNumber;
import io.bricksets.vocabulary.brickset.BricksetTitle;
import io.bricksets.vocabulary.time.Timestamp;
import org.jooq.DSLContext;

import java.util.List;

public final class RdbmsBricksetView implements BricksetView {

    private final DSLContext dsl;

    public RdbmsBricksetView(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public List<BricksetListItem> listBricksets() {
        return dsl.selectFrom(Tables.BRICKSET)
                .orderBy(Tables.BRICKSET.CREATED_ON.desc())
                .fetch()
                .stream()
                .map(it -> new BricksetListItem(
                        BricksetId.fromUuid(it.getId()),
                        BricksetNumber.fromString(it.getNumber()),
                        BricksetTitle.fromString(it.getTitle()),
                        Timestamp.fromLocalDateTime(it.getCreatedOn()),
                        Timestamp.fromLocalDateTime(it.getModifiedOn())
                ))
                .toList();
    }
}