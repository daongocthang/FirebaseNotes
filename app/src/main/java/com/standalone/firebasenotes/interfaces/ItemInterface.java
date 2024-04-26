package com.standalone.firebasenotes.interfaces;

import android.content.Context;

public interface ItemInterface {
    void editItem(int pos);

    void removeItem(int pos);

    Context getContext();

}
