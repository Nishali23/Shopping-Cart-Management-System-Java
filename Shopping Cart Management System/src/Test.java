import java.util.*;
public class Test {
    public static void main(){

        int[] array = {1,2,3,4,5,6,7,8,9,10};
        for(int i : array){
            if(i==10){
                System.out.println("10 is exist");
            }
            else{
                System.out.println("10 is not exist");
            }
        }

    }
}
