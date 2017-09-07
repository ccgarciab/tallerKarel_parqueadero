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

public class Layout {

    public Layout(City ciudad) {
        this.ciudad = ciudad;
        this.numeroColumnas = 3;
        this.carrosPorColumna = 5;
        this.xInicial = 1;
        this.yInicial = 1;
    }

    public Layout(City ciudad, int numeroColumnas, int carrosPorColumna) {
        this.ciudad = ciudad;
        this.numeroColumnas = numeroColumnas;
        this.carrosPorColumna = carrosPorColumna;
        this.xInicial = 1;
        this.yInicial = 1;
    }

    public Layout(City ciudad, int numeroColumnas, int carrosPorColumna, int yInicial, int xInicial) {
        this.ciudad = ciudad;
        this.yInicial = yInicial;
        this.xInicial = xInicial;
        this.numeroColumnas = numeroColumnas;
        this.carrosPorColumna = carrosPorColumna;
    }
    
    public City getCiudad() {
        return ciudad;
    }

    public int getyInicial() {
        return yInicial;
    }

    public int getxInicial() {
        return xInicial;
    }

    public int getNumeroColumnas() {
        return numeroColumnas;
    }

    public int getCarrosPorColumna() {
        return carrosPorColumna;
    }


    //genera el parqueadero hecho de objetos Wall
    public void showLayout(){
        for(int i=xInicial; i<(xInicial+(2*numeroColumnas)); i=i+2){
          Wall blockAve0 = new Wall(ciudad, yInicial, i, Direction.NORTH);
          Wall blockAve1 = new Wall(ciudad, yInicial, i+1, Direction.NORTH);
          for(int j=yInicial; j<yInicial+carrosPorColumna; j++){
              Wall blockAve2 = new Wall(ciudad, j, i, Direction.WEST);
              Wall blockAve3 = new Wall(ciudad, j, i, Direction.SOUTH);
          }   
      }
        
        Wall blockAve1 = new Wall(ciudad, yInicial+1, xInicial+(2*numeroColumnas), Direction.NORTH);
      
        for(int i=yInicial; i<yInicial+carrosPorColumna; i++){
          Wall blockAve2 = new Wall(ciudad, i, xInicial+(2*numeroColumnas)-1, Direction.EAST);
        }
        for(int i=yInicial+1; i<yInicial+carrosPorColumna; i++){
          Wall blockAve2 = new Wall(ciudad, i, xInicial+(2*numeroColumnas), Direction.EAST);
          Wall blockAve3 = new Wall(ciudad, i, xInicial+(2*numeroColumnas), Direction.WEST);
        }  
    }
 
    //coordenada vertical de la celda ubicada en la esquina sup izq
    private int yInicial;
    //coordenada horizontal de la celda ubicada en la esquina sup izq
    private int xInicial;
    private int numeroColumnas;
    private int carrosPorColumna;
    private City ciudad;
}
