package inputs;
/*
Optimize for 0 * x -> 0, or 0 / x -> 0
 */
public class Optimization3 {

    public static void main(String[] args) {
        int i = 1;
        int j = -1;

        i =0 * i;
        if(i > 0){
            j--;
        }
        StaticJavaLib.assertTrue(i == 0);
        StaticJavaLib.assertTrue(j == -1);
        i++;
        j = 0 / i;
        StaticJavaLib.assertTrue(j == 0);
        StaticJavaLib.assertTrue(i == 1);
    }
}
