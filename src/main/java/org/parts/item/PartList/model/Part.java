package org.parts.item.PartList.model;

import javax.persistence.*;

@Entity
@Table(name = "table_test", schema = "test", catalog = "")
public class Part {

    @Id
    @GeneratedValue
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "required")
    private boolean required;

    public Part() {
    }

    public Part(String title, int quantity, boolean required) {
        this.title = title;
        this.quantity = quantity;
        this.required = required;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean finished) {
        this.required = finished;
    }
}
