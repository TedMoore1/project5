package inputs;
/*
Optimize for 0 + x -> x
 */
public class Optimization2 {

    public static void main(String[] args) {
        int i = 3;

        i--;
        if(0 + i > 0){
            i--;
        }
        StaticJavaLib.assertTrue(0 + i + 0 == 1);
    }
}
