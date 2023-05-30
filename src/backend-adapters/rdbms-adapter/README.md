# Tables

## Table: Event

* id: ```UUID```
* occurred_on: ```TIMESTAMP WITHOUT TIMEZONE```
* position: ```BIGINT```
* clazz: ```VARCHAR```
* content: ```JSONB```

## Table: Tag

* event_id: ```UUID```
* clazz: ```VARCHAR```
* value: ```VARCHAR```

```sql
SELECT *
FROM event
WHERE id IN (SELECT event_id FROM tag WHERE clazz = ? AND value = ?)
ORDER BY position ASC;
```