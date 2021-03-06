package Arrays;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by leiwang on 4/16/18.
 */
//Given n points on a 2D plane,
//find the maximum number of points that
//lie on the same straight line.
public class MaxPointsInALine {
    public static void main(String[] args) {

        Point[] pts = new Point[5];
        pts[0] = new Point(0,1);
        pts[1] = new Point(0,10);
        pts[2] = new Point(0,4);
        pts[3] = new Point(0,14);
        pts[4] = new Point(0,19);
        System.out.println("max points = " + maxPoints(pts));
        System.out.println("max points2 = " + maxPoints2(pts));

    }

    static class Point {
        int x;
        int y;

        Point() {
            x = 0;
            y = 0;
        }

        Point(int a,
              int b) {
            x = a;
            y = b;
        }
    }

    //We can solve above problem by following approach – For each point p, calculate its
    //slope with other points and use a map to record how many points have same slope,
    //by which we can find out how many points are on same line with p as their one point.
    //For each point keep doing the same thing and update the maximum number of point
    //count found so far.
//
//    Some things to note in implementation are:
//            1) if two point are (x1, y1) and (x2, y2) then their slope will be (y2 – y1) / (x2 – x1)
    //           which can be a double value and can cause precision problems. To get rid of the precision
    //           problems, we treat slope as pair ((y2 – y1), (x2 – x1)) instead of ratio and reduce pair
    //           by their gcd before inserting into map. In below code points which are vertical or
    //           repeated are treated separately.
//
//            2) If we use unordered_map in c++ or HashMap in Java for storing the slope pair, then
    //           total time complexity of solution will be O(n^2)

    /*
     *  A line is determined by two factors,say y=ax+b
     *
     *  If two points(x1,y1) (x2,y2) are on the same line(Of course).

     *  Consider the gap between two points.

     *  We have (y2-y1)=a(x2-x1),a=(y2-y1)/(x2-x1) a is a rational, b is canceled since b is a constant

     *  If a third point (x3,y3) are on the same line. So we must have y3=ax3+b

     *  Thus,(y3-y1)/(x3-x1)=(y2-y1)/(x2-x1)=a

     *  Since a is a rational, there exists y0 and x0, y0/x0=(y3-y1)/(x3-x1)=(y2-y1)/(x2-x1)=a

     *  So we can use y0&x0 to track a line;
     */
    static int maxPoints(Point[] points) {
        if (points == null) return 0;
        if (points.length <= 2) return points.length;

        Map<Integer, Map<Integer, Integer>> map = new HashMap<Integer, Map<Integer, Integer>>();
        int result = 0;
        for (int i = 0; i < points.length; i++) {
            map.clear();
            int overlap = 0, max = 0;
            for (int j = i + 1; j < points.length; j++) {
                int x = points[j].x - points[i].x;
                int y = points[j].y - points[i].y;
                if (x == 0 && y == 0) {
                    overlap++;
                    continue;
                }
                // GCD = greatest common denominator
                int gcd = generateGCD(x, y);
                if (gcd != 0) {
                    x /= gcd;
                    y /= gcd;
                }

                if (map.containsKey(x)) {
                    if (map.get(x).containsKey(y)) {
                        map.get(x).put(y, map.get(x).get(y) + 1);
                    } else {
                        map.get(x).put(y, 1);
                    }
                } else {
                    Map<Integer, Integer> m = new HashMap<Integer, Integer>();
                    m.put(y, 1);
                    map.put(x, m);
                }
                max = Math.max(max, map.get(x).get(y));
            }
            result = Math.max(result, max + overlap + 1);
        }
        return result;


    }

    private static int generateGCD(int a, int b) {

        if (b == 0) return a;
        else return generateGCD(b, a % b);
    }

    public static int maxPoints2(Point[] points) {
        if(points.length <= 0) return 0;
        if(points.length <= 2) return points.length;
        int result = 0;
        for(int i = 0; i < points.length; i++){
            HashMap<Double, Integer> hm = new HashMap<Double, Integer>();
            int samex = 1;
            int samep = 0;
            for(int j = 0; j < points.length; j++){
                if(j != i){
                    if((points[j].x == points[i].x) && (points[j].y == points[i].y)){
                        samep++;
                    }
                    if(points[j].x == points[i].x){
                        samex++;
                        continue;
                    }
                    double k = (double)(points[j].y - points[i].y) / (double)(points[j].x - points[i].x);
                    if(hm.containsKey(k)){
                        hm.put(k,hm.get(k) + 1);
                    }else{
                        hm.put(k, 2);
                    }
                    result = Math.max(result, hm.get(k) + samep);
                }
            }
            result = Math.max(result, samex);
        }
        return result;
    }
}
