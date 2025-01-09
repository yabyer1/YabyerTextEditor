public class GapBuffer{
     int DEFAULT_BUFFER_SIZE = 100;
    char [] gapBuffer = new char[DEFAULT_BUFFER_SIZE];

    int gaphead = 0;
    int gaptail = 0;

    void checkBound(int cur, int amnt){
        while(cur + amnt >= gapBuffer.length){
            int amnt_diff = DEFAULT_BUFFER_SIZE - gaptail;
            DEFAULT_BUFFER_SIZE *= 2;

            char [] n = new char[DEFAULT_BUFFER_SIZE];

            System.arraycopy(gapBuffer, 0, n, 0, gapBuffer.length);
            gapBuffer = n;
            gaptail = DEFAULT_BUFFER_SIZE - amnt_diff;
        }

    }
void MoveCursor(int cur){

        if(gaphead == cur ){
                return;
        }
        if(cur < gaphead){
            int slide = gaphead - cur;
            for(int i = 0; i < slide; i++){
                gapBuffer[gaptail - 1 - i] = gapBuffer[gaphead -1 -  i];
            }
            gaphead -= slide;
            gaptail -= slide;
            return;
        }
        else{
            int slide = cur - gaphead;
            checkBound(cur, slide);
            for(int i = 0; i < slide; i++){
                gapBuffer[gaphead + i] = gapBuffer[gaptail + i];
            }
            gaphead += slide;
            gaptail += slide;
        }
}

    void insert(int cur, char [] wrd){


        MoveCursor(cur);
        System.arraycopy(wrd, 0, gapBuffer, cur, wrd.length);
        gaphead += wrd.length;

    }
    void backspace(int cur, int n){
        MoveCursor(cur);
        gaphead  = Math.max(0 ,gaphead -n) ;

    }
    void forwarddelete(int cur, int n){
        MoveCursor(cur);
        gaptail = Math.min(gaptail, cur + n)
    }



}
public static void main(String[] args) {

}