package com.bronet.blockchain.type;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by 18514 on 2019/7/19.
 */

public class MultipleItem implements MultiItemEntity {
    public static final int TYPE1 = 1;
    public static final int TYPE2 = 2;
    public static final int TYPE3 = 3;
    public static final int TYPE4 = 4;

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    private int itemType;

    public MultipleItem(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
