/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

public class InHouse extends Part {
     
    //Declare all fields:
    private int machineId;

    public InHouse(int id, String name, double price, int stock, int min, 
                   int max, int machineId) {
        
        super(id, name, price, stock, min, max);
        
        this.machineId = machineId;
        
    }
    
    //Declare all methods:
    public int getMachineId() {
        
        return machineId;
        
    }

    public void setMachineId(int machineid) {
        
        this.machineId = machineid;
        
    }
}
