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
        this.zonaTemp = new Carro[this.layout.getCarrosPorColumna()-1];
    }
    
    
    public int getCarrosActualmenteCol(int columna){
        int contador = 0;
        for(int i=0; i<this.layout.getCarrosPorColumna(); i++){
            if(this.arregloCarros[columna][i] != null){
                contador++;
            }
        }
        return contador;
    }
    
    public int getFilaLibre(){
        int indice = this.layout.getNumeroColumnas();
        int carrosCol = this.layout.getCarrosPorColumna();
        for(int i=0; i<this.layout.getNumeroColumnas(); i++){
            if(carrosCol>this.getCarrosActualmenteCol(i)){
                carrosCol=this.getCarrosActualmenteCol(i);
                indice=i;
            }
        }
        return indice+1;
    }
    
    //retorna un boolean indicando si se pudo ingresar el carro al parqueadero y a la "base de datos"
    public boolean ingresarCarro(Carro carro){
        boolean exito = false;
        int columna = this.getFilaLibre();
        int seccion = this.getCarrosActualmenteCol(columna)+1;
        /*solo se procedera si se intenta ingresar a un numero de columna y seccion validos, 
        y si la celda esta vacia*/
        if(columna<=this.layout.getNumeroColumnas() && 
                seccion<=this.layout.getCarrosPorColumna() && 
                columna>0 && seccion>0 && this.arregloCarros[columna-1][seccion-1]==null){
            //carro.ingresarKarel(int, int) mueve el carro en la reppresentacion grafica
            //tambien registra el tiempo inicial de entrada y la columna/seccion a la que ingreso
            carro.ingresarKarel(columna, seccion);
            this.arregloCarros[columna-1][seccion-1]=carro;
            exito = true;
        }
        return exito;
    }
    
    public int[] getLugar_Placa(int placa){
        int[] lugar=new int[2];
        lugar[0] = 0;
        lugar[1] = 0;
        for(int i=0; i<this.layout.getNumeroColumnas(); i++){
            for(int j=0; j<this.layout.getCarrosPorColumna(); i++){
                if(placa==this.arregloCarros[i][j].getPlaca()){
                    lugar[0]=i+1;
                    lugar[1]=j+1;
                    return lugar;
                }
            }
        }
        return lugar;
    }
    
    public double sacarCarro(int placa){
        int columna = this.getLugar_Placa(placa)[0];
        int seccion = this.getLugar_Placa(placa)[1];
        double tiempoTotal = 0;
        if(columna<=this.layout.getNumeroColumnas() && 
                seccion<=this.layout.getCarrosPorColumna() && 
                columna>0 && seccion>0 && this.arregloCarros[columna-1][seccion-1]!=null){
            
            int indiceTemp=0;
            for(int i=this.layout.getCarrosPorColumna()-1; i>seccion-1; i--){
                if(this.arregloCarros[columna-1][i]!=null){
                    this.arregloCarros[columna-1][i].ingresarZonaTempKarel(indiceTemp);
                    this.zonaTemp[indiceTemp]=this.arregloCarros[columna-1][i];
                    this.arregloCarros[columna-1][i]=null;
                }
            }
            tiempoTotal=this.arregloCarros[columna-1][seccion-1].getTiempoTotal();
            this.arregloCarros[columna-1][seccion-1].sacarKarel();
            this.arregloCarros[columna-1][seccion-1] = null;
            int contadorSeccion = 1;
            for(int i=this.layout.getCarrosPorColumna()-2; i>=0; i--){
                if(this.zonaTemp[i] != null){
                    this.zonaTemp[i].regresaDeZonaTempKarel(i, columna, contadorSeccion);
                    this.arregloCarros[columna-1][contadorSeccion-1] = zonaTemp[i];
                    zonaTemp[i] = null;
                    ++contadorSeccion;
                }
            }
        }
        return tiempoTotal;
    }
    
    
    
    
    private Layout layout;
    private Carro[][] arregloCarros;
    private Carro[] zonaTemp;
}
