package com.codepath.mypizza.entity;

import java.io.Serializable;

public class Item implements Serializable {
    private long id;
    private String name;
    private float price;
    private boolean selected;
    private String pathToPhoto;

    public Item() {
    }

    public Item(String name, float price, boolean selected, String pathToPhoto) {
        this.name = name;
        this.price = price;
        this.selected = selected;
        this.pathToPhoto = pathToPhoto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getPathToPhoto() {
        return pathToPhoto;
    }

    public void setPathToPhoto(String pathToPhoto) {
        this.pathToPhoto = pathToPhoto;
    }

    public boolean getSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (id != item.id) return false;
        if (Float.compare(item.price, price) != 0) return false;
        if (selected != item.selected) return false;
        if (!name.equals(item.name)) return false;
        return pathToPhoto.equals(item.pathToPhoto);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + name.hashCode();
        result = 31 * result + (price != +0.0f ? Float.floatToIntBits(price) : 0);
        result = 31 * result + (selected ? 1 : 0);
        result = 31 * result + pathToPhoto.hashCode();
        return result;
    }
}
