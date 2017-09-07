/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tallerparqueadero_karel;
import becker.robots.*;
/**
 *
 * @author Cristian
 */
public class TallerParqueadero_Karel {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        City bogota = new City();
        Layout ly = new Layout(bogota, 5, 7, 3, 4);
        ly.showLayout();
        Carro cr = new Carro(ly, 123);
        Parking pking = new Parking(ly);
        if(pking.ingresarCarro(cr, 0, 0)){
            System.out.println("ye");
        }
        else{
            System.out.println("nah");
        }
    }
    
}
