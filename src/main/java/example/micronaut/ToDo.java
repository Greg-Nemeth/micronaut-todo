package example.micronaut;

import javax.validation.constraints.NotNull;

import io.micronaut.data.annotation.GeneratedValue;
import static io.micronaut.data.annotation.GeneratedValue.Type.AUTO;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;

@MappedEntity
public class ToDo {

    @Id
    @GeneratedValue(AUTO)
    private Long Id;
    
    @NotNull
    private String title;

    private boolean completed;

    private final long authorId;

    public ToDo(String title, long authorId) {
        this.title = title;
        this.authorId = authorId;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Long getAuthorId() {
        return authorId;
    }
}


