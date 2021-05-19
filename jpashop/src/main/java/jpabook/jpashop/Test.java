package jpabook.jpashop;

public class Test {
    public static void main(String[] args) {
       Integer a = 5;
       Integer b = a;

       a = 10;

        System.out.println(a);
        System.out.println(b);
    }
}


class T {
    private int a;

    public T(int a) {
        this.a = a;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }
}