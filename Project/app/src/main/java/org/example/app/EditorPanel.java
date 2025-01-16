package org.example.app;

import org.apache.commons.lang3.tuple.Pair;

import javax.swing.*;
import javax.swing.text.Caret;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class EditorPanel extends JPanel {
    boolean showCaret = true;



    final GapBuffer gapbuffer;
    private int cursorPos;
    String check = "Arial";
    Font b = new Font(check, Font.PLAIN, 12);
    FontMetrics f = this.getFontMetrics(b);
    private JButton openFileButton;
    public EditorPanel(){
        gapbuffer = new GapBuffer();
        cursorPos = 0;
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handlekeyPressed(e);
            }



        });
        addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e){
                handleMouseClick(e);
            }
        });
        Timer blinkTimer = new Timer(500, e -> {
            showCaret = !showCaret;
            repaint();
        });
        blinkTimer.start();

    }



    private void handlekeyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        char c = e.getKeyChar();
        switch(code){
            case KeyEvent.VK_LEFT :
                cursorPos = Math.max(0, cursorPos - 1);
                gapbuffer.MoveCursor(cursorPos);
                repaint();
                break;
            case KeyEvent.VK_RIGHT:

                cursorPos = Math.min(gapbuffer.DEFAULT_BUFFER_SIZE - 1, cursorPos + 1);
                gapbuffer.MoveCursor(cursorPos);
                repaint();
                break;
            case KeyEvent.VK_BACK_SPACE:
                gapbuffer.backspace(cursorPos, 1, f);
                repaint();
                break;
            case KeyEvent.VK_DELETE:
                gapbuffer.forwarddelete(cursorPos, 1, f);
                repaint();
                break;
            case KeyEvent.VK_ENTER:

                gapbuffer.insert(cursorPos, new char[]{'\n'}, f);
                cursorPos++;
                repaint();
                break;

            default:
                if(!Character.isISOControl(c)){

                    char [] ar = new char[1];
                    ar[0] = c;
                    gapbuffer.insert(cursorPos,ar, f);
                    cursorPos++;
                    repaint();
                }
        }
    }

    private void handleMouseClick(MouseEvent e) {

        cursorPos = calculateTextIndexFromXY(e.getX(), e.getY());

        gapbuffer.MoveCursor(cursorPos);
        repaint();
    }

    private int calculateTextIndexFromXY(int x, int y) {
        return gapbuffer.convert(x, y, f);
    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setFont(b);
        String text = gapbuffer.getText();
        int height = f.getHeight();
        int [] t = gapbuffer.findGapHead();
        int caretWidth = t[0] ;
        int caretHeight = t[1] * f.getHeight();

        int y = 20;
        int x = 10;
        int caretX = x + caretWidth;
        int caretY = y + caretHeight;

        String [] line = text.split("\n");
        for(String l : line){

            g.drawString(l, x, y);
            y += height;
        }

        if (showCaret){
            int top = caretY - (height - 2);
        g.setColor(Color.BLACK);
        g.drawLine(caretX,top, caretX, caretY);
        }

    }

}
