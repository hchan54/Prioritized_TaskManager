//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Task Manager Tester!
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
// Online Sources:  Practice Quiz 11a for testing dequeue and enqueue
//
///////////////////////////////////////////////////////////////////////////////

import java.util.NoSuchElementException;

/**
 * This class is responsible for checking the correctness of various methods in
 * the Prioritized Task Manager Assignment
 *
 */
public class TaskQueueTester {

    /**
     * Tests the correctness of the compareTo() method for the time
     *
     * @return true if all the implementation passes all test cases
     */
    public static boolean testCompareToTime() {
        //compare Task time but different title, description, and priority level
        Task task1 = new Task("A", "N/A", 1, PriorityLevel.LOW);
        Task task2 = new Task("B", "A/N", 1, PriorityLevel.HIGH);

        //larger time
        Task task3 = new Task("B", "A/N", 2, PriorityLevel.HIGH);

        //smaller time
        Task task4 = new Task("B", "A/N", 0, PriorityLevel.HIGH);

        //test when times are the same
        int expectedEqual = 0;
        if (task1.compareTo(task2, CompareCriteria.TIME) != expectedEqual) return false;

        //test compare to a larger time
        int expectedGreater = -1;
        if (task1.compareTo(task3, CompareCriteria.TIME) != expectedGreater) return false;

        //test compare to a smaller time
        int expectedLess = 1;
        if (task1.compareTo(task4, CompareCriteria.TIME) != expectedLess) return false;

        return true; //if all test passed
    }

    /**
     * Tests the correctness of compareTo() method for the title
     *
     * @return true if all the implementation passes all test cases
     */
    public static boolean testCompareToTitle() {
        //compare Task title but different time, description, and priority level
        Task task1 = new Task("B", "N/A", 1, PriorityLevel.LOW);
        Task task2 = new Task("B", "A/N", 1, PriorityLevel.HIGH);

        //lowercase letter
        Task task3 = new Task("b", "A/N", 2, PriorityLevel.HIGH);

        //smaller title
        Task task4 = new Task("C", "A/N", 2, PriorityLevel.HIGH);

        //larger title
        Task task5 = new Task("A", "A/N", 0, PriorityLevel.HIGH);

        //test when title are the same
        int expectedEqual = 0;
        if (task1.compareTo(task2, CompareCriteria.TITLE) != expectedEqual) return false;

        //test compare when the title is lowercase
        int expectedLowercase = 32;
        if (task1.compareTo(task3, CompareCriteria.TITLE) != expectedLowercase) return false;

        //test compare to a larger title
        int expectedGreater = 1;
        if (task1.compareTo(task4, CompareCriteria.TITLE) != expectedGreater) return false;

        //test compare to a smaller title
        int expectedLess = -1;
        if (task1.compareTo(task5, CompareCriteria.TITLE) != expectedLess) return false;

        return true; //if all tests passed
    }


    /**
     * Tests the correctness of compareTo() method for the level
     *
     * @return true if all the implementation passes all test cases
     */
    public static boolean testCompareToLevel() {
        //compare Task level but different title, time, and description

        Task task1 = new Task("A", "N/A", 1, PriorityLevel.LOW);
        Task task2 = new Task("B", "A/N", 2, PriorityLevel.LOW);

        //higher priority
        Task task3 = new Task("B", "A/N", 2, PriorityLevel.HIGH);

        //lower priority
        Task task4 = new Task("B", "A/N", 2, PriorityLevel.OPTIONAL);


        //test when title are the same
        int expectedEqual = 0;
        if (task1.compareTo(task2, CompareCriteria.LEVEL) != expectedEqual) return false;

        //test compare to a greater level
        int expectedGreater = -2;
        if (task1.compareTo(task3, CompareCriteria.LEVEL) != expectedGreater) return false;

        //test compare to a smaller level
        int expectedLess = 1;
        if (task1.compareTo(task4, CompareCriteria.LEVEL) != expectedLess) return false;


        return true; //if all tests passed
    }

