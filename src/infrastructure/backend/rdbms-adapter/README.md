# Tables

## Table: Event

* id
* timestamp
* position
* event_class
* event_value

## Table: Tag

* event_id
* tag_class
* tag_value

```sql
SELECT
    *
FROM 
    event
WHERE
    id IN (SELECT event_id FROM tag WHERE tag_class = ? AND tag_value = ?)
ORDER BY
    position ASC;
```