package com.meutkarsh.clicknplay;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;

/**
 * Created by utkarsh on 14/11/17.
 */

public class PuzzleBoard {

    private static final int NumTiles = 3;
    private static final int x[] = new int[]{-1, 1, 0, 0};
    private static final int y[] = new int[]{0, 0, -1, 1};
    private int z = 4;

    private ArrayList<PuzzleTile> tiles;
    int steps;
    public PuzzleBoard previousBoard;

    PuzzleBoard(Bitmap bitmap, int parentWidth){
        tiles = new ArrayList<PuzzleTile>();
        Bitmap scaledBitmap = bitmap.createScaledBitmap(bitmap, parentWidth, parentWidth, true);
        int w = parentWidth / NumTiles;
        int c = 0;
        for(int i = 0; i < NumTiles; i++){
            for(int j = 0; j < NumTiles; j++){
                if(i == NumTiles-1 && j == NumTiles-1)  tiles.add(null);
                else{
                    Bitmap b = bitmap.createBitmap(scaledBitmap, j * w, i * w, w, w);
                    PuzzleTile pt = new PuzzleTile(b, c++);
                    tiles.add(pt);
                }
            }
        }
    }

    PuzzleBoard(PuzzleBoard other){
        this.tiles = (ArrayList<PuzzleTile>) other.tiles.clone();
        this.previousBoard = other;
        this.steps = other.steps + 1;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)   return false;
        PuzzleBoard p = (PuzzleBoard) obj;
        return tiles.equals(p.tiles);
    }

    public void draw(Canvas canvas){
        if(tiles == null)   return;
        for(int i = 0; i < NumTiles * NumTiles; i++){
            PuzzleTile pt = tiles.get(i);
            if(pt != null){
                pt.draw(canvas, i % NumTiles, i / NumTiles);
            }
        }
    }

    public boolean click(float x, float y){
        for(int i = 0; i < NumTiles * NumTiles; i++){
            PuzzleTile pt = tiles.get(i);
            if(pt != null && pt.isClicked(x, y, i % NumTiles, i / NumTiles)){
                return tryMoving(i % NumTiles, i / NumTiles);
            }
        }
        return false;
    }

    private boolean tryMoving(int tx, int ty){
        for(int i = 0; i < z; i++){
            int nx = tx + x[i];
            int ny = ty + y[i];
            if(nx >= 0 && nx < NumTiles && ny >= 0 && ny < NumTiles && tiles.get(nx + ny * NumTiles) == null){
                int u = nx + ny * NumTiles;
                int v = tx + ty * NumTiles;
                PuzzleTile pt = tiles.get(u);
                tiles.set(u, tiles.get(v));
                tiles.set(v, pt);
                return true;
            }
        }
        return false;
    }

    public boolean resolved(){
        for(int i = 0; i < NumTiles * NumTiles - 1; i++){
            PuzzleTile pt = tiles.get(i);
            if(pt == null || pt.getNumber() != i)   return false;
        }
        return true;
    }

    public ArrayList<PuzzleBoard> neighbours(){
        ArrayList<PuzzleBoard> newBoards = new ArrayList<PuzzleBoard>();
        PuzzleBoard existingBoard = this;
        for(int i = 0; i < NumTiles * NumTiles - 1; i++){
            if(tiles.get(i) == null){
                int tx = i % NumTiles;
                int ty = i / NumTiles;
                for(int j = 0; j < z; j++){
                    int nx = tx + x[j];
                    int ny = ty + y[j];
                    if(nx >= 0 && nx < NumTiles && ny >= 0 && ny < NumTiles){
                        PuzzleBoard newBoard = new PuzzleBoard(existingBoard);
                        int u = nx + ny * NumTiles;
                        int v = tx + ty * NumTiles;
                        PuzzleTile pt = newBoard.tiles.get(u);
                        newBoard.tiles.set(u, newBoard.tiles.get(v));
                        newBoard.tiles.set(v, pt);
                        newBoards.add(newBoard);
                    }
                }
                break;
            }
        }
        return newBoards;
    }

    public int priority(){
        int manhattanDistance = 0;
        for(int i = 0; i < NumTiles; i++){
            if(tiles.get(i) != null){
                int cx = i % NumTiles;
                int cy = i / NumTiles;
                int tnum = tiles.get(i).getNumber();
                int px = tnum % NumTiles;
                int py = tnum / NumTiles;
                manhattanDistance += Math.abs(cx - px) + Math.abs(cy - py);
            }
        }
        return manhattanDistance + steps;
    }
}
