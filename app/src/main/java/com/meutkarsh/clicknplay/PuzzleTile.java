package com.meutkarsh.clicknplay;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by utkarsh on 14/11/17.
 */

public class PuzzleTile {

    private Bitmap bitmap;
    private int num;

    public PuzzleTile(Bitmap bm, int n){
        this.bitmap = bm;
        this.num = n;
    }

    public int getNumber(){
        return this.num;
    }

    public void draw(Canvas canvas, int x, int y){
        canvas.drawBitmap(bitmap, x * bitmap.getWidth(), y * bitmap.getHeight(), null);
    }

    public boolean isClicked(float clickX, float clickY, int tileX, int tileY){
        int tx0 = tileX * bitmap.getWidth();
        int tx1 = (tileX + 1) * bitmap.getWidth();
        int ty0 = tileY * bitmap.getHeight();
        int ty1 = (tileY + 1) * bitmap.getHeight();
        return (clickX >= tx0 && clickX < tx1) && (clickY >= ty0 && clickY < ty1);
    }
}
