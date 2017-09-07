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
public class Parking {

    public Parking(Layout layout) {
        this.layout = layout;
        this.arregloCarros = new Carro[this.layout.getNumeroColumnas()]
                [this.layout.getCarrosPorColumna()];
        //pruebas
        this.ciudad = new City();
        this.carroNulo = new Carro(this.ciudad);
    }
    //retorna un boolean indicando si se pudo ingresar el carro al parqueadero y a la "base de datos"
    public boolean ingresarCarro(Carro carro, int columna, int seccion){
        boolean exito = false;
        /*solo se procedera si se intenta ingresar a un numero de columna y seccion validos, 
        y si la celda esta vacia*/
        if(columna<=this.layout.getNumeroColumnas() && 
                seccion<=this.layout.getCarrosPorColumna() && 
                columna>0 && seccion>0 && this.arregloCarros[columna-1][seccion-1]==null){
            //carro.ingresarKarel(int, int) mueve el carro en la reppresentacion grafica
            //tambien registra el tiempo inicial de entrada
            carro.ingresarKarel(columna, seccion);
            this.arregloCarros[columna-1][seccion-1]=carro;
            exito = true;
        }
        return exito;
    }
    
    private Layout layout;
    private Carro[][] arregloCarros;
    //pruebas
    private Carro carroNulo;
    private City ciudad;
}
