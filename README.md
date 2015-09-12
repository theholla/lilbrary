# _Library Catalog_

##### A little library catalog. Date of current version: 09/02/15

#### By **Diana Holland and Jennifer Morkunas**

## Description

In this little library, you can create a database of books that keeps track of author and title information. The database can support books with multiple authors, and authors with multiple books. We are currently building functionality for patrons to make checkouts, and to keep track of how many copies of a book exist.

## Setup

* Please have all Java developer tools ready, including the JDK.
* This app uses Apache Spark and Velocity to handle its user interface.
* You'll need to be able to run and compile Java. This app uses Gradle for that purpose. To run this project, first setup the local database. Then connect to this folder in terminal and type ```gradle run```.

**To use the database included with this file, follow these instructions:**

* Clone this git repository on your computer ```git clone https://github.com/theholla/library-catalog.git```
* In terminal, start the PostgreSQL server by typing ```postgres``` and ```psql```
* In psql, create a new database by typing ```CREATE DATABASE library_catalog;```
* In another tab in terminal, connect to this project folder: ```cd path/to/this/folder```
* In this tab, dump the database information from my sql file into your local database by typing the following code: ```psql library_catalog < library_catalog.sql```

* To run tests, you can create a new test database in psql: ```CREATE DATABASE library_catalog_test WITH TEMPLATE library_catalog;```

## Database Schema

<img src="/src/main/resources/public/img/sql_schema.png">

## Technologies Used

Java, Apache Spark and Velocity templates, PostgreSQL Database, HTML5, and CSS

## UI

<img src="/src/main/resources/public/img/ss.png">

### Legal

Copyright (c) 2015 **Diana Holland and Jennifer Morkunas**

This software is licensed under the MIT license.

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
