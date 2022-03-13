package com.company;

public class ThreadN implements Runnable {

    int[] array;
    int count;
    int numb;

    public void run() {
        bubbleSort(array);
    }

    public int[] getArray(){
        return array;
    }

    // разбиваем исходный массив на k равных по количеству элементов частей (за исключением последней части)
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
}