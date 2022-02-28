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
        System.out.println((numb + 1) + " часть массива:");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
        System.out.println("Отсортированная " + (numb + 1) + " часть массива:");
        sort(array);
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    public int[] getArray(){
        return array;
    }

    ThreadN(int[] a, int k, int n){
        count = k;
        numb = n;
        int step = (a.length) / k;
        int m = step;
        if ( n == (a.length) / k - 1){
            m += ((a.length) % k);
        }
        array = new int[m];
        for (int i = 0; i < m; i++) {
            array[i] = a[step * n + i];
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
        System.out.println("Ваш массив: ");
        for (int i = 0; i < a.length; i++) {
            a[i] = rand.nextInt();
            a1[i] = a[i];
            a2[i] = a[i];
            System.out.print(a[i] + " ");
        }
        System.out.println();

        long time1 = System.currentTimeMillis();
        sort(a1);
        time1 = System.currentTimeMillis() - time1;

        System.out.println("Массив отсортированный пузырьком:");
        for (int i = 0; i < a.length; i++) {
            System.out.print(a1[i] + " ");
        }
        System.out.println();

        System.out.println("Время №1 = " + time1);
        System.out.println();

        System.out.println("Введите количество частей, на которые нужно разделить исходный массив для сортировки");
        int k = sc.nextInt();
        ThreadN[] threadsN = new ThreadN[k];
        Thread[] threads = new Thread[k];
        long time2 = System.currentTimeMillis();
        try{
            for (int i = 0; i < k; i++) {
                threadsN[i] = new ThreadN(a, k, i);
                threads[i] = new Thread(threadsN[i]);
                threads[i].start();
                threads[i].join();
            }
            for (int i = 0; i < k-1; i++) {
                for (int j = 0; j < n/k; j++) {
                    a2[n/k * i + j] = threadsN[i].getArray()[j];
                }
            }
            for (int i = 0; i < n/k + n%k; i++) {
                a2[n/k * (k-1) + i] = threadsN[k-1].getArray()[i];
            }
            sort(a2);
        }
        catch(InterruptedException e){

            System.out.println("Something went wrong");
        }
        time2 = System.currentTimeMillis() - time2;

        System.out.println("Отсортированный массив после объединения всех частей:");
        for (int i = 0; i < a2.length; i++) {
            System.out.print(a2[i] + " ");
        }
        System.out.println();
        System.out.println("Время №2 = " + time2);
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