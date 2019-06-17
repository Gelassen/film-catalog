package ru.rsprm.storage.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "trucks")
public class TruckEntity {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "price")
    private String price;

    @ColumnInfo(name = "comment")
    private String comment;

    public TruckEntity() {
    }

    public TruckEntity(int id, String name, String price, String comment) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TruckEntity)) return false;

        TruckEntity entity = (TruckEntity) o;

        if (id != entity.id) return false;
        if (name != null ? !name.equals(entity.name) : entity.name != null) return false;
        if (price != null ? !price.equals(entity.price) : entity.price != null) return false;
        return comment != null ? comment.equals(entity.comment) : entity.comment == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }
}
