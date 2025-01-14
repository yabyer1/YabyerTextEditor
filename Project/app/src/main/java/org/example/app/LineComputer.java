package org.example.app;

import java.awt.*;
import java.util.ArrayList;

public class LineComputer {



   public ArrayList<LineNode> ReComputeIndex(GapBuffer gapbuff, FontMetrics f){


       ArrayList<LineNode> fin = new ArrayList<>();

     //  int vert_height = f.getHeight();
           int line = 0;
           int linewidth = 0;
            LineNode t = new LineNode();
            t.Sindex = line;
               while (line < gapbuff.gaphead) {
                    if(gapbuff.gapBuffer[line] == '\n'){
                        fin.add(t);
                        t = new LineNode();
                        linewidth = 0;
                        t.Sindex = line + 1;
                    }
                    else {
                        linewidth += f.charWidth(gapbuff.gapBuffer[line]);
                        t.pos.add(linewidth);

                    }
                   line++;
               }


               line = gapbuff.gaptail;
           while (line < gapbuff.DEFAULT_BUFFER_SIZE) {
               if (gapbuff.gapBuffer[line] == '\n') {
                   fin.add(t);
                   t = new LineNode();
                   linewidth = 0;
                   t.Sindex = line + 1;
               }
               else {
                   linewidth += f.charWidth(gapbuff.gapBuffer[line]);
                   t.pos.add(linewidth);

               }
               line++;
           }
       fin.add(t);
       return fin;
   }
}
