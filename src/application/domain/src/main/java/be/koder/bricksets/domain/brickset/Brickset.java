package be.koder.bricksets.domain.brickset;

import be.koder.bricksets.domain.aggregate.EventSourcedAggregate;
import be.koder.bricksets.domain.brickset.event.BricksetCreated;
import be.koder.bricksets.domain.brickset.event.BricksetModified;
import be.koder.bricksets.domain.brickset.event.BricksetRemoved;
import be.koder.bricksets.domain.event.Event;
import be.koder.bricksets.domain.event.EventStream;
import be.koder.bricksets.domain.time.TimeService;
import be.koder.bricksets.vocabulary.brickset.BricksetId;
import be.koder.bricksets.vocabulary.brickset.BricksetNumber;
import be.koder.bricksets.vocabulary.brickset.BricksetTitle;
import be.koder.bricksets.vocabulary.time.Timestamp;

import static java.util.Objects.requireNonNull;

public final class Brickset extends EventSourcedAggregate {

    private BricksetId id;
    private BricksetNumber number;
    private BricksetTitle title;
    private Timestamp createdOn;
    private Timestamp modifiedOn;

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

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public Timestamp getModifiedOn() {
        return modifiedOn;
    }

    @Override
    protected void dispatch(Event event) {
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
        requireNonNull(number);
        requireNonNull(title);
        var brickset = new Brickset();
        brickset.apply(new BricksetCreated(timeService.now(), BricksetId.createNew(), number, title));
        return brickset;
    }

    public void modify(BricksetTitle title, TimeService timeService) {
        requireNonNull(title);
        apply(new BricksetModified(timeService.now(), id, title));
    }

    public void remove(TimeService timeService) {
        apply(new BricksetRemoved(timeService.now(), id));
    }

    private void when(BricksetCreated created) {
        this.id = created.bricksetId();
        this.number = created.number();
        this.title = created.title();
        this.createdOn = created.occurredOn();
        this.modifiedOn = created.occurredOn();
    }

    private void when(BricksetModified modified) {
        this.title = modified.title();
        this.modifiedOn = modified.occurredOn();
    }
}