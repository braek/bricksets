# Tables

## Table: Event

* id: UUID
* timestamp: TIMESTAMP
* position: BIGINT
* event_class: VARCHAR
* event_value: JSONB

## Table: Tag

* event_id: UUID
* tag_class: VARCHAR
* tag_value: UUID

```sql
SELECT *
FROM event
WHERE id IN (SELECT event_id FROM tag WHERE tag_class = ? AND tag_value = ?)
ORDER BY position ASC;
```