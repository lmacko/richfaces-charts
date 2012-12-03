/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

/**
 *
 * @author Macko
 */
@ManagedBean
public class NewBook {

    @ManagedProperty(value = "#{store}")
    private Store store;
    private Book book;

    public NewBook() {
        book = new Book();
    }
    
    public void setStore(Store store) {
        this.store = store;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    
    public String submitAdd() {
        store.addBook(book);
        return "index";
    }
}
