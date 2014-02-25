
import java.util.Enumeration;
import java.util.Hashtable;
import javax.microedition.lcdui.Image;

/**
 * HeatMapper creates a heatmap styled visualization from dataset of
 * predefined x-y -coordinates and associated values.
 * @author NIM
 */
public class HeatMapper {
    
    private int width, height;
    
    /**
     * Default constructor
     * @param width
     * @param height 
     */
    HeatMapper(int width, int height) {
        this.width = width;
        this.height = height;                 
    }
    
    /**
     * Returns image based to data created in createRGBData -method
     * @param data Hashtable[Integer, Point], where integer is point.x + point.y * image width
     * @return Heatmap image
     */
    public Image makeImage(Hashtable data) {
        Image img = Image.createRGBImage(createRGBData(data, width, height), width, height, true);        
        return img;
    }
    
    /**
     * Returns predefined color based to value
     * @param value float [0..1]
     * @return 0xaarrggbb color
     */   
    private int defaultColor(float value) {   
        if (value > 0.9f) return 0xffff0000;
        if (value > 0.8f) return 0xe3ff2000;
        if (value > 0.7f) return 0xc7ff4000;
        if (value > 0.6f) return 0xabff6000;
        if (value > 0.5f) return 0x8fff8000;
        if (value > 0.4f) return 0x73ff9f00;
        if (value > 0.3f) return 0x57ffbf00;
        if (value > 0.2f) return 0x3bffdf00;
        if (value > 0.1f) return 0x1fffff00;
      
        return 0x00000000;        
    }
            
    /**
     * Method to create array of int values denoting aRGB colors.
     * Values are arranged in such way that the final result resembles
     * a heatmap. Resulting array can be used to create transparent images.
     * 
     * @param datapoints Hashtable[Integer, Point] where Integer = point.x + point.y * width
     * @param width final image width
     * @param height final image height
     * @return 
     */
    public int[] createRGBData(Hashtable datapoints, int width, int height) {
        float[] value = new float[width * height];
        int[] rgbData = new int[width * height];

        int step = 2;
        Enumeration keys = datapoints.keys();
        
        // for timing purposes
        long start, startDp, interval, intervalDp;
        
        float maxValue = 0.0f;
        start = System.currentTimeMillis();
        
        while (keys.hasMoreElements()) {
            startDp = System.currentTimeMillis();
            Integer pos = (Integer) keys.nextElement();            
            System.out.println("Heatmapper.createRGBData position: "+pos.intValue());
            Point data = (Point) datapoints.get(pos);
            System.out.println("Heatmapper.createRGBData calculating datapoint: "+data.toString());
          
            int radius = (int) Math.floor(data.value / step);
            int x = (int) Math.floor(pos.intValue() % width);
            int y = (int) Math.floor(pos.intValue() / width);
            
            // calculate x.y
            for (int scanx = (int) (x - radius); scanx < (int) (x + radius); scanx += 1) {
                // out of screen area
                if (scanx < 0 || scanx > width) {
                    continue;
                }
                for (int scany = (int) (y - radius); scany < (int) (y + radius); scany += 1) {
                    if (scany < 0 || scany > height) {
                        continue;
                    }

                    float dist = (float) Math.sqrt(((scanx - x)*(scanx-x)) + ((scany - y)*(scany-y)));
                    if (dist > radius) {
                        continue;
                    } else {
                        float v = (float) (data.value - step*dist);
                        int id = scanx + scany * width;
                        if (value[id] != 0.0) {
                            value[id] = (value[id] + v);                            
//                            System.out.println("Heatmapper.createRGBData x"+x+" y"+y+" from data("+data.toString()+") was not 0, new value = "+value[id]);
                        } else {                            
                            value[id] = v;
//                            System.out.println("Heatmapper.createRGBData v data("+data.toString()+") was 0.0, value set to = "+value[id]);
                        }
                        maxValue = Math.max(value[id], maxValue);
                    }
                }
            }
            intervalDp = System.currentTimeMillis()-startDp;
            System.out.println("Heatmapper.createRGBData datapoint calculation took "+intervalDp+"ms");
        }
        System.out.println("Heatmapper.createRGBData values calculated, creating rgb data table..."); 
        System.out.println("Heatmapper.createRGBData max value: "+maxValue);
               
        for(int pos = 0; pos < value.length; pos++){
            int x = (int) Math.floor(pos % this.width);
            int y = (int) Math.floor(pos / this.width);
            
            // data = [0xa1r1g11b, 0xa2r2g2b2, ... ]
            int pixelColorIndex = x + y * this.width;

            int color = defaultColor(value[pos]/maxValue);
            
            rgbData[pixelColorIndex] = color; // 0xaarrggbb

        }
        interval = System.currentTimeMillis()-start;
        System.out.println("Heatmapper.createRGBData whole calculation for "+datapoints.size()+" took "+interval+"ms");
        return rgbData;
        
    }  
}
