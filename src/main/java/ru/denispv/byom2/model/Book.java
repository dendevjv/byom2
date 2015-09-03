package ru.denispv.byom2.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import ru.denispv.byom2.shared.PersistentBase;

@Entity
public class Book extends PersistentBase implements Serializable {
    private static final long serialVersionUID = 1025086356235419730L;
    
    private String title;
    private String description;
    private String source;

    @Pattern(regexp=".*\\S.*", message="cannot be empty")
    @NotNull
    public final String getTitle() {
        return title;
    }

    @Lob
    @Pattern(regexp=".*\\S.*", message="cannot be empty")
    @NotNull
    public final String getDescription() {
        return description;
    }

    @Pattern(regexp=".*\\S.*", message="cannot be empty")
    @NotNull
    public final String getSource() {
        return source;
    }

    public final void setTitle(String title) {
        this.title = title;
    }

    public final void setDescription(String description) {
        this.description = description;
    }

    public final void setSource(String source) {
        this.source = source;
    }
}
