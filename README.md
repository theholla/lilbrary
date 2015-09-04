# _Library Catalog_

##### _Create a library catalog. Date of current version: 09/02/15_

#### By _**Diana Holland and Jennifer Morkunas**_

## Description

Add books to authors and authors to books.

## Setup

* Clone or fork from github
* Adjust settings in DB.java to work with your db setup
* Create a database to_do
* Run on gradle or on a server
* Open in your browser!

In PSQL:

CREATE DATABASE library_catalog;
\c library_catalog
CREATE TABLE books (id SERIAL PRIMARY KEY, title VARCHAR);
CREATE TABLE authors (id SERIAL PRIMARY KEY, name VARCHAR);
CREATE TABLE authors_books (id SERIAL PRIMARY KEY, author_id INT, book_id INT);
CREATE TABLE copies (id SERIAL PRIMARY KEY, book_id INT, checkedout BOOLEAN, checkout_date VARCHAR, due_date VARCHAR);
CREATE TABLE checkouts (id SERIAL PRIMARY KEY, patron_id INT, copy_id INT);
CREATE TABLE patrons (id SERIAL PRIMARY KEY, name VARCHAR);
CREATE DATABASE library_catalog_test WITH TEMPLATE library_catalog;

## Technologies Used

Java, Velocity Templating, Postgres Database, HTML and CSS



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
