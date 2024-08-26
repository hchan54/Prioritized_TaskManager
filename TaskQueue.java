//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Task Manager!
// Course:   CS 300 Summer 2024
//
// Author:   Hunter
// Email:    hchan54@wisc.edu
// Lecturer: (Andy Kuemmel)
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name:    NOT ALLOWED
// Partner Email:   NOT ALLOWED
//
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//   ___ Write-up states that pair programming is allowed for this assignment.
//   ___ We have both read and understand the course Pair Programming Policy.
//   ___ We have registered our team prior to the team registration deadline.
//
//////////////////////// ASSISTANCE/HELP CITATIONS ////////////////////////////
//
// Persons:         none
// Online Sources:  Zybooks chapter 13
//
///////////////////////////////////////////////////////////////////////////////


import java.util.NoSuchElementException;

/**
 * This class represents the TaskQueue that prioritizes tasks based on their description
 *
 */
public class TaskQueue {
    private Task[] heapData; //oversized array that holds all the Tasks in the heap
    private CompareCriteria priorityCriteria; //the critera used to determine how to
                                              // prioritize Task in the queue
    private int size; //the number of items in the TaskQueue

    /**
     * Creates an empty TaskQueue with the given capacity and priority criteria
     *
     */
    public TaskQueue(int capacity, CompareCriteria priorityCriteria) {
        this.heapData = new Task[capacity];
        this.priorityCriteria = priorityCriteria;
        this.size = 0;
    }

    /**
     * Gets the criteria use to prioritize in this TaskQueue
     *
     * @return  the prioritization criteria of this TaskQueue
     */
    public CompareCriteria getPriorityCriteria() {
        return this.priorityCriteria;
    }

    /**
     * Reports if the TaskQueue is empty
     *
     * @return true if the TaskQueue is empty
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Reports the size of the TaskQueue
     *
     * @return the number of Tasks in this TaskQueue
     */
    public int size() {
        return this.size;
    }

    /**
     * Gets the Task in the TaskQueue that has the highest priority without removing it
     *
     * @return the Task in the queue with the highest priority
     * @throws NoSuchElementException with a descriptive message that informs that the
     *                                TaskQueue is empty
     */
    public Task peekBest() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty"); //check if the heap is empty
        return heapData[0]; //return the root
    }

    /**
     * Adds the newTask to this priority queue
     *
     * @param newTask the new task to add to the queue
     * @throws IllegalStateException with a descriptive message if the priority queue is full
     * @throws IllegalArgumentException with a descriptive message if the Task is
     *                                  already completed
     */
    public void enqueue (Task newTask) {
        if (size() == heapData.length) {//check if the heap is at max capacity
            throw new IllegalStateException("Queue is currently full");
        }

        if (newTask.isCompleted()) { //check if the item to enqueue is completed
            throw new IllegalArgumentException("Task is already completed");
        }

        //add newTask at the end of the heap
        int index = size;
        this.heapData[index] = newTask;
        this.size++;

        //restore the heap order by percolating upwards
        percolateUp(index);
    }

    /**
     * Fixes one heap violation by moving up the heap
     *
     * @param index the index of the element where the violation may be
     */
    protected void percolateUp(int index) {
        //iterate until the root
        while (index > 0) {
            //holds the parent index
            int parentIndex = (index - 1) / 2;
            //if the current element has a lower or equal priority, the heap property is satisfied
            if (this.heapData[index].compareTo(this.heapData[parentIndex], getPriorityCriteria()) <= 0) {
                return;
            } else { //otherwise swap the index with the parent index
                swap(parentIndex, index);
                index = parentIndex;
            }
        }
    }

    /**
     * This helper method swaps the value at the index and the parent index
     *
     * @param parentIndex the index of the parent to swap
     * @param childIndex the index of the child to swap
     */
    private void swap(int parentIndex, int childIndex) {
        Task temp = heapData[parentIndex];
        heapData[parentIndex] = heapData[childIndex];
        heapData[childIndex] = temp;
    }

    /**
     * Gets and removes the Task with the highest priority
     *
     * @return the Task in the queue with the highest priority
     * @throws NoSuchElementException with descriptive message if this TaskQueue is empty
    */
    public Task dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty"); //ensure the queue is not empty
        Task highestPriority = heapData[0]; //store root
        this.heapData[0] = heapData[size - 1]; //remove last data
        this.heapData[size - 1] = null; // Clear the last element
        this.size--;
        percolateDown(0);
        return highestPriority;
    }

    /**
     * Fixes one heap violation by moving it down the heap
     *
     * @param index  the index of the element where the violation may be
     */
    protected void percolateDown(int index) {
        int childIndex = index * 2 + 1; //hold the index of the child
        Task value = heapData[index];

        //iterate as long as the child index is less than the size
        while (childIndex < size()) {
            //assume the left child is the max
            int maxIndex = childIndex;

            //check if the right child exists and is greater than the left child
            if (childIndex + 1 < size() && heapData[childIndex + 1].compareTo(heapData[childIndex], getPriorityCriteria()) > 0) {
                maxIndex = childIndex + 1; //change the maxIndex to the right child
            }

            //if the current element is greater or equal, the heap property is satisfied
            if (value.compareTo(this.heapData[maxIndex], getPriorityCriteria()) >= 0) {
                break;
            }

            //swap the current node with the larger child
            this.heapData[index] = this.heapData[maxIndex];
            index = maxIndex;
            childIndex = 2 * index + 1;
        }

        //place the original value in its correct position
        this.heapData[index] = value;
    }

    /**
     * Changes the priority criteria of this priority queue and fixes it so
     * that it is a proper priority queue based on the new criteria
     *
     * @param priorityCriteria the new criteria that should be used to prioritize the Tasks
     *                         in queue
     */
    public void reprioritize(CompareCriteria priorityCriteria) {
        this.priorityCriteria = priorityCriteria; //change the compare criteria

        //copy the current heap data
        Task[] temp = getHeapData();

        //empty the current heap
        this.size = 0;
        this.heapData = new Task[this.heapData.length]; //clear heap contents

        //re-enqueue the data
        for (Task task : temp) {
            if (task != null) enqueue(task);
        }

    }

    /**
     * Creates and returns a deep copy of the heap's array of data
     *
     * @return the deep copy of the array holding the heap's data
     */
    public Task[] getHeapData() {
        Task[] deepCopy = new Task[this.heapData.length]; //new array
        System.arraycopy(this.heapData, 0, deepCopy, 0, this.heapData.length); //copy all tasks to copy
        return deepCopy;
    }
}
