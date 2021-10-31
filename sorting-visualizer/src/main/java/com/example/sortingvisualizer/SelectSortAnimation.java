package com.example.sortingvisualizer;

public class SelectSortAnimation {
    private double[] array;
    private RectangleBar[] rectangles;
    private SortingQueue sortingQueue;
    private boolean running = false;

    public SelectSortAnimation(double[] array, RectangleBar[] rectangles) {
        this.array = array;
        this.rectangles = rectangles;
        sortingQueue = new SortingQueue(rectangles);
    }

    private void swap(int p1, int p2) {
        double temp = array[p1];
        array[p1] = array[p2];
        array[p2] = temp;
    }

    public void play() {
        sortingQueue.clear();
        running = true;

        for(int i = 0, min; i < array.length; i ++) {
            min = i;
            for(int j = i + 1; j < array.length; j ++) {
                sortingQueue.addEntry(i, j, SortingQueue.CHECK);
                if(array[j] < array[min])
                    min = j;
            }
            System.out.println(min);
            swap(i, min);
            System.out.println(i + " " + min);
            sortingQueue.addEntry(i, min, SortingQueue.SWAP);
        }

        sortingQueue.startAnimation();
    }

    public boolean isRunning() {
        return sortingQueue.isRunning();
    }


    public void stop() {
        sortingQueue.stopAnimation();
    }
}
