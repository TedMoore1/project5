package inputs;
/*
Optimize for x + 0 -> x, or x - 0 -> x
 */
public class Optimization1 {

    public static void main(String[] args) {
        int i = 3;

        i = i + 0;
        if(i + 0 > 0){
            i--;
        }
        StaticJavaLib.assertTrue(i - 0 == 2);
    }
}
