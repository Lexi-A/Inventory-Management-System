/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
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

public class ModifyPartController implements Initializable {
    
    Stage stage;
    Scene scene;
    Parent root;
    private int selectedIndex = 0;
    
    @FXML
    private RadioButton modpInHRBtn;
    
    @FXML
    private ToggleGroup ModPartType;

    @FXML
    private RadioButton modpOutRBtn;

    @FXML
    private TextField modpIdTxt;
    
    @FXML
    private Label modpNmLbl;

    @FXML
    private TextField modpNameTxt;

    @FXML
    private TextField modpInvTxt;

    @FXML
    private TextField modpCostTxt;

    @FXML
    private TextField modpMaxTxt;

    @FXML
    private TextField modpMinTxt;

    @FXML
    private TextField ModpCmpNmTxt;
    
    @FXML
    void onActInHRBtn(ActionEvent event) {
        
            modpNmLbl.setText("Machine ID ");
    }

    @FXML
    void onActOutBtn(ActionEvent event) {
        
            modpNmLbl.setText("Company Name ");

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
        
            int id = Integer.parseInt(modpIdTxt.getText());
        
            String name = modpNameTxt.getText();
        
            int stock = Integer.parseInt(modpInvTxt.getText());
        
            double price = Double.parseDouble(modpCostTxt.getText());
        
            int max = Integer.parseInt(modpMaxTxt.getText());
        
            int min = Integer.parseInt(modpMinTxt.getText());
        
            if(min < max){ //Requirement J. Set 1: preventing the minimum field from having a value above the maximum field.
            
                if(modpInHRBtn.isSelected()){
                
                
                    int machineId = Integer.parseInt(ModpCmpNmTxt.getText());
            
                    Inventory.updatePart(selectedIndex, new InHouse(id, name, price, stock, min, max, machineId));
                
                }
                else if (modpOutRBtn.isSelected()){
                
                  
                
                    String companyName = ModpCmpNmTxt.getText();
                
                    Inventory.updatePart(selectedIndex, new Outsourced(id, name, price, stock, min, max, companyName));
            
                }
            
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
    
    public void RecPartData (Part part){
        
        selectedIndex = Inventory.getAllParts().indexOf(part);
        
        modpIdTxt.setText(String.valueOf(part.getId()));
        
        modpNameTxt.setText(part.getName());
        
        modpInvTxt.setText(String.valueOf(part.getStock()));
        
        modpCostTxt.setText(String.valueOf(part.getPrice()));
        
        modpMaxTxt.setText(String.valueOf(part.getMax()));
        
        modpMinTxt.setText(String.valueOf(part.getMin()));
        

      if(part instanceof Outsourced){
           
          modpNmLbl.setText("Company Name ");
          
          modpOutRBtn.setSelected(true);
          
          ModpCmpNmTxt.setText(String.valueOf(((Outsourced) part).getCompanyName()));
        
       }
      
      else{
          
         modpNmLbl.setText("Machine ID ");
          
          modpInHRBtn.setSelected(true);
          
          ModpCmpNmTxt.setText(String.valueOf(((InHouse) part).getMachineId()));
          
      }
      
      
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    
}
