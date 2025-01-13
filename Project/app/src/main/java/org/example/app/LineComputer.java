package org.example.app;

import java.awt.*;
import java.util.ArrayList;

public class LineComputer {



   public ArrayList<LineNode> ReComputeIndex(GapBuffer gapbuff, FontMetrics f){


       ArrayList<LineNode> fin = new ArrayList<>();
       int i = 0;
     //  int vert_height = f.getHeight();
       while(i < gapbuff.gaphead) {
           int line = i;
           int linewidth = 0;
            LineNode t = new LineNode();
            t.Sindex = line;
               while (line < gapbuff.gaphead && gapbuff.gapBuffer[line] != '\n') {

                   linewidth += f.charWidth(gapbuff.gapBuffer[line]);
                   t.pos.add(linewidth);
                   line++;

               }
           i = line;
               if(i < gapbuff.gaphead && gapbuff.gapBuffer[i] == '\n')
                   i++;
           fin.add(t);


       }

       i = gapbuff.gaptail;
       while(i < gapbuff.DEFAULT_BUFFER_SIZE) {
           int line = i;
           int linewidth = 0;
           LineNode t = new LineNode();
           t.Sindex = line;
           while (line < gapbuff.DEFAULT_BUFFER_SIZE && gapbuff.gapBuffer[line] != '\n') {
               linewidth += f.charWidth(gapbuff.gapBuffer[line]);
               t.pos.add(linewidth);
               line++;

           }
            fin.add(t);
           i = line;
           if (i < gapbuff.DEFAULT_BUFFER_SIZE && gapbuff.gapBuffer[i] == '\n') {
               i++;
           }

       }
       return fin;
   }
}
