# Hospital Lab Test Management

Java console application for 23CSR306 Java Programming, Assignment II (OOP Mini Project), Specification 20.

## Problem Statement

Design and implement a Java console application for a Hospital Lab Test Management system that handles patients, tests, orders, samples, results, and billing. The application should demonstrate object-oriented principles and ensure traceability from order to result.

## Classes

| Class | Purpose |
|---|---|
| Patient | Patient details (id, name, age, phone) |
| LabTest | Test catalogue entry (code, name, price) |
| TestOrder | An order for one patient; holds its order items and invoice |
| TestOrderItem | One test within an order; tracks status, sample and result |
| Sample | Specimen collected for a specific order item |
| TestResult | Result recorded for a specific order item |
| Invoice | Charges plus 12% tax; payment closes the invoice |
| HospitalLabApp | Main class with the menu |

## Business Rules Implemented

1. A test order must be created before sample collection.
2. Samples are associated with specific order items.
3. Results are recorded only for valid, collected samples.
4. Invoices include test charges and 12% tax; payment closes the invoice.
5. Each result links directly to its order item.

## Menu Options

1. Add Patient
2. Add Lab Test
3. Create Test Order
4. Collect Sample
5. Record Result
6. Generate Invoice
7. Record Payment
8. Display Orders
9. Exit

## How to Compile and Run

Requires JDK 8 or later.

```
javac *.java
java HospitalLabApp
```

In Eclipse: create a Java Project, copy all `.java` files into `src`, then right-click `HospitalLabApp.java` and choose Run As > Java Application.

## Sample Run

1. Add Patient: Ravi, 25, 1234567890 (ID P001)
2. Add Lab Test: CBC, Complete Blood Count, 500
3. Add Lab Test: LP, Lipid Profile, 800
4. Create Test Order: P001 with CBC and LP (ID O001, items O001-1 and O001-2)
5. Collect Sample: O001 / O001-1 / Blood / y, then O001 / O001-2 / Serum / y
6. Record Result: O001-1 = 13.5 g/dl, O001-2 = 180 mg/dl
7. Generate Invoice: subtotal Rs.1300.00, tax Rs.156.00, total Rs.1456.00
8. Record Payment: 1456 via UPI, invoice closed
9. Display Orders: order summary, results report, invoice

IDs are case-insensitive but must start with a letter (for example `O001`, not `0001`).

## Student Details

- Name: Harivishnu
- Roll No: 717825I317
- Department: Artificial Intelligence and Data Science, Karpagam College of Engineering
