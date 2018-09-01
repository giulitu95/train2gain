package com.train2gain.train2gain.adapter.model;

import android.support.annotation.NonNull;

public class RecyclerViewItem<ObjectType> {

    public enum ItemType {
        HEADER,
        STANDARD_SET
    }

    @NonNull private final ItemType type;
    @NonNull private final ObjectType object;

    public RecyclerViewItem(@NonNull ItemType type, @NonNull ObjectType object){
        this.type = type;
        this.object = object;
    }

    // GETTERS

    @NonNull public ItemType getType() {
        return type;
    }

    @NonNull public ObjectType getObject() {
        return object;
    }

}
