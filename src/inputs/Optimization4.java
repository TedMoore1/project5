package inputs;
/*
Optimize for ILOAD x, ILOAD x -> ILOAD x, DUP
and
LOAD obj, GETFIELD f, LOAD obj, GEFIELD f -> LOAD obj, GETFIELD f, DUP
 */

class FieldOptimizaion4 {
    public int i;
    public int f;
}

public class Optimization4 {

    public static void main(String[] args) {
        int x = 1;
        int y = -1;
        FieldOptimizaion4 fopt = new FieldOptimizaion4();
        FieldOptimizaion4 fopt1 = new FieldOptimizaion4();

        x = x + x;
        if(x > 0){
            y = y + 1;
        }
        StaticJavaLib.assertTrue(x + x == 4);
        StaticJavaLib.assertTrue(y * y  == 0);

        for(int i = 0; i < x + x; i++){
            y = x - 4;
        }
        StaticJavaLib.assertTrue(x == 2);
        StaticJavaLib.assertTrue(y == -2);


        fopt.f = x - y;
        fopt.i = fopt.f + fopt.f;
        fopt1.i = fopt.f + fopt1.f;

        StaticJavaLib.assertTrue(fopt.f == 4);
        StaticJavaLib.assertTrue(fopt.i == 8);
        StaticJavaLib.assertTrue(fopt1.i == 4);

    }
}
