package com.pyg.vo;

import com.pyg.pojo.*;

import java.io.Serializable;
import java.util.List;

public class Goods implements Serializable {

    //规格
    private TbGoods goods;
    private TbGoodsDesc goodsDesc;
    //规格选项
    private List<TbItem> itemList;

    public TbGoods getGoods() {
        return goods;
    }

    public void setGoods(TbGoods goods) {
        this.goods = goods;
    }

    public TbGoodsDesc getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(TbGoodsDesc goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public List<TbItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<TbItem> itemList) {
        this.itemList = itemList;
    }
}
