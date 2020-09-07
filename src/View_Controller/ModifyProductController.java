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

public class ModifyProductController implements Initializable {
    
    Stage stage;
    Parent root;
    Scene scene;
    private int selectIndex = 0;
    

    @FXML
    private TextField modprIdTxt;

    @FXML
    private TextField modprNameTxt;

    @FXML
    private TextField modprInvTxt;

    @FXML
    private TextField modprCostTxt;

    @FXML
    private TextField modprMaxTxt;

    @FXML
    private TextField modprMinTxt;

    @FXML
    private TextField modprSearchTxt;

    @FXML
    private TableView<Part> modprTable1;

    @FXML
    private TableColumn<Part, Integer> modprID1;

    @FXML
    private TableColumn<Part, String> modprName1;

    @FXML
    private TableColumn<Part, Integer> modprInvLvl1;

    @FXML
    private TableColumn<Part, Double> modprCost1;

    @FXML
    private TableView<Part> modprTable2;
    
    ObservableList<Part> modprTable2Temp = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Part, Integer> modprID2;

    @FXML
    private TableColumn<Part, String> modprName2;

    @FXML
    private TableColumn<Part, Integer> modprInvLvl2;

    @FXML
    private TableColumn<Part, Double> modprCost2;

    @FXML
    void onActionAddProduct(ActionEvent event) {
        
        Part pSelected = modprTable1.getSelectionModel().getSelectedItem();
        
        modprTable2Temp.add(pSelected);

    }

    @FXML
    void onActionDeleteProduct(ActionEvent event) {
        
        Part pDeleted = modprTable2.getSelectionModel().getSelectedItem();
        
        if(pDeleted != null){ //Requirement J. Set 2: including a confirm dialogue for all “Delete” and “Cancel” buttons.
        
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        
            alert.setTitle("Delete Confirmation");
        
            alert.setHeaderText("Are you sure you want to delete? (You cannot undo this action)");
            
            Optional<ButtonType> result = alert.showAndWait();
            
            if (result.get() == ButtonType.OK){
            
                modprTable2Temp.remove(pDeleted);
            
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
        
            int id = Integer.parseInt(modprIdTxt.getText());
        
            String name = modprNameTxt.getText();
        
            int stock = Integer.parseInt(modprInvTxt.getText());
        
            double price = Double.parseDouble(modprCostTxt.getText());
        
            int max = Integer.parseInt(modprMaxTxt.getText());
        
            int min = Integer.parseInt(modprMinTxt.getText());
        
            if (min < max){ //Requirement J. Set 1: preventing the minimum field from having a value above the maximum field.
            
                Product newPr = new Product(id, name, price, stock , min, max);
            
                Inventory.updateProduct(selectIndex, newPr);
            
                newPr.getAllAssociatedParts().addAll(modprTable2Temp);
            
                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        
                root = FXMLLoader.load(getClass().getResource("/View_Controller/MainScreen.fxml"));
        
                stage.setScene(new Scene(root));
        
                stage.show();
        
            }
            
            else{
            
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
        
       String searchInput = modprSearchTxt.getText();
    
        for (Part searchPart : Inventory.getAllParts()){
        
            if(searchPart.getName().contains(searchInput)){
            
                modprTable1.getSelectionModel().select(searchPart);
            
            }
            else if(Integer.toString(searchPart.getId()).contains(searchInput)){
            
                modprTable1.getSelectionModel().select(searchPart);
            
            }
                
        }

    }
    
    public void RecProductData (Product product){
        
        selectIndex = Inventory.getAllProducts().indexOf(product);
        
        modprIdTxt.setText(String.valueOf(product.getId()));
        
        modprNameTxt.setText(product.getName());
        
        modprInvTxt.setText(String.valueOf(product.getStock()));
        
        modprCostTxt.setText(String.valueOf(product.getPrice()));
        
        modprMaxTxt.setText(String.valueOf(product.getMax()));
        
        modprMinTxt.setText(String.valueOf(product.getMin()));
        
        modprTable2Temp.setAll(product.getAllAssociatedParts());
        
        
        modprTable2.setItems(modprTable2Temp);
        modprID2.setCellValueFactory(new PropertyValueFactory<>("id"));
        
        modprName2.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        modprInvLvl2.setCellValueFactory(new PropertyValueFactory<>("stock"));
        
        modprCost2.setCellValueFactory(new PropertyValueFactory<>("price"));
        

      
      
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        modprTable1.setItems(Inventory.getAllParts());
        
        modprID1.setCellValueFactory(new PropertyValueFactory<>("id"));
        
        modprName1.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        modprInvLvl1.setCellValueFactory(new PropertyValueFactory<>("stock"));
        
        modprCost1.setCellValueFactory(new PropertyValueFactory<>("price"));
        
    }    
    
}
