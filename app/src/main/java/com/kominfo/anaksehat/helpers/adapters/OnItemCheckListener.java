package com.kominfo.anaksehat.helpers.adapters;

import java.util.List;

public interface OnItemCheckListener<t, String> {
    void onItemCheck(t data, String type);
    void onItemUncheck(t data, String type);
}
