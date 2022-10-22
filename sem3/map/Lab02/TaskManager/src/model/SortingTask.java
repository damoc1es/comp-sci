package model;

import utils.AbstractSorter;
import utils.BubbleSort;
import utils.MergeSort;
import utils.SortType;

import java.util.Arrays;

public class SortingTask extends Task {
    int[] list;
    AbstractSorter sorter;

    private void sort() {
        sorter.sort(list);
    }

    public SortingTask(String taskID, String description, SortType sortType, int[] list) {
        super(taskID, description);
        this.list = list;

        switch (sortType) {
            case BUBBLE_SORT -> sorter = new BubbleSort();
            case MERGE_SORT -> sorter = new MergeSort();
        }

        sort();
    }

    @Override
    public void execute() {
        System.out.println(Arrays.toString(list));
    }
}
