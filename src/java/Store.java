/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Macko
 */
@ManagedBean
@SessionScoped
public class Store {

    /**
     * Creates a new instance of Store
     */
    private List<Book> warehouse;

    public List<Book> getWarehouse() {
        return warehouse;
    }
    
    private void initWarehouse(){
        warehouse = new ArrayList<Book>();
        warehouse.add(new Book("Title","Desc","author",2.4,0,0));
        warehouse.add(new Book("Title1","Desc1","author",2.4,0,0));
        warehouse.add(new Book("Title2","Desc2","author",2.4,0,0));
    }
    
    public Store() {
        initWarehouse();    
    }
}
