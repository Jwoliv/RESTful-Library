# RESTful Library

----

### Technologies
:closed_book:  Java <br>
:closed_book:  Maven <br>
:closed_book:  PostgreSQL <br>
:closed_book:  Spring: Core, MVC, Boot <br>
:closed_book:  Mockito <br>
:closed_book:  JUnit5 <br>

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
:loudspeaker: Request for create a new user

```POST```
```
/api/users
```
```json
{
    "firstname": "John",
    "lastname": "Doe",
    "surname": "Adams",
    "email": "test@gmail.com",
    "numberOfOverdue": 0,
    "isBanned": false,
    "numberPhone": "+380983381762"
}
```
:loudspeaker: Request for create a new author

```POST```
```
/api/authors
```
```json
{
    "firstname": "AuthorFirstName",
    "lastname": "AuthorLastName",
    "surname": "AuthorSurname",
    "books": []
}
```
:loudspeaker: Request for create a new theme

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
:loudspeaker: Request for create a new book

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
        "firstname": "AuthorFirstName",
        "lastname": "AuthorLastName",
        "surname": "AuthorSurname"
    },
    "theme": {
        "id": 1,
        "name": "Theme1"
    },
    "currentOwner": null,
    "previousOwners": null
}
```
