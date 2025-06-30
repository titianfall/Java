class ArrayUtil{
    static int [] c;
    public static int [] concat(int []a, int[]b){
        c = new int[a.length+b.length];
        for(int i=0;i<a.length;i++){
            c[i] = a[i];
        }
        for(int i=0;i<b.length;i++){
            c[i+a.length]=b[i];
        }
        return c;
    }
    public static void print(int[] a){
        for(int i=0;i<a.length;i++){
            System.out.print(a[i]+" ");
        }
        System.out.println();
    }
}

public class p11 {
    public static void main(String[] args){
        int [] a1={1,5,7,9};
        int [] a2 ={3,6,-1,100,77};
        int [] a3=ArrayUtil.concat(a1,a2);

        ArrayUtil.print(a1);
        ArrayUtil.print(a2);
        ArrayUtil.print(a3);
    }   
}
