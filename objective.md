# Take-Home Coding Exercise: Transaction Analyzer
## Objective
Build a small program that processes a list of financial transactions and produces account-level summaries.
## 
Problem Statement
You are given a list of transactions. Each transaction contains:
•	accountId
•	type, which is either credit or debit
•	amount
•	timestamp
Write code that returns the following:
1.	A summary for each account containing:
-	total credits
-	total debits
-	ending balance
-	largest transaction amount
-	number of valid transactions
-	whether the account is overdrawn at the end
2.	A topSpender, defined as the account with the highest total debits. If there is a tie, return the lexicographically smaller accountId.
## Rules
•	All accounts start with a balance of 0
•	credit increases the balance
•	debit decreases the balance
•	amount is always expected to be positive
•	Ignore invalid transactions:
o	missing or empty accountId
o	invalid type
o	amount <= 0
•	Input may not be sorted by timestamp
•	Return account summaries sorted by accountId
## Example Input
```
[
  { "accountId": "A100", "type": "credit", "amount": 500, "timestamp": "2026-05-10T10:00:00Z" },
  { "accountId": "A100", "type": "debit",  "amount": 200, "timestamp": "2026-05-10T12:00:00Z" },
  { "accountId": "B200", "type": "debit",  "amount": 50,  "timestamp": "2026-05-10T11:00:00Z" },
  { "accountId": "B200", "type": "credit", "amount": 20,  "timestamp": "2026-05-10T13:00:00Z" },
  { "accountId": "",     "type": "credit", "amount": 100, "timestamp": "2026-05-10T14:00:00Z" }
]
```
## Example Output
```
{
  "summaries": [
    {
      "accountId": "A100",
      "totalCredits": 500,
      "totalDebits": 200,
      "endingBalance": 300,
      "largestTransaction": 500,
      "transactionCount": 2,
      "overdrawn": false
    },
    {
      "accountId": "B200",
      "totalCredits": 20,
      "totalDebits": 50,
      "endingBalance": -30,
      "largestTransaction": 50,
      "transactionCount": 2,
      "overdrawn": true
    }
  ],
  "topSpender": "A100"
}
```
