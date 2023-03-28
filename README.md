# RESTful Library

----

### Technologies
* Java
* Maven
* PostgreSQL
* Spring: Core, MVC, Boot
* Mockito
* JUnit5

---
### What is this application?

This application helps manage the library. By using this app, we can accomplish various tasks such as:

* Add / Delete:
  * Authors
  * Themes 
  * Books
* Create a new contract between user and library
* Close contracts
* Mark books as lost
* Extend the term of the contract
---
Usage
You need to clone this repository
```
git clone https://github.com/Jwoliv/RESTful-Library.git
```
Launch the maven
```
mvn clean install
```
Open the postman and created the templates of the request with correct url
#### Example of the url and json object
* Request for create a new user

```POST```
```
/api/users
```
```json
{
    "firstname": "UserFirstname1",
    "lastname": "UserLastname1",
    "surname": "UserSurname1",
    "email": "test@gmail.com",
    "numberOfOverdue": 0,
    "isBanned": false,
    "numberPhone": "+380984454286"
}
```
* Request for create a new author

```POST```
```
/api/authors
```
```json
{
    "firstname": "Firstname1",
    "lastname": "Lastname1",
    "surname": "Surname1",
    "books": []
}
```
* Request for create a new theme

```POST```
```
/api/themes
```
```json
{
    "name": "Theme 1",
    "books": []
}
```
* Request for create a new book

```POST```
```
/api/books
```
```json
{
    "title": "The Great Gatsby",
    "isTaken": false,
    "isAvailiable": true,
    "dateOfCreate": "2022-06-14",
    "rating": 4.5,
    "author": {
        "id": 2,
        "firstname": "Firstname1",
        "lastname": "Lastname1",
        "surname": "Surname1"
    },
    "theme": {
        "id": 1,
        "name": "Theme1"
    },
    "currentOwner": null,
    "previousOwners": null
}
```
