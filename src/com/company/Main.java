package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

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
        bubbleSort(a1);
        time[0] = System.currentTimeMillis() - time[0];


//        System.out.println("Массив отсортированный пузырьком:");
//        for (int i = 0; i < a.length; i++) {
//            System.out.print(a1[i] + " ");
//        }
//        System.out.println();


        System.out.println("Время сортировки пузырьком = " + time[0]);
        System.out.println();

        System.out.println("Введите количество частей, на которые нужно разделить исходный массив для сортировки");
        int count = sc.nextInt();
        if (count  > n) count = n;

        long T = System.currentTimeMillis();
        long min = 1000000000;
        int minArg = 1000000;
        ThreadN[] threadsNCount = new ThreadN[count];
        Thread[] threadsCount = new Thread[count];
        time[count] = System.currentTimeMillis();
        try {
            for (int i = 0; i < count; i++) {
                threadsNCount[i] = new ThreadN(a2, count, i);
                threadsCount[i] = new Thread(threadsNCount[i]);
                threadsCount[i].start();
            }
            for (int i = 0; i < count; i++) {
                threadsCount[i].join();
            }

            ArrayList res = new ArrayList();
            res.add(threadsNCount[0].getArray());
            for (int i = 1; i < count; i++) {
                res.add(mergeSort((int[]) res.get(i-1), threadsNCount[i].getArray()));
            }
            int[] b = (int[]) res.get(count-1);
            for (int i = 0; i < b.length; i++) {
                a2[i] = b[i];
            }
        }
        catch(InterruptedException e){
            System.out.println("Something went wrong");
        }
        time[count] = System.currentTimeMillis() - time[count];
        if(time[count] < min){
            min = time[count];
            minArg = count;
        }
        System.out.println("Время для выбранного числа частей = " + time[count]);
//        System.out.println("Отсортированный массив после объединения всех частей:");
//        for (int i = 0; i < a2.length; i++) {
//            System.out.print(a2[i] + " ");
//        }
        System.out.println();
        System.out.println();


        for (int k = 1; k < n+1; k++){
            if (k==count) {
                System.out.println(k + " " + time[k]);
                continue;
            }
            ThreadN[] threadsN = new ThreadN[k];
            Thread[] threads = new Thread[k];
            time[k] = System.currentTimeMillis();
            try {
                for (int i = 0; i < k; i++) {
                    threadsN[i] = new ThreadN(a2, k, i);
                    threads[i] = new Thread(threadsN[i]);
                    threads[i].start();
                }
                for (int i = 0; i < k; i++) {
                    threads[i].join();
                }

                ArrayList res = new ArrayList();
                res.add(threadsN[0].getArray());
                for (int i = 1; i < k; i++) {
                    res.add(mergeSort((int[]) res.get(i-1), threadsN[i].getArray()));
                }
                int[] b = (int[]) res.get(k-1);
                if (k == count){
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
            System.out.println(k + " " + time[k] + " min = " + min + " при k = " + minArg + " полное время: " + ((System.currentTimeMillis()-T)/1000));
        }

        System.out.println("Оптимальное количество частей для сортировки = " + minArg);
        System.out.println("Время для оптимального количества частей = " + min);
        T = System.currentTimeMillis() - T;
        System.out.println("Итоговое время сортировки = " + T);
    }

    // сортировка пузырьком
    public static void bubbleSort(int[] array) {
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

    // сортировка слиянием
    public static int[] mergeSort(int[] a, int[] b) {
        int[] c = new int[a.length + b.length];
        int i = 0;
        int j = 0;
        for (int k = 0; k < a.length + b.length; k++)
        {
            if (i > a.length - 1)
            {
                int x = b[j];
                c[k] = x;
                j++;
            }
            else {
                if (j > b.length - 1)
                    {
                        int x = a[i];
                        c[k] = x;
                        i++;
                    }
                else{
                    if (a[i] < b[j])
                    {
                        int x = a[i];
                        c[k] = x;
                        i++;
                    }
                    else
                    {
                        int x = b[j];
                        c[k] = x;
                        j++;
                    }
                }
            }
        }
        return  c;
    }
}