package ru.denispv.byom2.model;

public class Book {
    private static final String DEFAULT_SOURCE = "No source";
    private static final String DEFAULT_DESCRIPTION = "No description";
    private static final String DEFAULT_TITLE = "No title";
    private String title;
    private String description;
    private String source;

    public final String getTitle() {
        if (isValidTitle()) {
            return title;
        } else {
            return DEFAULT_TITLE;
        }
    }

    public final String getDescription() {
        if (isValidDescription()) {
            return description;
        } else {
            return DEFAULT_DESCRIPTION;
        }
    }

    public final String getSource() {
        if (isValidSource()) {
            return source;
        } else {
            return DEFAULT_SOURCE;
        }
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

    private boolean isValidTitle() {
        return title != null && !title.trim().isEmpty();
    }
    
    private boolean isValidDescription() {
        return description != null && !description.trim().isEmpty();
    }
    
    private boolean isValidSource() {
        return source != null && !source.trim().isEmpty();
    }
}
