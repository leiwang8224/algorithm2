package Math;

//Implement pow(x, n), which calculates x raised to the power n (xn).
//
//        Example 1:
//
//        Input: 2.00000, 10
//        Output: 1024.00000
//
//        Example 2:
//
//        Input: 2.10000, 3
//        Output: 9.26100
//
//        Example 3:
//
//        Input: 2.00000, -2
//        Output: 0.25000
//        Explanation: 2-2 = 1/22 = 1/4 = 0.25

public class Pow {
    public static void main(String[] args) {

    }

    double myPow(double x, int n) {
        if(n<0) return 1/x * myPow(1/x, -(n+1));
        if(n==0) return 1;
        if(n==2) return x*x;
        if(n%2==0) return myPow( myPow(x, n/2), 2);
        else return x*myPow( myPow(x, n/2), 2);
    }

    double myPow2(double x, int n) {
        if(n==0) return 1;
        double t = myPow2(x,n/2);
        if(n%2>0) return n<0 ? 1/x*t*t : x*t*t;
        else return t*t;
    }

    double myPow3(double x, int n) {
        if(n==0) return 1;
        if(n<0){
            n = -n;
            x = 1/x;
        }
        return n%2==0 ? myPow(x*x, n/2) : x*myPow(x*x, n/2);
    }

//    double myPow4(double x, int n) {
//        if(n==0) return 1;
//        if(n<0) {
//            n = -n;
//            x = 1/x;
//        }
//        double ans = 1;
//        while(n>0){
//            if(n&1) ans *= x;
//            x *= x;
//            n >>= 1;
//        }
//        return ans;
//    }
}
