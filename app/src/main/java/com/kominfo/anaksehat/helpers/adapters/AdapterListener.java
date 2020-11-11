package com.kominfo.anaksehat.helpers.adapters;

public interface AdapterListener<t> {
    void onItemSelected(t data);
    void onItemLongSelected(t data);
}
