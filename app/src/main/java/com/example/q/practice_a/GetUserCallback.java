package com.example.q.practice_a;

import android.graphics.Bitmap;

public interface GetUserCallback {
    public abstract void flagged(boolean flag);
    public abstract void imgData(Bitmap image);
}