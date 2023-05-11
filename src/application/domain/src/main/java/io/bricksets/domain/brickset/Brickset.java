package io.bricksets.domain.brickset;

import io.bricksets.domain.aggregate.EventSourcedAggregate;
import io.bricksets.domain.event.Event;
import io.bricksets.domain.event.EventStream;
import io.bricksets.domain.time.TimeService;
import io.bricksets.vocabulary.brickset.BricksetId;
import io.bricksets.vocabulary.brickset.BricksetNumber;
import io.bricksets.vocabulary.brickset.BricksetTitle;
import io.bricksets.vocabulary.time.Timestamp;

public final class Brickset extends EventSourcedAggregate {

    private BricksetId id;
    private BricksetNumber number;
    private BricksetTitle title;
    private Timestamp createdAt;
    private Timestamp modifiedAt;

    private Brickset() {
        super();
    }

    public Brickset(EventStream eventStream) {
        super(eventStream);
    }

    @Override
    public BricksetId getId() {
        return id;
    }

    public BricksetNumber getNumber() {
        return number;
    }

    public BricksetTitle getTitle() {
        return title;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getModifiedAt() {
        return modifiedAt;
    }

    public Timestamp getRemovedAt() {
        return removedAt;
    }

    @Override
    protected void when(Event event) {
        if (event instanceof BricksetCreated created) {
            when(created);
            return;
        }
        if (event instanceof BricksetModified modified) {
            when(modified);
            return;
        }
        if (event instanceof BricksetRemoved) {
            return;
        }
        throw new IllegalStateException(String.format("Cannot process event of type %s", event.getClass().getSimpleName()));
    }

    public static Brickset create(BricksetNumber number, BricksetTitle title, TimeService timeService) {
        var brickset = new Brickset();
        brickset.apply(new BricksetCreated(timeService.now(), BricksetId.createNew(), number, title));
        return brickset;
    }

    public void modify(BricksetTitle title, TimeService timeService) {
        apply(new BricksetModified(timeService.now(), id, title));
    }

    public void remove(TimeService timeService) {
        apply(new BricksetRemoved(timeService.now(), id, number));
    }

    private void when(BricksetCreated created) {
        this.id = created.bricksetId();
        this.number = created.number();
        this.title = created.title();
        this.createdAt = created.timestamp();
    }

    private void when(BricksetModified modified) {
        this.title = modified.title();
        this.modifiedAt = modified.timestamp();
    }
}