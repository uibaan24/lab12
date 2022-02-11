import java.util.Arrays;

public class ThreadClassMethods {
    static int SIZE = 10000000;
    static int HALFSIZE = SIZE/2;

    public static void singleThread(){
        float[] arr = new float[SIZE];
        Arrays.fill(arr, 1);
        long a = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println("Время работы первого метода");
        System.out.println(System.currentTimeMillis() - a);
    }
    public static void doubleThread(){
        float[] arr = new float[SIZE];
        Arrays.fill(arr, 1);
        float[] arr1 = new float[HALFSIZE];
        float[] arr2 = new float[HALFSIZE];
        long a = System.currentTimeMillis();
        System.arraycopy(arr, 0,arr1, 0,HALFSIZE);
        System.arraycopy(arr,HALFSIZE,arr2, 0,HALFSIZE);

        MultiThread mOne = new MultiThread(arr1, 0);
        MultiThread mTwo = new MultiThread(arr2, HALFSIZE);

        mOne.start();
        mTwo.start();
        try {
            mOne.join();
            mTwo.join();
        }
        catch (InterruptedException ie){
            System.out.println("Потоки прерваны");
        }

        System.arraycopy(arr1, 0,arr, 0,HALFSIZE);
        System.arraycopy(arr2, 0,arr,HALFSIZE,HALFSIZE);
        System.out.println("Время работы второго метода");
        System.out.println(System.currentTimeMillis() - a);
    }
    public static void main(String[] args){
        singleThread();
        doubleThread();
    }
}
class MultiThread extends Thread{
    float[] arr;
    int shift;
    MultiThread(float[]array,int shift){
        this.arr = array;
        this.shift = shift;
    }

    @Override
    public void run() {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + (i+ shift)/ 5) * Math.cos(0.2f + (i+ shift) / 5) * Math.cos(0.4f + (i+ shift) / 2));
        }
    }
    // 1 1 1 1 1 1 1 1 1 1
    // 0 1 2 3 4 5 6 7 8 9

    // 1 1 1 1 1
    // 0 1 2 3 4

    //1 1 1 1 1 + shift 5
    //0 1 2 3 4
}