    /**
     * Tests the correctness of the enqueue() method
     *
     * @return true if all the implementation passes all test cases
     */
    public static boolean testEnqueue() {
        //sizes that are altered for exceptions but cant be checked in the try block
        int actualSize = 0;
        int actualSize1 = 0;

        //expected size after the try exception block
        int expectedSize = 2;
        int expectedSize1 = 0;

        //test exception for when TaskQueue is at maximum capacity
        try {
            int actualCapacity = 2;
            TaskQueue taskQueue = new TaskQueue(actualCapacity, CompareCriteria.TIME);
            //add tasks until capacity is surpassed
            taskQueue.enqueue(new Task("A", "N/A", 1, PriorityLevel.LOW));
            taskQueue.enqueue(new Task("B", "N/A", 1, PriorityLevel.LOW));
            actualSize = taskQueue.size(); //update the size
            taskQueue.enqueue(new Task("C", "N/A", 1, PriorityLevel.LOW));

            return false; //exception was not thrown
        } catch (IllegalStateException e) { //catch exception thrown when queue is full
            if (e.getMessage() == null || e.getMessage().isBlank()) return false;
        } catch (Exception e) {
            return false; //any other exception is not good
        }

        //size should be 2
        if (expectedSize != actualSize) return false;

        //test exception when a completed task is added
        try {
            int actualCapacity = 2;
            TaskQueue taskQueue = new TaskQueue(actualCapacity, CompareCriteria.TIME);
            Task task = new Task("A", "N/A", 1, PriorityLevel.LOW);
            expectedSize1 = taskQueue.size();
            task.markCompleted(); //set the task to add as completed
            taskQueue.enqueue(task);

            return false; //exception was not thrown
        } catch (IllegalArgumentException e) { //catch exception when a completed item is added
            if (e.getMessage() == null || e.getMessage().isBlank()) return false;
        } catch (Exception e) { //any other exception is not good
            return false;
        }

        //check that the size was not changed
        if (expectedSize1 != actualSize1) return false;

        { //test enqueue task so it works accordingly
            TaskQueue taskQueue = new TaskQueue(10, CompareCriteria.TIME); //priority queue compare based on time

            //tasks to add
            Task task1 = new Task("A", "N/A", 96, PriorityLevel.LOW);
            Task task2 = new Task("B", "hi", 75, PriorityLevel.LOW);
            Task task3 = new Task("C", "bye", 32, PriorityLevel.OPTIONAL);
            Task task4 = new Task("D", "bye", 50, PriorityLevel.OPTIONAL);
            Task task5 = new Task("E", "bye", 72, PriorityLevel.OPTIONAL);
            Task task6 = new Task("F", "bye", 16, PriorityLevel.OPTIONAL);
            Task task7 = new Task("G", "bye", 4, PriorityLevel.OPTIONAL);
            Task task8 = new Task("H", "bye", 34, PriorityLevel.OPTIONAL);
            Task task9 = new Task("I", "bye", 5, PriorityLevel.OPTIONAL);
            Task task10 = new Task("J", "bye", 83, PriorityLevel.OPTIONAL);

            //enqueue in order
            taskQueue.enqueue(task1);
            taskQueue.enqueue(task2);
            taskQueue.enqueue(task3);
            taskQueue.enqueue(task4);
            taskQueue.enqueue(task5);
            taskQueue.enqueue(task6);
            taskQueue.enqueue(task7);
            taskQueue.enqueue(task8);
            taskQueue.enqueue(task9);
            taskQueue.enqueue(task10);

            //create a temp copy to check contents
            Task[] tasks = taskQueue.getHeapData();

            //ensure that the tasks are ordered as expected
            if (!tasks[0].getTitle().equals("A")) return false;

            if (!tasks[1].getTitle().equals("J")) return false;

            if (!tasks[2].getTitle().equals("C")) return false;

            if (!tasks[3].getTitle().equals("D")) return false;

            if (!tasks[4].getTitle().equals("B")) return false;

            if (!tasks[5].getTitle().equals("F")) return false;

            if (!tasks[6].getTitle().equals("G")) return false;

            if (!tasks[7].getTitle().equals("H")) return false;

            if (!tasks[8].getTitle().equals("I")) return false;

            if (!tasks[9].getTitle().equals("E")) return false;

            //check that size was changed properly
            int expected = 10;
            if (taskQueue.size() != expected) return false;
        }


        {//test enqueue for the level
            TaskQueue taskQueue = new TaskQueue(4, CompareCriteria.LEVEL); //queue to compare the level

            //tasks to add
            Task task1 = new Task("A", "N/A", 1, PriorityLevel.HIGH);
            Task task2 = new Task("a", "hi", 3, PriorityLevel.LOW);
            Task task3 = new Task("A", "bye", 3, PriorityLevel.URGENT);

            //enqueue in order
            taskQueue.enqueue(task1);
            taskQueue.enqueue(task2);
            taskQueue.enqueue(task3);

            //store a deep copy to check its contents
            Task[] tasks = taskQueue.getHeapData();

            //double check that task3 is first
            if (!tasks[0].getDescription().equals("bye")) return false;

            //check that task2 is in the middle of the queue
            if (!tasks[1].getDescription().equals("hi")) return false;

            //check that task1 is last
            if (!tasks[2].getDescription().equals("N/A")) return false;

            //check that size was changed accordingly
            int expected = 3;
            if (taskQueue.size() != expected) return false;
        }

        { //test enqueue task with same compareCriteria at the front of the heap
            TaskQueue taskQueue = new TaskQueue(4, CompareCriteria.TITLE); //queue to compare the level

            //tasks to add
            Task task1 = new Task("A", "N/A", 1, PriorityLevel.LOW);
            Task task2 = new Task("a", "hi", 3, PriorityLevel.LOW);
            Task task3 = new Task("A", "bye", 3, PriorityLevel.LOW);

            //enqueue in order
            taskQueue.enqueue(task1);
            taskQueue.enqueue(task2);
            taskQueue.enqueue(task3);

            //create a temp copy to check contents
            Task[] tasks = taskQueue.getHeapData();

            //double check that task1 is first
            if (!tasks[0].getDescription().equals("N/A")) return false;

            //check that task2 is in the middle of the queue
            if (!tasks[1].getDescription().equals("hi")) return false;

            //check that task3 is last
            if (!tasks[2].getDescription().equals("bye")) return false;

            //check that size was updated accordingly
            int expected = 3;
            if (taskQueue.size() != expected) return false;
        }

        return true; //all tests passed
    }

