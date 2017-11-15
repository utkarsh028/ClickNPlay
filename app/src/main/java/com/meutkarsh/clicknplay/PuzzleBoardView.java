package com.meutkarsh.clicknplay;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by utkarsh on 15/11/17.
 */

public class PuzzleBoardView extends View {

    public static final int NumShuffle = 10;
    private Activity activity;
    private PuzzleBoard puzzleBoard;
    private ArrayList<PuzzleBoard> animation;
    private Random random = new Random();

    public PuzzleBoardView(Context context) {
        super(context);
        activity = (Activity) context;
        animation = null;
    }

    public void initialize(Bitmap bitmap){
        int w = getWidth();
        puzzleBoard = new PuzzleBoard(bitmap, w);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(puzzleBoard != null){
            if(animation.size() > 0){
                puzzleBoard = animation.remove(0);
                puzzleBoard.draw(canvas);
                if (animation.size() == 0){
                    animation = null;
                    //reset();
                    Toast.makeText(activity, "Good Job!!!", Toast.LENGTH_SHORT).show();
                }else   this.postInvalidateDelayed(500);
            }else puzzleBoard.draw(canvas);
        }
    }

    public void shuffle(){
        if(animation == null && puzzleBoard != null){
            for(int i = 0; i < NumShuffle; i++){
                Log.e("Utkarsh", "" + puzzleBoard.neighbours().size());
                //to be continued
            }
        }
    }
}
