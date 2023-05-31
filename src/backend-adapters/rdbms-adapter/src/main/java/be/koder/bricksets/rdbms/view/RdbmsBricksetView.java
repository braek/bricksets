package be.koder.bricksets.rdbms.view;

import be.koder.bricksets.query.brickset.BricksetView;
import be.koder.bricksets.vocabulary.brickset.BricksetId;
import be.koder.bricksets.vocabulary.brickset.BricksetListItem;
import be.koder.bricksets.vocabulary.brickset.BricksetNumber;
import be.koder.bricksets.vocabulary.brickset.BricksetTitle;
import be.koder.bricksets.vocabulary.time.Timestamp;
import org.jooq.DSLContext;

import java.util.List;

import static be.koder.bricksets.rdbms.Tables.BRICKSET_VIEW;

public final class RdbmsBricksetView implements BricksetView {

    private final DSLContext dsl;

    public RdbmsBricksetView(final DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public List<BricksetListItem> listBricksets() {
        return dsl.selectFrom(BRICKSET_VIEW)
                .orderBy(BRICKSET_VIEW.CREATED_ON.desc())
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