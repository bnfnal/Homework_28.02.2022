package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class ThreadN implements Runnable {

    int[] array;
    int count;
    int numb;

    public void run() {
        sort(array);
    }

    public int[] getArray(){
        return array;
    }

    ThreadN(int[] a, int k, int ind){
        count = k;
        numb = ind;
        int n = a.length;
        int m = n/k;
        if ( ind == k - 1){
            m += n%k;
        }
        array = new int[m];
        for (int j = 0; j < m; j++) {
            array[j] = a[n/k * ind + j];
        }
    }

    public static void sort(int[] array) {
        boolean sorted = false;
        int x;
        while(!sorted) {
            sorted = true;
            for (int i = 0; i < array.length - 1; i++) {
                if (array[i] > array[i+1]) {
                    x = array[i];
                    array[i] = array[i+1];
                    array[i+1] = x;
                    sorted = false;
                }
            }
        }
    }
}

class Main{
    public static void main(String[] args) {
        System.out.println("Введите количество элементов в массиве");
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();
        int n = sc.nextInt();
        int[] a = new int[n];
        int[] a1 = new int[n];
        int[] a2 = new int[n];
        long[] time = new long[n+1];

        //System.out.println("Ваш массив: ");
        for (int i = 0; i < a.length; i++) {
            a[i] = rand.nextInt();
            a1[i] = a[i];
            a2[i] = a[i];
            //System.out.print(a[i] + " ");
        }
        //System.out.println();

        time[0] = System.currentTimeMillis();
        sort(a1);
        time[0] = System.currentTimeMillis() - time[0];

        /*
        System.out.println("Массив отсортированный пузырьком:");
        for (int i = 0; i < a.length; i++) {
            System.out.print(a1[i] + " ");
        }
        System.out.println();
         */

        System.out.println("Время сортировки пузырьком = " + time[0]);
        System.out.println();

        System.out.println("Введите количество частей, на которые нужно разделить исходный массив для сортировки");
        int count = sc.nextInt();
        if (count  > n) count = n;
        int[] bc = new int[n];
        ThreadN[] threadsNCount = new ThreadN[count];
        Thread[] threadsCount = new Thread[count];
        long timeCount = System.currentTimeMillis();
        try {
            for (int i = 0; i < count; i++) {
                threadsNCount[i] = new ThreadN(a2, count, i);
                threadsCount[i] = new Thread(threadsNCount[i]);
                threadsCount[i].start();
                threadsCount[i].join();
            }
            for (int i = 0; i < count; i++) {
                for (int j = 0; j < n / count; j++) {
                    bc[n / count * i + j] = threadsNCount[i].getArray()[j];
                }
                for (int j = 0; j < n/count + n%count; j++) {
                    bc[n/count * (count-1) + j] = threadsNCount[count-1].getArray()[j];
                }
            }
            sort(bc);
            if (count == n){
                for (int i = 0; i < bc.length; i++) {
                    a2[i] = bc[i];
                }
            }
        }
        catch(InterruptedException e){
            System.out.println("Something went wrong");
        }
        timeCount = System.currentTimeMillis() - timeCount;
        System.out.println("Время для выбранного числа частей = " + timeCount);
        System.out.println();

        long min = 1000000000;
        int minArg = 1000000;
        for (int k = 1; k < n+1; k++){
            int[] b = new int[n];
            ThreadN[] threadsN = new ThreadN[k];
            Thread[] threads = new Thread[k];
            time[k] = System.currentTimeMillis();
            try {
                for (int i = 0; i < k; i++) {
                    threadsN[i] = new ThreadN(a2, k, i);
                    threads[i] = new Thread(threadsN[i]);
                    threads[i].start();
                    threads[i].join();
                }
                for (int i = 0; i < k; i++) {
                    for (int j = 0; j < n / k; j++) {
                        b[n / k * i + j] = threadsN[i].getArray()[j];
                    }
                    for (int j = 0; j < n/k + n%k; j++) {
                        b[n/k * (k-1) + j] = threadsN[k-1].getArray()[j];
                    }
                }
                sort(b);
                if (k == n){
                    for (int i = 0; i < b.length; i++) {
                        a2[i] = b[i];
                    }
                }
            }
            catch(InterruptedException e){
                System.out.println("Something went wrong");
            }
            time[k] = System.currentTimeMillis() - time[k];
            if(time[k] < min){
                min = time[k];
                minArg = k;
            }
        }

        /*
        System.out.println("Отсортированный массив после объединения всех частей:");
        for (int i = 0; i < a2.length; i++) {
            System.out.print(a2[i] + " ");
        }
        System.out.println();

         */

        System.out.println("Оптимальное количество частей для сортировки = " + minArg);
        System.out.println("Время для оптимального количества частей = " + min);

        /*
        System.out.println("Время сортировки масива для разного количества частей:");
        for (int i = 1; i < time.length; i++) {
            System.out.print(time[i] + " ");
        }
        System.out.println();

         */

    }

    // сортировка пузырьком
    public static void sort(int[] array) {
        boolean sorted = false;
        int x;
        while(!sorted) {
            sorted = true;
            for (int i = 0; i < array.length - 1; i++) {
                if (array[i] > array[i+1]) {
                    x = array[i];
                    array[i] = array[i+1];
                    array[i+1] = x;
                    sorted = false;
                }
            }
        }
    }
}