    /**
     * Tests the correctness of the dequeue() method
     *
     * @return true if all the implementation passes all test cases
     */
    public static boolean testDequeue() {
        try { //test exception thrown when the heap is empty
            TaskQueue taskQueue = new TaskQueue(4, CompareCriteria.TIME);

            taskQueue.dequeue(); //dequeue empty heap

            return false; //no exception was thrown
        } catch (NoSuchElementException e) { //exception for dequeue empty heap
            if (e.getMessage() == null || e.getMessage().isBlank()) return false;
        } catch (Exception e) { //any other exception is not good
            return false;
        }

        {//dequeue from heap with one task
            TaskQueue taskQueue = new TaskQueue(4, CompareCriteria.TIME); //priority queue that prioritizes time
            //task to add
            Task task1 = new Task("A", "N/A", 1, PriorityLevel.LOW);

            //add the task
            taskQueue.enqueue(task1);

            //dequeue and store to check the value
            Task dequeued = taskQueue.dequeue();

            //check that size was updated accordingly
            int expectedSize = 0;
            if (taskQueue.size() != expectedSize) return false;

            //check that the dequeued task is the one in the heap
            if (dequeued != task1) return false;
        }

        {//dequeue from full heap
            TaskQueue taskQueue = new TaskQueue(7, CompareCriteria.TIME);
            Task task1 = new Task("A", "N/A", 47, PriorityLevel.LOW);
            Task task2 = new Task("B", "hi", 38, PriorityLevel.LOW);
            Task task3 = new Task("C", "bye", 22, PriorityLevel.LOW);
            Task task4 = new Task("D", "nah", 10, PriorityLevel.LOW);
            Task task5 = new Task("E", "bye", 15, PriorityLevel.LOW);
            Task task6 = new Task("F", "bye", 20, PriorityLevel.URGENT);
            Task task7 = new Task("G", "bye", 5, PriorityLevel.URGENT);


            //enqueue
            taskQueue.enqueue(task1);
            taskQueue.enqueue(task2);
            taskQueue.enqueue(task3);
            taskQueue.enqueue(task4);
            taskQueue.enqueue(task5);
            taskQueue.enqueue(task6);
            taskQueue.enqueue(task7);

            //dequeue
            if (taskQueue.dequeue() != task1) return false;

            //create a temp copy to check contents
            Task[] tasks = taskQueue.getHeapData();

            //check elements after dequeueing
            if (!tasks[0].getTitle().equals("B")) return false;
            if (!tasks[1].getTitle().equals("E")) return false;
            if (!tasks[2].getTitle().equals("C")) return false;
            if (!tasks[3].getTitle().equals("D")) return false;
            if (!tasks[4].getTitle().equals("G")) return false;
            if (!tasks[5].getTitle().equals("F")) return false;
            if (tasks[6] != null) return false;

            int expected = 6;
            if (taskQueue.size() != expected) return false;

        }

        return true; //all tests passed
    }

