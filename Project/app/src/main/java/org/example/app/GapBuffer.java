package org.example.app;

import org.apache.commons.lang3.tuple.Pair;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

public class GapBuffer {

    public int DEFAULT_BUFFER_SIZE = 100;
    public char[] gapBuffer = new char[DEFAULT_BUFFER_SIZE];
    int gaphead = 0;
    int gaptail = DEFAULT_BUFFER_SIZE;
    ArrayList<LineNode> lineIndex = new ArrayList<>();
    LineComputer computer = new LineComputer();
    void checkBound(int cur, int amnt) {
        while (cur + amnt >= gaptail) {
            int amnt_diff = DEFAULT_BUFFER_SIZE - gaptail;
            DEFAULT_BUFFER_SIZE *= 2;

            char[] n = new char[DEFAULT_BUFFER_SIZE];

            System.arraycopy(gapBuffer, 0, n, 0, gapBuffer.length);
            gapBuffer = n;
            gaptail = DEFAULT_BUFFER_SIZE - amnt_diff;
        }


    }
    int getVisibleText(){
        return gaphead + (DEFAULT_BUFFER_SIZE - gaptail);
    }

    void MoveCursor(int cur) {
        int visibleLen = getVisibleText();
        cur = Math.max(0, Math.min(cur, visibleLen));
        if (gaphead == cur) {
            return;
        }
        if (cur < gaphead) {
            int slide = gaphead - cur;
            for (int i = 0; i < slide; i++) {
                gapBuffer[gaptail - 1 - i] = gapBuffer[gaphead - 1 - i];
            }
            gaphead -= slide;
            gaptail -= slide;

        } else {
            int slide = cur - gaphead;

            for (int i = 0; i < slide; i++) {
                gapBuffer[gaphead + i] = gapBuffer[gaptail + i];
            }
            gaphead += slide;
            gaptail += slide;
        }
    }

    void insert(int cur, char[] wrd, FontMetrics q) {
        MoveCursor(cur);
        checkBound(cur, wrd.length);
        System.arraycopy(wrd, 0, gapBuffer, gaphead, wrd.length);
        gaphead += wrd.length;
       lineIndex =  computer.ReComputeIndex(this, q);
    }

    void backspace(int cur, int n, FontMetrics p) {
        MoveCursor(cur);

        gaphead = Math.max(0, gaphead - n);
       /* for (int i = gaphead; i < gaptail; i++) {
            gapBuffer[i] = 'x';
        }*/

        lineIndex =  computer.ReComputeIndex(this, p);

    }

    void forwarddelete(int cur, int n, FontMetrics w) {
        MoveCursor(cur);
        MoveCursor(cur + n);
        gaptail -= n;
        if (gaptail < gaphead)
            gaptail = gaphead;
        lineIndex =  computer.ReComputeIndex(this, w);
    }



    public String getText() {
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < gaphead; i++){
            str.append(gapBuffer[i]);
        }
        for(int i = gaptail; i < DEFAULT_BUFFER_SIZE; i++){
            str.append(gapBuffer[i]);
        }
      return str.toString();
    }

    public int convert(int x, int y, FontMetrics f) {
        if(lineIndex.isEmpty()){
            return 0;
        }

       int lineHeight = f.getHeight();
       int index = y / lineHeight;
       index = Math.max(0, index);

       index = Math.min(lineIndex.size() - 1, index);
       LineNode cur = lineIndex.get(index);
       if (cur.pos.isEmpty()){
           return cur.Sindex;
       }
        ArrayList<Integer> row = lineIndex.get(index).pos;
       /* int l = 0;
        int h = row.size();
        while(l < h){
            int mid = (l + h) / 2;
            if(row.get(mid) > x){
                h = mid;
            }
            else if(row.get(mid) < x){
                l = mid + 1;
            }
            else{
                break;
            }
        }*/
        return lineIndex.get(index).Sindex + Collections.binarySearch(row, x);
    }

    public int[] findGapHead() {
        // Find x and y coordinates of the gaphead to display the new cursor

        if(lineIndex.isEmpty()){
            return new int[] {0, 0};
        }

        int l = 0;
        int h = lineIndex.size() - 1;

        while(l < h){
            int m = (l + h + 1) / 2;
            if(lineIndex.get(m).Sindex <= gaphead){
                l = m;
            }
            else{
                h = m - 1;
            }
        }
        int y =  l; // line number (vertical height)

        int xSum = 0; //cumulative sum to get x dimension



       if(!lineIndex.get(l).pos.isEmpty())  {
            int x = gaphead - lineIndex.get(l).Sindex; // offset from start of line (horizontal height)
           if(x <= 0) {
                xSum = 0;
           }
           else {
               x = Math.min(x, lineIndex.get(l).pos.size());
               xSum = lineIndex.get(l).pos.get(x - 1); //render after  last character before gaphead
           }
        }


        return new int[]{
               xSum, y
        };
    }
    void LoadFile(final String path, FontMetrics q) throws IOException{
        clearBuffer();
        final String rawFile = new String(Files.readAllBytes(Paths.get(path)));
        insert(0,rawFile.toCharArray(), q);

    }

    private void clearBuffer() {
        DEFAULT_BUFFER_SIZE = 100;
        gapBuffer = new char[DEFAULT_BUFFER_SIZE];
         gaphead = 0;
         gaptail = DEFAULT_BUFFER_SIZE;
    }
}
