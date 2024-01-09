# bank-api

It is a core banking API which will be for bankers to perform day to day activities related to bank customer. 

Features (Endpoints developed) :- 
1. Creating a new customer account and assigning new 10 digits account number.
2. Crediting amount to the customer's account against account number sending notification to the account holder through email.
3. Debiting money from the customer's account against account number and sending notification to account holder through email.
4. Transferring amount from one to another account through account number and sending notification to source and target account holder by email.
5. Fetching customer's bank statement and generating PDF of customer's bank statement and sending to customer's email.

Note: For Email Notification Service SMTP protocol is followed, which will take place asynchronously. For Securing the endpoints Spring Security module has been implemented for authentication and 
authorization.
