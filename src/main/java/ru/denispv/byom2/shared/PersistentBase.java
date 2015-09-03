package ru.denispv.byom2.shared;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class PersistentBase {
    protected Long id;
    
    @Id
    @GeneratedValue
    public Long getId() { return id; }

    /**
     * The mutator is made private to limit how the field is modified.
     * Only the database should change the value of this field.
     * @param id
     */
    @SuppressWarnings("unused")
    private void setId(Long id) { this.id = id; }
    
    public PersistentBase() {
    }
}
