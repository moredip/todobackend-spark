package db;

import lombok.Data;

@Data
public class TodoRecord {
    final private int id;
    final private String title;
    final private Boolean completed;
    final private Integer item_order;
}