    /**
     * Tests the correctness of the peek() method
     *
     * @return true if all the implementation passes all test cases
     */
    public static boolean testPeek() {
        try {//test exception on empty heap
            TaskQueue taskQueue = new TaskQueue(4, CompareCriteria.TIME);

            taskQueue.peekBest(); //peek the empty heap

            return false; //exception was not thrown
        } catch (NoSuchElementException e) { //exception should be thrown
            if (e.getMessage() == null || e.getMessage().isBlank()) return false;
        } catch (Exception e) {
            return false; //any other exception is not good
        }

        //create a priority queue that prioritizes using time that will be peeked
        TaskQueue taskQueue = new TaskQueue(4, CompareCriteria.TIME);

        //tasks to add to the queue
        Task task1 = new Task("A", "N/A", 1, PriorityLevel.LOW);
        Task task2 = new Task("B", "hi", 3, PriorityLevel.LOW);
        Task task3 = new Task("B", "bye", 1, PriorityLevel.URGENT);

        //add the tasks
        taskQueue.enqueue(task1);
        taskQueue.enqueue(task2);
        taskQueue.enqueue(task3);

        //peek the priority queue
        if (taskQueue.peekBest() != task2) return false;

        //check that the size was updated correctly
        int expectedSize = 3; //stays the same
        if (taskQueue.size() != expectedSize) return false;

        //create a temp copy to check contents
        Task[] tasks = taskQueue.getHeapData();

        //check every task to make sure that nothing was changed
        if (!tasks[0].getDescription().equals("hi")) return false;
        if (!tasks[1].getDescription().equals("N/A")) return false;
        if (!tasks[2].getDescription().equals("bye")) return false;

        return true; //if all tests passed
    }

