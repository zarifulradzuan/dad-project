# dad-project
This project is a simple java application that enables one to 
-keep track of daily transactions
-keep track of who owes who what

The user part of the application implements "phantom user." 
That is, a preemptively created account with its username and password set to null. 
This enables the application to keep track of debts and such without needing both parties to register.
To take over the account, the owner would simply register as usual using the same email that was used to create the
phantom account. Of course, in a real world situation a confirmation email would be sent first, 
but this should suffice for now. 

User can edit right out of the table and it would update the database right after. 
An error message would popup if the user entered an unexpected input. However, testing has not been done extensively 
therefore unexpected behaviour should be, well, expected.



















