package domain;

import lombok.Data;

@Data(staticConstructor = "of")
public class Todo {
    public final Integer id;
    public final String title;
    public final Boolean completed;
    public final Integer order;
}