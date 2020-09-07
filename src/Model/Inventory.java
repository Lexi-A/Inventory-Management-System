/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;


public class Inventory {
    
    //Declare all fields
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    
   
    //Declare methods
    public static void addPart(Part newPart){
        
        allParts.add(newPart);
        
    }
    
    public static void addProduct(Product newProduct){
        
        allProducts.add(newProduct);
        
    }
    
    public static Part lookupPart(int partId){
        
        for(Part partID : allParts){
            
            if(partID.getId() == partId){
                
                return partID;
                
            }
            
        }
        
        return null;
    }
    
    public static Product lookupProduct(int productId){
        
        for (Product productID : allProducts){
        
            if(productID.getId() == productId){
            
                return productID;
                
            }
        }
        
        return null;
    }
    
   public static ObservableList<Part> lookupPart(String partName){
       
        ObservableList<Part> listResultsP =  FXCollections.observableArrayList();
        
        for(Part pName: allParts){
            
            if(pName.getName().equals(partName)){
                
                listResultsP.add(pName);
                
            }
        }
        return listResultsP;
    }
    
    public static ObservableList<Product> lookupProduct(String productName){
        
        ObservableList<Product> listResultsPr = FXCollections.observableArrayList();
     
        for(Product prName: allProducts){
            
            if(prName.getName().equals(productName)){
                
                listResultsPr.add(prName);
                
            }
        }
        
        return listResultsPr;
        
    }
    
    public static void updatePart(int index, Part selectedPart) {
        
       allParts.set(index, selectedPart);
       
    }

    public static void updateProduct(int index, Product newProduct) {
        
        allProducts.set(index, newProduct);
        
    }
    
    public static boolean deletePart(Part selectedPart){
        
        allParts.remove(selectedPart);
        
        return true;
        
    }
    
    public static boolean deleteProduct(Product selectedProduct){
        
        allProducts.remove(selectedProduct);
        
        return true;
        
    }
    
    public static ObservableList<Part> getAllParts(){
        
        return allParts;
        
    }
    
    public static ObservableList<Product> getAllProducts(){
        
        return allProducts;
        
    }
    
}
