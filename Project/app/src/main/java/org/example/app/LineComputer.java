package org.example.app;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Line {
    String check = "example";
    Font b = new Font(check, 10, 12);

    FontMetrics f =  new FontMetrics(b) {
        /**
         * Gets the {@code Font} described by this
         * {@code FontMetrics} object.
         *
         * @return the {@code Font} described by this
         * {@code FontMetrics} object.
         */
        @Override
        public Font getFont() {
            return super.getFont();
        }
    };
   public int ComputeIndex(GapBuffer gapbuff, int x, int y){
        int curX = 0;
        int curY = 0;

       int i = 0;
       int vert_height = f.getHeight();
       while(i < gapbuff.gaphead) {
           int line = i;
           int linewidth = 0;
           if (curY == (int) (double) (y / vert_height)) {
               while (line < gapbuff.gaphead && gapbuff.gapBuffer[line] != '\n') {
                   linewidth += f.charWidth(gapbuff.gapBuffer[line]);
                   line++;
               }
           } else {
               while (line < gapbuff.gaphead && gapbuff.gapBuffer[line] != '\n') {

                   line++;
               }
           }
           i = line;
           curY += vert_height;
       }
       i = gapbuff.gaptail;
       while(i < gapbuff.DEFAULT_BUFFER_SIZE){

       }
   }
}
