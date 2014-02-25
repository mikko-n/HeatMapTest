/**
 * Point is a class for holding coordinate x and y values and additional
 * integer value.
 * @author NIM
 */
public class Point {        
        public int x = 0;
        public int y = 0;
        public int value = 0;
        
        public Point(int x, int y, int value) {            
            this.x = x;
            this.y = y;
            this.value = value;                    
        }

        public String toString() {
            return "x"+x+" y"+y+" value="+value;
        }
        
    }
