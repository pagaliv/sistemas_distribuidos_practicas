package Practica3.ejercicio2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        String file1 = "C:\\Users\\pagaliv\\Documents\\GitHub\\sistemas_distribuidos_practicas\\t\\hola2.txt";
        String file2 = "C:\\Users\\pagaliv\\Documents\\GitHub\\sistemas_distribuidos_practicas\\t\\pagaliv.txt";
        String file3 = "C:\\Users\\pagaliv\\Documents\\GitHub\\sistemas_distribuidos_practicas\\t\\pagaliv2.txt";



        long startTime = System.nanoTime();
        List<FileLineCounter> counters = new ArrayList<>();
        counters.add(new FileLineCounter(file1));
        counters.add(new FileLineCounter(file2));
        counters.add(new FileLineCounter(file3));


        for (FileLineCounter counter : counters) {
            counter.start();
        }


        long totalLines = 0;
        for (FileLineCounter counter : counters) {
            try {
                counter.join();
                totalLines += counter.getLineCount();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long endTime = System.nanoTime();
        System.out.println("Total lines (concurrent): " + totalLines);
        System.out.println("Concurrent execution time: " + (endTime - startTime) / 1_000_000 + " ms");


        startTime = System.nanoTime();
        totalLines = 0;
        totalLines += countLinesSequentially(file1);
        totalLines += countLinesSequentially(file2);
        totalLines += countLinesSequentially(file3);
        endTime = System.nanoTime();
        System.out.println("Total lines (sequential): " + totalLines);
        System.out.println("Sequential execution time: " + (endTime - startTime) / 1_000_000 + " ms");
    }

    public static long countLinesSequentially(String filePath) {
        long lineCount = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            while (reader.readLine() != null) {
                lineCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineCount;
    }
}
