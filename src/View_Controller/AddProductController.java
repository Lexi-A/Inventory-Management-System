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
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

public class AddProductController implements Initializable {
    
    Stage stage;
    Parent root;
    Scene scene;
    
    @FXML
    private TextField addprIdTxt;

    @FXML
    private TextField addprNameTxt;

    @FXML
    private TextField addprInvTxt;

    @FXML
    private TextField addprCosttxt;

    @FXML
    private TextField addprMaxTxt;

    @FXML
    private TextField addprMinTxt;

    @FXML
    private TextField addprSearchTxt;

    @FXML
    private TableView<Part> addprTable1;

    @FXML
    private TableColumn<Part, Integer> prId1;

    @FXML
    private TableColumn<Part, String> prName1;

    @FXML
    private TableColumn<Part, Integer> prInv1;

    @FXML
    private TableColumn<Part, Double> prCost1;

    @FXML
    private TableView<Part> addprTable2;
    
    ObservableList<Part> addprTable2Temp = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Part, Integer> prId2;

    @FXML
    private TableColumn<Part, String> prName2;

    @FXML
    private TableColumn<Part, Integer> prInv2;

    @FXML
    private TableColumn<Part, Double> prCost2;
    
    @FXML
    void onActionAddProduct(ActionEvent event) {
        
        Part pSelected = addprTable1.getSelectionModel().getSelectedItem();
        
        addprTable2Temp.add(pSelected);
  
    }

    @FXML
    void onActionDeleteProduct(ActionEvent event) {
        
        Part pDeleted = addprTable2.getSelectionModel().getSelectedItem();
        
        if(pDeleted != null){ //Requirement J. Set 2: including a confirm dialogue for all “Delete” and “Cancel” buttons.
        
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        
            alert.setTitle("Delete Confirmation");
        
            alert.setHeaderText("Are you sure you want to delete? (You cannot undo this action)");
            
            Optional<ButtonType> result = alert.showAndWait();
            
            if (result.get() == ButtonType.OK){
            
                addprTable2Temp.remove(pDeleted);
            
            }
        
        }
       

    }

    @FXML
    void onActionDisplayMainScreen(ActionEvent event) throws IOException {
        
        Alert backToMain = new Alert(Alert.AlertType.CONFIRMATION); 
        
        backToMain.setTitle("Cancel Confirmation"); //Requirement J. Set 2: including a confirm dialogue for all “Delete” and “Cancel” buttons.
        
        backToMain.setHeaderText("Are you sure you want to cancel? (Your changes may not be saved)");
        
        Optional<ButtonType> result = backToMain.showAndWait();
            
        if (result.get() == ButtonType.OK){
        
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        
            root = FXMLLoader.load(getClass().getResource("/View_Controller/MainScreen.fxml"));
        
            stage.setScene(new Scene(root));
        
            stage.show();
        
        }
        
    }

    @FXML
    void onActionSaveProduct(ActionEvent event) throws IOException {
        
        try{
        
            int id = Inventory.getAllParts().size() + 5; 
        
            String name = addprNameTxt.getText();
        
            int stock = Integer.parseInt(addprInvTxt.getText());
        
            double price = Double.parseDouble(addprCosttxt.getText());
        
            int max = Integer.parseInt(addprMaxTxt.getText());
        
            int min = Integer.parseInt(addprMinTxt.getText());
        
        
            if (min < max){ //Requirement J. Set 1: preventing the minimum field from having a value above the maximum field.
            
                Product addPr = new Product(id, name, price, stock, min, max);
            
                Inventory.addProduct(addPr);
            
                addprTable2Temp.forEach((partAssociate) -> {
                    Product invMinus = Inventory.getAllProducts().get(Inventory.getAllProducts().size() - 1);
                
                    invMinus.addAssociatedPart(partAssociate);
                });
            
            
                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        
                root = FXMLLoader.load(getClass().getResource("/View_Controller/MainScreen.fxml"));
        
                stage.setScene(new Scene(root));
        
                stage.show();
        
            }
        
         else{
                //Added warning alert incase invalid input was made.
                Alert warningAlert = new Alert(Alert.AlertType.ERROR);
            
                warningAlert.setTitle("Error Warning!");
            
                warningAlert.setContentText("The input you have put in is incompatible! "
                        + "Make sure minimum is less than maximum.");
            
                warningAlert.showAndWait();
        
            }
        
        }
        
        catch(NumberFormatException e){
            
           Alert warningAlert = new Alert(Alert.AlertType.ERROR);
           
           warningAlert.setTitle("Error Warning Exception: " + e);
            
           warningAlert.setHeaderText("Please enter valid values!");
            
           warningAlert.showAndWait(); 
        
        }

    }

    @FXML
    void onActionSearchProduct(ActionEvent event) {
        
       String searchInput = addprSearchTxt.getText();
    
        for (Part searchPart : Inventory.getAllParts()){
        
            if(searchPart.getName().contains(searchInput)){
            
                addprTable1.getSelectionModel().select(searchPart);
            
            }
            else if(Integer.toString(searchPart.getId()).contains(searchInput)){
            
                addprTable1.getSelectionModel().select(searchPart);
            
            }
                
        }
        
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        addprTable1.setItems(Inventory.getAllParts());
        
        prId1.setCellValueFactory(new PropertyValueFactory<>("id"));
        
        prName1.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        prInv1.setCellValueFactory(new PropertyValueFactory<>("stock"));
        
        prCost1.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        //Second Table
        addprTable2.setItems(addprTable2Temp);
        
        prId2.setCellValueFactory(new PropertyValueFactory<>("id"));
        
        prName2.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        prInv2.setCellValueFactory(new PropertyValueFactory<>("stock"));
        
        prCost2.setCellValueFactory(new PropertyValueFactory<>("price"));
        
    }    
    
}
