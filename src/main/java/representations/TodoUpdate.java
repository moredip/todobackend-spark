package representations;

import lombok.Data;

@Data
public class TodoUpdate {
    final private String title;
    final private Integer order;
    final private boolean completed;
}