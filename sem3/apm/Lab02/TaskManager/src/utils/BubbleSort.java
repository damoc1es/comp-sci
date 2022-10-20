package utils;

public class BubbleSort implements AbstractSorter {

    @Override
    public void sort(int[] arr) {
        for(int i=0; i < arr.length-1; i++) {
            boolean swapped = false;
            for(int j=0; j < arr.length-i-1; j++)
                if(arr[j] > arr[j+1]) {
                    int aux = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = aux;
                    swapped = true;
                }

            if(!swapped)
                break;
        }
    }
}
