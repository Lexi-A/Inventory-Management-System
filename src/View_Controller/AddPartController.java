/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class AddPartController implements Initializable {
    
    Stage stage;
    
    Scene scene;
    
    Parent root;
    
    @FXML
    private RadioButton pInHRBtn;
    
    @FXML
    private ToggleGroup PartType;

    @FXML
    private RadioButton pOutRBtn;

    @FXML
    private TextField addpIDTxt;

    @FXML
    private TextField addpNameTxt;

    @FXML
    private TextField addpInvTxt;

    @FXML
    private TextField addpCosttxt;

    @FXML
    private TextField addpMaxTxt;

    @FXML
    private TextField addpMinTxt;

    @FXML
    private TextField addptext;
    
    @FXML
    private Label addptextNm;
    
    @FXML
    void onActionAddCmNm(ActionEvent event) {
        addptextNm.setText("Company Name ");
    }

    @FXML
    void onActionAddMchId(ActionEvent event) {
        addptextNm.setText("Machine ID ");
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
    void onActionSavePart(ActionEvent event) throws IOException {
         
        try{
        
            int id = Inventory.getAllParts().size() + 5; 
        
            String name = addpNameTxt.getText();
        
            int stock = Integer.parseInt(addpInvTxt.getText());
        
            double price = Double.parseDouble(addpCosttxt.getText());
        
            int max = Integer.parseInt(addpMaxTxt.getText());
        
            int min = Integer.parseInt(addpMinTxt.getText());
        
        
            if (min < max){ //Requirement J. Set 1: preventing the minimum field from having a value above the maximum field.
            
                if(pInHRBtn.isSelected()){ 
                
                    int machineId = Integer.parseInt(addptext.getText());
                
                    Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineId));
                
                
                }
                else if(pOutRBtn.isSelected()){
                
                    String companyName = addptext.getText();
                
                    Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));
                 
                }
            
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        
            root = FXMLLoader.load(getClass().getResource("/View_Controller/MainScreen.fxml"));
        
            stage.setScene(new Scene(root));
        
            stage.show();
        
            }
            else {
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
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        pOutRBtn.setSelected(true);
    }    
    
}
