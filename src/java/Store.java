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
@ManagedBean(name="store")
@SessionScoped
public class Store {

    /**
     * Creates a new instance of Store
     */
    private List<Book> warehouse;
    private int sortType;
    private String[] sortTypes;
    

    public String[] getSortTypes() {
        return sortTypes;
    }

    public void setSortTypes(String[] sortTypes) {
        this.sortTypes = sortTypes;
    }

    public int getSortType() {
        return sortType;
    }

    public void setSortType(int sortType) {
        this.sortType = sortType;
    }
    
    
    
   

    public List<Book> getWarehouse() {
        return warehouse;
    }
    
    private void initWarehouse(){
        warehouse = new ArrayList<Book>();
        warehouse.add(new Book("A","Desc","author",2.4,0,0));
        warehouse.add(new Book("B","Desc1","author",2.4,0,0));
        warehouse.add(new Book("C","Desc2","author",2.4,0,0));
    }
    private void initSortTypes(){
      sortTypes = new String[3];  
      sortTypes[0] = "Title";
      sortTypes[1] = "Author";
      sortTypes[2] = "Likes";
    }
    
    public void addBook(Book b){
        warehouse.add(b);
    }
    
    public Store() {
        initWarehouse();  
        initSortTypes();
    }
}
