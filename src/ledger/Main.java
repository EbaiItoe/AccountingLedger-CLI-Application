package ledger;

import java.time.*;
import java.util.*;

public class Main {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        TransactionService.ensureFileExistsSilently();
        TransactionService.seedExampleIfEmpty();

        while (true) {
            System.out.println("\n===== ACCOUNTING LEDGER =====");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment (Debit)");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");
            System.out.print("Choose option: ");
            String choice = sc.nextLine().trim().toUpperCase();

            switch (choice) {
                case "D": addTransaction(true); break;
                case "P": addTransaction(false); break;
                case "L": showLedger(); break;
                case "X": System.out.println("Goodbye!"); return;
                default: System.out.println("Invalid choice!");
            }
        }
    }

    private static void addTransaction(boolean isDeposit) {
        System.out.print("Description: ");
        String desc = sc.nextLine().trim();
        System.out.print("Vendor: ");
        String vendor = sc.nextLine().trim();
        System.out.print("Amount: ");
        double amt = Double.parseDouble(sc.nextLine().trim());
        if (!isDeposit) amt = -Math.abs(amt);

        Transaction t = new Transaction(LocalDate.now(), LocalTime.now().withNano(0), desc, vendor, amt);
        TransactionService.addTransaction(t);
        System.out.println("Transaction saved successfully!");
    }

    private static void showLedger() {
        List<Transaction> list = TransactionService.readTransactions();
        if (list.isEmpty()) {
            System.out.println("No transactions found!");
            return;
        }

        while (true) {
            System.out.println("\n===== LEDGER MENU =====");
            System.out.println("A) All");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home");
            System.out.print("Choose option: ");
            String choice = sc.nextLine().trim().toUpperCase();

            switch (choice) {
                case "A": displayTransactions(list); break;
                case "D": displayTransactions(filterDeposits(list)); break;
                case "P": displayTransactions(filterPayments(list)); break;
                case "R": showReports(list); break;
                case "H": return;
                default: System.out.println("Invalid choice!");
            }
        }
    }

    private static void displayTransactions(List<Transaction> list) {
        list.sort(Comparator.comparing(Transaction::getDate)
                .thenComparing(Transaction::getTime)
                .reversed());

        System.out.println("\nDate | Time | Description | Vendor | Amount");
        System.out.println("--------------------------------------------------------");
        for (Transaction t : list) {
            System.out.println(t);
        }
    }

    private static List<Transaction> filterDeposits(List<Transaction> list) {
        List<Transaction> deposits = new ArrayList<>();
        for (Transaction t : list) if (t.getAmount() > 0) deposits.add(t);
        return deposits;
    }

    private static List<Transaction> filterPayments(List<Transaction> list) {
        List<Transaction> payments = new ArrayList<>();
        for (Transaction t : list) if (t.getAmount() < 0) payments.add(t);
        return payments;
    }

    private static void showReports(List<Transaction> all) {
        while (true) {
            System.out.println("\n===== REPORTS MENU =====");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("0) Back");
            System.out.print("Choose option: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1": monthToDate(all); break;
                case "2": previousMonth(all); break;
                case "3": yearToDate(all); break;
                case "4": previousYear(all); break;
                case "5": searchByVendor(all); break;
                case "0": return;
                default: System.out.println("Invalid choice!");
            }
        }
    }

    private static void monthToDate(List<Transaction> all) {
        YearMonth current = YearMonth.now();
        LocalDate start = current.atDay(1);
        LocalDate end = LocalDate.now();
        displayTransactions(filterByDate(all, start, end));
    }

    private static void previousMonth(List<Transaction> all) {
        YearMonth prev = YearMonth.now().minusMonths(1);
        LocalDate start = prev.atDay(1);
        LocalDate end = prev.atEndOfMonth();
        displayTransactions(filterByDate(all, start, end));
    }

    private static void yearToDate(List<Transaction> all) {
        LocalDate start = LocalDate.of(LocalDate.now().getYear(), 1, 1);
        LocalDate end = LocalDate.now();
        displayTransactions(filterByDate(all, start, end));
    }

    private static void previousYear(List<Transaction> all) {
        int year = LocalDate.now().getYear() - 1;
        LocalDate start = LocalDate.of(year, 1, 1);
        LocalDate end = LocalDate.of(year, 12, 31);
        displayTransactions(filterByDate(all, start, end));
    }

    private static List<Transaction> filterByDate(List<Transaction> list, LocalDate start, LocalDate end) {
        List<Transaction> filtered = new ArrayList<>();
        for (Transaction t : list) {
            if ((t.getDate().isEqual(start) || t.getDate().isAfter(start)) &&
                    (t.getDate().isEqual(end) || t.getDate().isBefore(end))) {
                filtered.add(t);
            }
        }
        return filtered;
    }

    private static void searchByVendor(List<Transaction> all) {
        System.out.print("Enter vendor name: ");
        String vendor = sc.nextLine().trim().toLowerCase();

        List<Transaction> matches = new ArrayList<>();
        for (Transaction t : all) {
            if (t.getVendor().toLowerCase().contains(vendor)) matches.add(t);
        }

        if (matches.isEmpty()) System.out.println("No transactions found for vendor: " + vendor);
        else displayTransactions(matches);
    }
}