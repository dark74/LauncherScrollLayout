package com.dk.asandroidlauncher.adapter;

import android.view.View;

public interface SAdapter {
    void exchange(int oldPosition, int newPositon);

    int getCount();

    View getView(int position);
}