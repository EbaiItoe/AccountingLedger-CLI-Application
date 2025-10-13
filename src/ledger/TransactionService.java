package ledger;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class TransactionService {
    private static final String FILE_NAME = "transactions.csv";

    public static void addTransaction(Transaction t) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            bw.write(t.toCSV());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public static List<Transaction> readTransactions() {
        List<Transaction> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(Transaction.fromCSV(line));
            }
        } catch (IOException e) {
            // File might not exist yet
        }
        return list;
    }

    public static void ensureFileExistsSilently() {
        try {
            Path path = Paths.get(FILE_NAME);
            if (!Files.exists(path)) Files.createFile(path);
        } catch (IOException e) { }
    }

    public static void seedExampleIfEmpty() {
        try {
            ensureFileExistsSilently();
            if (readTransactions().isEmpty()) {
                addTransaction(new Transaction(java.time.LocalDate.now().minusDays(1),
                        java.time.LocalTime.of(10, 0), "Example Deposit", "Bank", 1000.00));
                addTransaction(new Transaction(java.time.LocalDate.now(),
                        java.time.LocalTime.of(11, 0), "Example Payment", "Store", -150.00));
            }
        } catch (Exception ignored) { }
    }
}