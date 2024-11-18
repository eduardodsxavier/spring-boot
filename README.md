# A repository for my spring-boot studies

## rest-service 
A basic project to start to learn

## demo 
A REST service with get, post, put and delete.
Have a list of employees and ordere.
Go to URL/employees to see employees octions and URL/orders to see orders actions.

## ByMySelf
A REST api that manage a list of To-Do. 
### Get
- URL/todos : get the list of all todos
- URL/todos/{x} : get the todo of id = x
### Post 
- URL/todos : add a new todo, in the body put: {name: name of todo, description: description of the todo} 
### Put 
- URL/todos/{x} : update name and description of todo of id = x, if that to-do aren't in the list add it
- URL/todos/{x}/complete : change the status of todo of id = x to completed
- URL/todos/{x}/reset : change the status of todo of id = x to to-do
### Delete
- URL/todos/{x}/cancel : change the status of todo of id = x to cancel 

## database
A basic start of how to use databases in Spring-boot.
