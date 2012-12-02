/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Macko
 */
public class Book  {
    private String title;
    private String description;
    private String author;
    private double price;
    private int likes;
    private int dislikes;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }
    
    public void like(){
        this.likes++;
    }

    public void dislike(){
        this.dislikes++;
    }
    
    public Book(String title, String description, String author, double price, int likes, int dislikes) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.price = price;
        this.likes = likes;
        this.dislikes = dislikes;
    }

    

    
}
