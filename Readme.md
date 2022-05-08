Freezer Catalog API
Task
Create an app that publishes a REST API with methods as specified below. Write the API
code and test cases, and document the code. You may use common frameworks if you
wish. You will be asked to explain your choices.

Specification
The API is a service to create a catalog of the food in my freezer. The endpoints are
● /food
○ Method to add some food to the freezer, giving name, type &amp; quantity; returns
ID
○ Method to provide an ID &amp; get the detail of the food
○ Method update an item
● /food/search
○ Method search for food by name, type or date added

All methods should be authenticated with an API key. Data can be stored in memory or a
temporary database such as H2 so we can run it locally and test using Postman.

What you are expected to deliver
● Documented source code
● A working solution
● Test cases
● A brief summary of your implementation decisions and thoughts on possible
enhancements &amp; challenges