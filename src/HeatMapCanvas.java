
import java.util.Hashtable;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

/**
 * Test canvas for Heatmap demo
 * @author NIM
 */
public class HeatMapCanvas extends Canvas {

    private Hashtable data;
    private HeatMapper heatMap;
    
    HeatMapCanvas() {
        data = new Hashtable();
        heatMap = new HeatMapper(getWidth(), getHeight());
    }
    
    protected void paint(Graphics g) {
        System.out.println("Paint method called");
        g.setColor(0xffffffff);            
        g.fillRect(0, 0, getWidth(), getHeight()); 

        if (data != null && !data.isEmpty()) {
            long start = System.currentTimeMillis();
            System.out.println("Dataset not empty, creating image...");

            g.fillRect(0, 0, getWidth(), getHeight());                 
            g.drawRGB(heatMap.createRGBData(data, getWidth(), getHeight()),0,getWidth(),0,0,getWidth(), getHeight(), true);
                
            g.setColor(0x000000);
            g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
            g.drawString(data.size()+"points in "+ (System.currentTimeMillis()-start)+"ms", 0, 0, g.TOP|g.LEFT);
        }
        System.out.println("image done");
    }
    
    public void reset() {
        data.clear();
        System.out.println("Dataset reset done");
    }
    
    protected void pointerPressed(int x, int y) {
        if (data != null) {
            Point newData = new Point(x, y, 50);
            Integer id = new Integer(x+y*getWidth());
            
            data.put(id, newData);
            
            System.out.println("Data point added to: "+newData.toString());
            repaint();
        }
    }
    
}
