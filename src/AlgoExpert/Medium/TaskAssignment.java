package AlgoExpert.Medium;

import java.lang.reflect.Array;
import java.util.*;

public class TaskAssignment {
    public static void main(String[] args) {

    }

    // O(nlog(n)) time | O(n) space
    private static ArrayList<ArrayList<Integer>> taskAssignment(int k, ArrayList<Integer> tasks) {
        ArrayList<ArrayList<Integer>> pairedTasks = new ArrayList<>();
        // task duration is mapped based on duration and indices
        // map<Duration, list of indices>, there could be multiple durations with the same values
        HashMap<Integer, ArrayList<Integer>> taskDurationToIndices = getTaskDurationsToIndices(tasks);
        ArrayList<Integer> sortedTasks = tasks;
        Collections.sort(sortedTasks);

        // iterate through k workers
        for (int idx = 0; idx < k; idx++) {
            // task 1 duration is from beginning of array
            int task1Duration = sortedTasks.get(idx);
            // take the list out from the map with task 1 duration
            ArrayList<Integer> indicesWithTask1Duration = taskDurationToIndices.get(task1Duration);
            // remove the last element in the indices list and store in task1Index
            int task1Index = indicesWithTask1Duration.remove(indicesWithTask1Duration.size() - 1);

            // get task2 duration from the end of the array
            int task2SortedIndex = tasks.size() - 1 - idx;
            int task2Duration = sortedTasks.get(task2SortedIndex);
            ArrayList<Integer> indicesWithTask2Duration = taskDurationToIndices.get(task2Duration);
            // remove the last element in the indices list and store in task2Index
            int task2Index = indicesWithTask2Duration.remove(indicesWithTask2Duration.size() - 1);

            ArrayList<Integer> pairedTask = new ArrayList<>();
            pairedTask.add(task1Index);
            pairedTask.add(task2Index);
            pairedTasks.add(pairedTask);
        }
        return pairedTasks;
    }

    private static HashMap<Integer, ArrayList<Integer>> getTaskDurationsToIndices(ArrayList<Integer> tasks) {
        HashMap<Integer, ArrayList<Integer>> taskDurationsToIndices = new HashMap<>();

        for (int idx = 0; idx < tasks.size(); idx++) {
            int taskDuration = tasks.get(idx);
            if (taskDurationsToIndices.containsKey(taskDuration)) {
                taskDurationsToIndices.get(taskDuration).add(idx);
            } else {
                ArrayList<Integer> temp = new ArrayList<>();
                temp.add(idx);
                taskDurationsToIndices.put(taskDuration, temp);
            }
        }

        return taskDurationsToIndices;
    }
}
