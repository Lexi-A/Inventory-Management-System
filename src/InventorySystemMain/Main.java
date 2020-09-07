/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InventorySystemMain;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import Model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*

Author: DuckytheMomo

 */
public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/MainScreen.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Part partIn1 = new InHouse(30, "Electronic", 543.43, 5, 2, 9, 4255);
        
        Part partIn2 = new InHouse(87, "Software", 204.43, 8, 49, 56, 2134);
        
        Part partOut1 = new Outsourced(34, "Hardware", 456.00, 2, 5, 8,"WGU");
        
        Part partOut2 = new Outsourced(13, "Cloud Work", 758, 3 , 43, 89, "DollHouse Academy");
        
        
        Inventory.addPart(partIn1);
        
        Inventory.addPart(partIn2);
        
        Inventory.addPart(partOut1);
        
        Inventory.addPart(partOut2);
        
        
        Product addproduct1 = new Product(344, "Computer Keyboard", 34.12, 34, 21, 34);
        
        addproduct1.addAssociatedPart(partIn2);
        
        addproduct1.addAssociatedPart(partOut2);
        
        Product addproduct2 = new Product(653, "Motherboard Chip", 577.00, 75, 33, 55);
        
        addproduct2.addAssociatedPart(partOut1);
        
        
        Inventory.addProduct(addproduct1);
        
        Inventory.addProduct(addproduct2);
        
        
        
        launch(args);
    }
    
}
