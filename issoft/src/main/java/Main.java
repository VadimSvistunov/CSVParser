import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    static List<String> orders = new ArrayList<>();
    static Set<String> products = new HashSet<>();

    public static void main(String[] args) {
        String fileName = "orders.csv";
        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            List<String[]> str = reader.readAll();
            fillArray(str);
            parsingOrders();
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

    }

    private static void parsingOrders() {
        String fileName1 = "order_items.csv";
        String fileName2 = "products.csv";
        try {
            CSVReader reader1 = new CSVReader(new FileReader(fileName1));
            CSVReader reader2 = new CSVReader(new FileReader(fileName2));
            List<String[]> fisrtTable = reader1.readAll();
            List<String[]> secondTable = reader2.readAll();
            searchIDProducts(fisrtTable);
            searchPrice(fisrtTable,secondTable);

        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

    }

    private static void searchPrice(List<String[]> fisrtTable, List<String[]> secondTable) {
        Map<String, Integer> prices = new HashMap<>();
        Map<String, Integer> cost = new HashMap<>();
        for (String[] product:
                secondTable) {
            if(products.contains(product[0])) {
                prices.put(product[0], Integer.parseInt(product[2]));
                cost.put(product[0], 0);
            }
        }
        for (String[] product:
                fisrtTable) {
            if(products.contains(product[1])) {
                cost.put(product[1], cost.get(product[1]) + Integer.parseInt(product[2]));
            }
        }
        
    }

    private static void fillArray(List<String[]> str) {
        for (String[] line :
             str) {
            if(line[1].contains("2021-01-21")) {
                orders.add(line[0]);
            }
        }
    }

    private static void searchIDProducts(List<String[]> str) {
        for (String[] line :
                str) {
            for (String order :
                    orders) {
                if (line[0].contains(order)) {
                    products.add(line[1]);
                }
            }
        }
    }



}
