// BookController.aidl
package com.llj.login;

import com.llj.login.Book;

interface BookController {

    List<Book> getBookList();

    void addBookInOut(in Book book);

}