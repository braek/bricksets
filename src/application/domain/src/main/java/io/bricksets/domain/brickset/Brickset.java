package io.bricksets.domain.brickset;

import io.bricksets.domain.aggregate.EventSourcedAggregate;
import io.bricksets.domain.event.Event;
import io.bricksets.domain.event.EventStream;
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
    private Timestamp removedAt;

    private Brickset() {
        super();
    }

    public Brickset(EventStream eventStream) {
        super(eventStream);
    }

    @Override
    protected void when(Event event) {
        if (event instanceof BricksetCreated bricksetCreated) {
            when(bricksetCreated);
            return;
        }
        if (event instanceof BricksetModified bricksetModified) {
            when(bricksetModified);
            return;
        }
        if (event instanceof BricksetRemoved bricksetRemoved) {
            when(bricksetRemoved);
            return;
        }
        throw new IllegalStateException(String.format("Cannot process event of type %s", event.getClass().getSimpleName()));
    }

    public static Brickset create(BricksetNumber number, BricksetTitle title) {
        var event = new BricksetCreated(BricksetId.createNew(), number, title);
        var brickset = new Brickset();
        brickset.apply(event);
        return brickset;
    }

    public void modify(BricksetTitle title) {
        apply(new BricksetModified(id, title));
    }

    public void remove() {
        apply(new BricksetRemoved(id));
    }

    private void when(BricksetCreated bricksetCreated) {
        this.id = bricksetCreated.bricksetId();
        this.number = bricksetCreated.number();
        this.title = bricksetCreated.title();
        this.createdAt = bricksetCreated.timestamp();
        this.modifiedAt = bricksetCreated.timestamp();
    }

    private void when(BricksetModified bricksetModified) {
        this.title = bricksetModified.title();
        this.modifiedAt = bricksetModified.timestamp();
    }

    private void when(BricksetRemoved bricksetRemoved) {
        this.removedAt = bricksetRemoved.timestamp();
    }
}