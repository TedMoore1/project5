package inputs;
/*
Optimize for if(b==true) -> if (b)
 */
public class Optimization5 {

    public static void main(String[] args) {
        boolean b1 = true;

        int i = 0;
        if(b1 == true){
            i--;
        }
        StaticJavaLib.assertTrue(i == -1);
        b1 = !b1;
        if(b1 == true){
            i++;
        }
        StaticJavaLib.assertTrue(i == -1);
        System.out.println(i);
        if(i == 1){
            b1 = true;
        }
        StaticJavaLib.assertTrue(!(b1 == true));
    }
}
