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
    @Column(name = "i_requared")
    private boolean i_requared;

    public Part() {
    }

    public Part(String title, int quantity, boolean i_requared) {
        this.title = title;
        this.quantity = quantity;
        this.i_requared = i_requared;
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

    public boolean isI_requared() {
        return i_requared;
    }

    public void setI_requared(boolean finished) {
        this.i_requared = finished;
    }
}
