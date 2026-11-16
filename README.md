# Take-Home Coding Exercise: Transaction Analyzer
## Objective
Build a small program that processes a list of financial transactions and produces account-level summaries.

## How to run the solution
placeholder


## Assumptions I made
- There is no need to process the transactions chronologically, since input may not be sorted by timestamp and the transactions regardless of time will total to the same amount. Unless the program instructions explicitly asked me to check the balance at a certain time, it would be unnecessary.
- 

## Design Choices
- One of the first things I considered when understanding the transactions that this program would accept was creating an abstract class for Transactions, so it could have two child classes:
  - Debit subclass
  - Credit subclass
I ended up going against the above decision; it would be redundant because we're explicitly given the type of transaction in valid input, and they would contain the exact same methods/fields, which don't necessarily behave differently (they both just apply the transaction to the balance.
