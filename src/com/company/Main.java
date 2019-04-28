package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Есть массив целых чисел, найти сумму элементов на подотрезке
 */
public class Main {
    static int regularSum(ArrayList<Integer> arrList, int index) {
        int ans = 0;
        for (int i = 0; i <= index; i++)
            ans += arrList.get(i);
        return ans;
    }

    public static void main(String[] args) {
        int k = 1;
        for (int amount = 10; amount <= 1E9; amount *= 10) {
            int sqrt = (int) Math.sqrt(amount), min = -sqrt, max = +sqrt;
            int[] values = new Random(0).ints(amount, min, max).toArray();
            int start = 5*k;
            int end = 9*k;
            k = start;
            int[] newValues = Arrays.copyOfRange(values, start, end);
            ArrayList<Integer> valuesList = new ArrayList<>();
            ArrayList<Integer> newValuesList = new ArrayList<>();
            for (int i = 0; i < values.length; i++) {
                valuesList.add(values[i]);
            }
            System.out.println(String.format("Array size: %,d elements", amount));
            System.out.println(String.format("First simple 10 elements: %s", Arrays.toString(Arrays.copyOfRange(values, 0, 9))));

            long startFOR, endFOR, startBIT, endBIT;

            startFOR = System.nanoTime();
            int sumUsingFOR = regularSum(valuesList, valuesList.size() - 1);
            endFOR = System.nanoTime();
            System.out.println(String.format("Использование обычного цикла (%s) Время %s ns", sumUsingFOR, (endFOR - startFOR)));

            BIT.constructBITree(valuesList);
            startBIT = System.nanoTime();
            int sumUsingBIT = BIT.getSum(valuesList.size() - 1);
            endBIT = System.nanoTime();
            System.out.println(String.format("Использование Фенвика (%s) Время %s ns", sumUsingBIT, (endBIT - startBIT)));

            double ratio = ((double) (endFOR - startFOR) / (double) (endBIT - startBIT));
            System.out.println(String.format("Фенвик на %.2f  %s  чем цикл\n", ratio, (endBIT - startBIT) < (endFOR - startFOR) ? "быстрее" : "медленнее"));

            System.err.println("------------------------------------");
            System.out.println("Under Array start: "+start+"\nUnder Array finish: "+end);
            System.out.println(String.format("Under Array: %s", Arrays.toString(Arrays.copyOfRange(values, start, end))));
            for (int i = 0; i < newValues.length; i++) {
                newValuesList.add(newValues[i]);
            }
            long startFORInUnderArray = System.nanoTime();
            int sumUnderArrayUnsingFOR = regularSum(newValuesList, newValuesList.size() - 1);
            long endFORInUnderArray = System.nanoTime();
            System.out.println(String.format("Использование обычного цикла для суммы подмассива(%s) Время %s ns", sumUnderArrayUnsingFOR, (endFORInUnderArray - startFORInUnderArray)));

            BIT.constructBITree(newValuesList);
            long startBITInUnderArray = System.nanoTime();
            int sumUsingBITInUnderArray = BIT.getSum(newValuesList.size() - 1);
            long endBITInUnderArray = System.nanoTime();
            System.out.println(String.format("Использование Фенвика в падомассиве (%s) Время %s ns", sumUsingBITInUnderArray, (endBITInUnderArray - startBITInUnderArray)));

            double ratio2 = ((double) (endFORInUnderArray - startFORInUnderArray) / (double) (endBITInUnderArray - startBITInUnderArray));
            System.out.println(String.format("Фенвик на %.2f  %s  чем цикл\n", ratio2, (endBITInUnderArray - startBITInUnderArray) < (endFORInUnderArray - startFORInUnderArray) ? "быстрее" : "медленнее"));
        }
    }
}
