/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Flowe
 */
public class Outsourced extends Part {
    
    //Declare all fields for Outsourced class:
    private String companyName;
    
    
    //Declare constructor
    public Outsourced(int id, String name, double price, int stock, int min, 
                      int max, String companyName){
        
       super(id, name, price, stock, min, max);
       
       this.companyName = companyName;
       
    }
    
    
    //Declare all methods for Outsourced class:
    
    public String getCompanyName(){
        
        return companyName;
        
    }
    
    public void setCompanyName(String companyName){
        
        this.companyName = companyName;
        
    }
    
}