    /**
     * Tests the correctness of the de() method
     *
     * @return true if all the implementation passes all test cases
     */
    public static boolean testReprioritize() {
        //task queue that prioritizes time
        TaskQueue taskQueue = new TaskQueue(7, CompareCriteria.TIME);

        //tasks to add
        Task task1 = new Task("A", "N/A", 47, PriorityLevel.URGENT);
        Task task2 = new Task("B", "hi", 38, PriorityLevel.LOW);
        Task task3 = new Task("C", "bye", 22, PriorityLevel.MEDIUM);
        Task task4 = new Task("D", "nah", 10, PriorityLevel.OPTIONAL);
        Task task5 = new Task("E", "bye", 15, PriorityLevel.OPTIONAL);
        Task task6 = new Task("F", "bye", 20, PriorityLevel.HIGH);
        Task task7 = new Task("G", "bye", 5, PriorityLevel.LOW);

        //enqueue
        taskQueue.enqueue(task1);
        taskQueue.enqueue(task2);
        taskQueue.enqueue(task3);
        taskQueue.enqueue(task4);
        taskQueue.enqueue(task5);
        taskQueue.enqueue(task6);
        taskQueue.enqueue(task7);

        {//test Reprioritize by Level
            taskQueue.reprioritize(CompareCriteria.LEVEL); //reprioritize by level

            //test that size is not changed
            int expectedSize = 7;
            if (taskQueue.size() != expectedSize) return false;

            //create a temp copy to check contents
            Task[] tasks = taskQueue.getHeapData();

            //check every task is ordered accordingly
            if (!tasks[0].getTitle().equals("A")) return false;
            if (!tasks[1].getTitle().equals("B")) return false;
            if (!tasks[2].getTitle().equals("F")) return false;
            if (!tasks[3].getTitle().equals("D")) return false;
            if (!tasks[4].getTitle().equals("E")) return false;
            if (!tasks[5].getTitle().equals("C")) return false;
            if (!tasks[6].getTitle().equals("G")) return false;

        }

        {//test Reprioritize by Time
            taskQueue.reprioritize(CompareCriteria.TIME); //requeue by time

            //check that size is not changed
            int expectedSize = 7;
            if (taskQueue.size() != expectedSize) return false;

            //create a temp copy to check contents
            Task[] tasks = taskQueue.getHeapData();

            //check every task is ordered accordingly
            if (!tasks[0].getTitle().equals("A")) return false;
            if (!tasks[1].getTitle().equals("B")) return false;
            if (!tasks[2].getTitle().equals("C")) return false;
            if (!tasks[3].getTitle().equals("D")) return false;
            if (!tasks[4].getTitle().equals("E")) return false;
            if (!tasks[5].getTitle().equals("F")) return false;
            if (!tasks[6].getTitle().equals("G")) return false;

        }

        {//test Reprioritize by Title
            taskQueue.reprioritize(CompareCriteria.TITLE); //requeue by title

            //check that size is not changed
            int expectedSize = 7;
            if (taskQueue.size() != expectedSize) return false;

            //create a temp copy to check contents
            Task[] tasks = taskQueue.getHeapData();

            //check every task is ordered accordingly
            if (!tasks[0].getTitle().equals("A")) return false;
            if (!tasks[1].getTitle().equals("B")) return false;
            if (!tasks[2].getTitle().equals("C")) return false;
            if (!tasks[3].getTitle().equals("D")) return false;
            if (!tasks[4].getTitle().equals("E")) return false;
            if (!tasks[5].getTitle().equals("F")) return false;
            if (!tasks[6].getTitle().equals("G")) return false;
        }

        return true; //all tests passed
    }



    public static void main(String[] args) {
        String printFormat = "%-29s %s\n";

        System.out.printf(printFormat, "testCompareToTime(): ", testCompareToTime());
        System.out.printf(printFormat, "testCompareToTitle(): ", testCompareToTitle());
        System.out.printf(printFormat, "testCompareToLevel(): ", testCompareToLevel());
        System.out.printf(printFormat, "testEnqueue(): ", testEnqueue());
        System.out.printf(printFormat, "testDequeue(): ", testDequeue());
        System.out.printf(printFormat, "testPeek(): ", testPeek());
        System.out.printf(printFormat, "testReprioritize(): ", testReprioritize());

    }
}
