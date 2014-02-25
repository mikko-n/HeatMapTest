/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.*;

/**
 * Test midlet for Heatmap
 * @author NIM
 */
public class HeatMapTest extends MIDlet implements CommandListener {

    private Command resetCmd;
    private Command exitCmd;
    private HeatMapCanvas testCanvas;
    public void startApp() {
        Display display = Display.getDisplay(this);
        testCanvas = new HeatMapCanvas();

        resetCmd = new Command("Reset", "Reset", Command.SCREEN, 1);
        exitCmd = new Command("Exit", "Exit", Command.EXIT, 0);

        testCanvas.addCommand(resetCmd);
        testCanvas.addCommand(exitCmd);
        testCanvas.setCommandListener(this);
        
        display.setCurrent(testCanvas);
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
    }

    public void commandAction(Command c, Displayable d) {
        if (c == exitCmd) {
            destroyApp(true);
            notifyDestroyed();
        }
        if (c == resetCmd) {
            testCanvas.reset();
            testCanvas.repaint();
        }
    }
}
