package ru.denispv.byom2.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class Book {
    private String title;
    private String description;
    private String source;

    @Pattern(regexp=".*\\S.*", message="cannot be empty")
    @NotNull
    public final String getTitle() {
        return title;
    }

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
