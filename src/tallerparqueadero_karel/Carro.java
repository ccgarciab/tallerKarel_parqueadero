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
    

    public int getPlaca(){
        return this.placa;
    }
    
    public long getTiempoInicial(){
        return this.tiempoInicial;
    }
    
    public int getColumna(){
        return this.columna;
    }
    
    public int getSeccion(){
        return this.seccion;
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
    
    //retorna la diferencia entre el tiempo inicial y el tiempo actual, en segundos
    public double getTiempoTotal(){
        double tiempoTotal = (double)(this.tiempoInicial-System.nanoTime());
        return tiempoTotal/1000000.0;
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
        this.columna = columna;
        this.seccion = seccion;
    }
    
    //envia un carro desde una seccion y columna a cierta casilla de la zona temporal
    public void ingresarZonaTempKarel(int seccionTemp){
        this.karel.move();
        this.girarVeces(3);
        this.moverVeces(this.layout.getCarrosPorColumna()-this.seccion+1);
        this.karel.turnLeft();
        this.moverVeces(((this.layout.getNumeroColumnas()-this.columna)*2)+1);
        this.karel.turnLeft();
        this.moverVeces(this.layout.getCarrosPorColumna()-(seccionTemp+1));
    }
    
    //regresa un carro desde cierta casilla de la zona temporal a la pricipal
    public void regresaDeZonaTempKarel(int seccionTemp, int nuevaCol, int nuevaSec){
        this.girarVeces(2);
        this.moverVeces(this.layout.getCarrosPorColumna()-(seccionTemp+1));
        this.girarVeces(3);
        this.moverVeces(((this.layout.getNumeroColumnas()-nuevaCol)*2)+1);
        this.girarVeces(3);
        this.moverVeces(this.layout.getCarrosPorColumna()-nuevaSec);
        this.karel.move();
        this.karel.turnLeft();
        this.karel.move();
        this.girarVeces(2);
    }
    
    public void sacarKarel(){
        this.karel.move();
        this.girarVeces(3);
        this.moverVeces(this.layout.getCarrosPorColumna()-this.seccion+1);
        this.karel.turnLeft();
        this.moverVeces(((this.layout.getNumeroColumnas()-this.columna)*2)+3);
        this.karel = null;
    }
    
    private Layout layout;
    private int placa;
    private Robot karel;
    private long tiempoInicial;
    private int columna;
    private int seccion;
}
