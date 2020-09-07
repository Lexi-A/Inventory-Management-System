/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import java.io.IOException;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class MainScreenController implements Initializable {
    
    //Reference variables
    Stage stage;
    Scene scene;
    Parent root;
    
   @FXML
    private TableView<Part> InvTable1;

    @FXML
    private TableColumn<Part, Integer> invPartId;

    @FXML
    private TableColumn<Part, String> invPartName;

    @FXML
    private TableColumn<Part, Integer> invPartInvLevel;

    @FXML
    private TableColumn<Part, Double> invPartCost;
    
    @FXML
    private Button modPartBTN;

    @FXML
    private TextField InvSearchTxt1;

    @FXML
    private TableView<Product> InvTable2;

    @FXML
    private TableColumn<Product, Integer> invProductId;

    @FXML
    private TableColumn<Product, String> invProductName;

    @FXML
    private TableColumn<Product, Integer> invProductInvLvl;

    @FXML
    private TableColumn<Product, Double> invProductCost;

    @FXML
    private TextField InvSearchTxt2;
    
    //Handlers
    @FXML
    void onActionAddParts(ActionEvent event) throws IOException {
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        
        root = FXMLLoader.load(getClass().getResource("/View_Controller/AddPart.fxml"));
        
        stage.setScene(new Scene(root));
        
        stage.show();
        
    }

    @FXML
    void onActionAddProducts(ActionEvent event) throws IOException {
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        
        root = FXMLLoader.load(getClass().getResource("/View_Controller/AddProduct.fxml"));
        
        stage.setScene(new Scene(root));
        
        stage.show();
    }

    @FXML
    void onActionDeleteParts(ActionEvent event) {
        
       //Requirement J. Set 2: including a confirm dialogue for all “Delete” and “Cancel” buttons.
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION); 
        
        alert.setTitle("Delete Conformation Box");
        
        alert.setHeaderText("Do you wish to delete this part? (You cannot undo it.)");
        
        //Option to delete the part.
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK){
            
            Part delPart = InvTable1.getSelectionModel().getSelectedItem();
            
            Inventory.deletePart(delPart);
            
        }


    }

    @FXML
    void onActionDeleteProducts(ActionEvent event) {
        
               
        //Conformation message box:
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION); //Requirement J. Set 2: including a confirm dialogue for all “Delete” and “Cancel” buttons.
        
        alert.setTitle("Delete Conformation Box");
        
        alert.setHeaderText("Do you wish to delete this product? (You cannot undo it.)");
        
        //Option to delete the product.
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK){
            
            Product delProduct = InvTable2.getSelectionModel().getSelectedItem();
            
            Inventory.deleteProduct(delProduct);
            
        }

    }

    @FXML
    void onActionExitScreen(ActionEvent event) {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        
        alert.setTitle("Exit Screen");
        
        alert.setHeaderText("Do you wish to exit?");
        
        //Option to delete the product.
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK){
            
           System.exit(0);
        }
     
        
    }

    @FXML
    void onActionModParts(ActionEvent event) throws IOException {
        
        if(InvTable1.getSelectionModel().getSelectedItem() != null){
            
            FXMLLoader loader = new FXMLLoader();
            
            loader.setLocation(getClass().getResource("/View_Controller/ModifyPart.fxml")); //Loading up the screen
            
            loader.load(); //Telling us we want to load the FXML document.
        
            ModifyPartController MpCntrl = loader.getController(); //Letting the object know the controller to use.
            
            MpCntrl.RecPartData(InvTable1.getSelectionModel().getSelectedItem()); //Get whatever value is selected from that tableview.
        
            stage = (Stage)((Button)event.getSource()).getScene().getWindow(); //Keeping track of the container that the button will launch to.
  
            root = loader.getRoot();
        
            stage.setScene(new Scene(root));
        
            stage.show(); 
        }
        
        else{
        
            Alert alert = new Alert(Alert.AlertType.WARNING);
        
            alert.setTitle("Part Not Selected");
        
            alert.setHeaderText("Please select a part!");
                    
            alert.showAndWait();
            
        }
        
    }

    @FXML
    void onActionModProducts(ActionEvent event) throws IOException {
        
        if(InvTable2.getSelectionModel().getSelectedItem() != null){
            
            FXMLLoader loader = new FXMLLoader();
            
            loader.setLocation(getClass().getResource("/View_Controller/ModifyProduct.fxml")); //Loading up the screen
            
            loader.load(); //Telling us we want to load the FXML document.
        
            ModifyProductController MprCntrl = loader.getController(); //Letting the object know the controller to use.
            
            MprCntrl.RecProductData(InvTable2.getSelectionModel().getSelectedItem()); //Get whatever value is selected from that tableview.
        
        
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        
            root = loader.getRoot();
        
            stage.setScene(new Scene(root));
        
            stage.show();
            
        }
        else{
            
            Alert alert = new Alert(Alert.AlertType.WARNING);
        
            alert.setTitle("Product Not Selected");
        
            alert.setHeaderText("Please select a product!");
            
            
            alert.showAndWait();
        
        }
        
    }

    @FXML
    void onActionSearchParts(ActionEvent event) {
        
        String searchInput = InvSearchTxt1.getText();
        
        for (Part searchPart : Inventory.getAllParts()){
        
            if(searchPart.getName().contains(searchInput)){
            
                InvTable1.getSelectionModel().select(searchPart);
            
            }
            
            else if(Integer.toString(searchPart.getId()).contains(searchInput)){
            
                InvTable1.getSelectionModel().select(searchPart);
            
            }
                
        }
        
    }

    @FXML
    void onActionSearchProducts(ActionEvent event) {

        String searchInput = InvSearchTxt2.getText();
    
        for (Product searchProduct : Inventory.getAllProducts()){
        
            if(searchProduct.getName().contains(searchInput)){
            
                InvTable2.getSelectionModel().select(searchProduct);
            
            }
            
            else if(Integer.toString(searchProduct.getId()).contains(searchInput)){
            
                InvTable2.getSelectionModel().select(searchProduct);
            
            }
                
        }
        
    }

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        InvTable1.setItems(Inventory.getAllParts());
        
        invPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        
        invPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        invPartInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        
        invPartCost.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        
        InvTable2.setItems(Inventory.getAllProducts());
        
        invProductId.setCellValueFactory(new PropertyValueFactory<>("id"));
        
        invProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        invProductInvLvl.setCellValueFactory(new PropertyValueFactory<>("stock"));
        
        invProductCost.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        
    }    
    
}
