
# Java customer management example

The following Java program was created as an assignement for the Object Oriented Programming 1 class of the third semester at the Harokopeio University of Athens.
## Briefing
This program accomplishes the following tasks:
```
Make new customer profile
```
```
Make new contract
```
```
Delete existing contract
```
```
Display usage statistics among all customers
```
```
Display specific customers active contracts
```

## Progress:
Complete!

## Details
### 1.Make new customer profile
***
This menu options allows for a new customer profile to be created.The system first asks for the customers tax ID and his ID number.
```
Note: If a customer profile with the same ID number or the same Tax ID exists,creation of a new profile
with the same details will not be allowed
```
If the customer doesnt exist,a random customer id is generated and added to the profile.

The program then asks for the customers first and last name,asks if customer is a student or a professional,asks for customers payment method,and if the customer wants electronic bill and finally asks if you want to make a contract for this customer.

At this point all the discounts that a customer should have have been calculated.The discounts are:

* 5% for each active contract - for a max of 15%
* 10% if the customer is a professional or 15% **or** the customer is a student
* 8% if the customer has a fixed phone plan with over 1000 minutes **or** 10% if the customer has a mobile phone plan with over 1000 minutes
* 5% if the customer pays by direct deposit or credit card
* 2% if the customer wants an electronic bill instead of a paper one

***The discounts are only considered valid if customer has atleast one active contract and they cannot exceed 45% combined***
```
This information is stored in an arrayList called CustomerList
```
### 2. Make new contract
***
First the program asks if the new contract is a fixed or a mobile contract.

Then,if this method has been called from the new customer method then no customer id is asked since it is retrieved from the new customer method,however if called from the main menu the method will ask for the customers tax id in order to find his/hers customer id.

If it finds a customer with that tax id then the program proceeds to ask for the phone number that the contract is to be created fo(and checks if it valid),otherwise it exits.
If a contract for the specific phone number exists,the expiration date for the contract is retrieved,and user input is checked afterwards to make sure that contract dates dont overlap
Finally the program asks the user how many minutes to add to the program and asks if the customer wants internet,if the customer answers positively,the program asks for the amount of data,or the internet speed depending on the contract type.

```
Note for validity check:
Greek phone numbers are 10 digits long,fixed phone numbers start with a 2,
and mobile phone numbers start with 69
```

```
This information is stored in an arrayList called contractList with two possible types:mobile and fixed
```

### 3.Delete Contract
***
First,the program asks for a tax id to lookup,after it finds the specified customer,it then lists all of his/hers contacts,and numbers them,finally it asks the user which contract they want to delete

### 4.Display specific customers active contracts
***
Prints all the active contracts that a customer has,finding the customer using the tax id

### 5.Display usage statistics among all customers
***
Displays the amount of subscribers for fixed and for mobile,and the min max and mean amounts of internet and minutes for fixed and mobile
