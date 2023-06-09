package be.koder.bricksets.inmemory;

import be.koder.bricksets.domain.brickset.event.BricksetCreated;
import be.koder.bricksets.domain.brickset.event.BricksetModified;
import be.koder.bricksets.domain.brickset.event.BricksetRemoved;
import be.koder.bricksets.domain.event.Event;
import be.koder.bricksets.domain.event.EventProjector;
import be.koder.bricksets.query.brickset.BricksetView;
import be.koder.bricksets.vocabulary.brickset.BricksetListItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class InMemoryBricksetView implements BricksetView, EventProjector {

    private final List<BricksetListItem> items = new ArrayList<>();

    @Override
    public void project(Event event) {
        if (event instanceof BricksetCreated concrete) {
            items.add(new BricksetListItem(
                    concrete.bricksetId(),
                    concrete.number(),
                    concrete.title(),
                    concrete.occurredOn(),
                    concrete.occurredOn()
            ));
            return;
        }
        if (event instanceof BricksetModified concrete) {
            for (int i = 0; i < items.size(); i++) {
                final var old = items.get(i);
                if (old.id().equals(concrete.bricksetId())) {
                    items.set(i, new BricksetListItem(
                            old.id(),
                            old.number(),
                            concrete.title(),
                            old.createdOn(),
                            concrete.occurredOn()
                    ));
                }
                return;
            }
        }
        if (event instanceof BricksetRemoved concrete) {
            for (int i = 0; i < items.size(); i++) {
                final var old = items.get(i);
                if (old.id().equals(concrete.bricksetId())) {
                    items.remove(i);
                }
            }
            return;
        }
        throw new IllegalArgumentException(String.format("Cannot project this type of Event (%s)", event.getClass().getSimpleName()));
    }

    @Override
    public List<BricksetListItem> listBricksets() {
        return Collections.unmodifiableList(items);
    }
}