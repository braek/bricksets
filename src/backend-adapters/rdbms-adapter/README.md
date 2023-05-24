# Tables

## Table: Event

* id: ```UUID```
* occurred_on: ```TIMESTAMP WITHOUT TIMEZONE```
* position: ```BIGINT```
* event_class: ```VARCHAR(100)```
* event_value: ```JSONB```

## Table: Tag

* event_id: ```UUID```
* tag_class: ```VARCHAR(100)```
* tag_value: ```VARCHAR(100)``` or ```UUID``` (?)

```sql
SELECT *
FROM event
WHERE id IN (SELECT event_id FROM tag WHERE tag_class = ? AND tag_value = ?)
ORDER BY position ASC;
```