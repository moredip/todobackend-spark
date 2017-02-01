package domain;

import lombok.Data;

@Data(staticConstructor = "of")
public class Todo {
    final private Integer id;
    final private String title;
    final private Boolean completed;
    final private Integer order;
}