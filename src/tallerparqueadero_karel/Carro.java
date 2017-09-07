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
public class Carro {

    public Carro(Layout layout, int placa) {
        this.layout = layout;
        this.karel = new Robot(layout.getCiudad(), layout.getyInicial()+layout.getCarrosPorColumna(),
                layout.getxInicial()+(layout.getNumeroColumnas()*2)+1, Direction.WEST);
        this.placa = placa;
        this.tiempoInicial = 0;
    }
    //pruebas
    public Carro(City ciudad){
        this.ciudad = ciudad;
        this.karel = new Robot(this.ciudad, 0, 0, Direction.NORTH);
        this.placa = 0;
        this.tiempoInicial = 0;
    }
    

    public int getPlaca(){
        return this.placa;
    }
    
    public long getTiempoInicial(){
        return this.tiempoInicial;
    }
    
    
    //permite girar a la izquierda n veces. Mejora la lectura del codigo
    public void girarVeces(int veces){
        for(int i=0; i<veces; i++){
            this.karel.turnLeft();
        }
    }
    //permite moverse hhacia adelante n casillas. Mejora la lectura del codigo
    public void moverVeces(int veces){
        for(int i=0; i<veces; i++){
            this.karel.move();
        }
    }
    
    public void iniciarCronometro(){
        this.tiempoInicial = System.nanoTime();
    }
    
    //mueve el robot a la celda que se le indica, en el parqueadero hecho de obj Wall
    //tambien registra el tiempo del sistema en el que se ingreso a la celda
    public void ingresarKarel(int columna, int seccion){
        this.moverVeces(((this.layout.getNumeroColumnas()-columna)*2)+2);
        this.girarVeces(3);
        this.moverVeces(this.layout.getCarrosPorColumna()-seccion+1);
        this.karel.turnLeft();
        this.karel.move();
        this.girarVeces(2);
        this.iniciarCronometro();
    }
    //en desarrollo
    public void zonaTempKarel(int columna, int seccion){
        this.karel.move();
        this.girarVeces(3);
        this.moverVeces(placa);
    }
    
    private Layout layout;
    private int placa;
    private Robot karel;
    private long tiempoInicial;
    //pruebas
    private City ciudad;
}
