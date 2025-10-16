📘 Accounting Ledger (Java CLI Application)
🧾 Overview

The Accounting Ledger is a Java-based Command Line Interface (CLI) application that allows users to record, track, and report financial transactions — such as deposits and payments.

All transactions are stored in a CSV file (transactions.csv), and the user can view them using various filters like Month-to-Date, Year-to-Date, or Vendor Search.

This project is built as part of the Pluralsight Capstone 1: Accounting Ledger Application.

⚙️ Features
🏠 Home Screen

D) Add Deposit — Add a positive amount transaction.

P) Make Payment — Add a negative amount transaction.

L) Open Ledger screen.

X) Exit the application.

📒 Ledger Menu

Displays transaction entries (newest first) and includes:

A) All — Shows all transactions

D) Deposits — Shows only positive transactions

P) Payments — Shows only negative transactions

R) Reports — Opens report menu

H) Home — Returns to the Home screen

📊 Reports Menu

1) Month-To-Date — Shows transactions from the start of the current month

2) Previous Month — Shows transactions from the previous month

3) Year-To-Date — Shows transactions from the start of the current year

4) Previous Year — Shows transactions from the previous year

5) Search by Vendor — Displays all transactions from a specific vendor

0) Back — Returns to the Ledger menu

🧩 Folder Structure
AccountingLedger/
├── src/
│   └── ledger/
│       ├── Main.java
│       ├── Transaction.java
│       ├── TransactionService.java
│       └── data/
│           └── transactions.csv
├── .idea/
├── AccountingLedger.iml
└── README.md

🧰 Technologies Used

Language: Java 17+

IDE: IntelliJ IDEA

Data Storage: CSV file (transactions.csv)

Libraries: Java Standard Library (no external dependencies)

🚀 How to Run
In IntelliJ IDEA:

Open IntelliJ IDEA

Click File → Open and select the AccountingLedger folder

Run Main.java (green ▶️ icon beside the main() method)

Use the CLI menu to add deposits, payments, and view reports

📁 Data File

All transactions are stored in:

—->src/ledger/data/transactions.csv


Each record is saved in the following format:

     date|time|description|vendor|amount


Example:

2025-10-12|09:15:00|Salary|CompanyXYZ|5000.00
2025-10-12|14:10:00|Groceries|SuperMart|-350.50


Interesting Piece of Code

One interesting part of my project is the Month-to-Date (MTD) report filter, which automatically shows all transactions from the start of the current month up to today.

📜 Code Snippet
		private static void monthToDate(List<Transaction> all) {
		    YearMonth current = YearMonth.now();
		    LocalDate start = current.atDay(1);
		    LocalDate end = LocalDate.now();
		    displayTransactions(filterByDate(all, start, end));
		}

🧠 Explanation

YearMonth.now() retrieves the current month and year.

current.atDay(1) sets the start date to the 1st day of that month.

LocalDate.now() marks the end date as today.

Then it displays only the transactions within that date range.

This logic makes the Reports feature dynamic — every time you run the program, it automatically adjusts to the current month without hardcoding any dates, just like in real accounting software.
