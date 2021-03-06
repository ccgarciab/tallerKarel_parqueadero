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
    
    //retorna el numero de carros guardados en una columna
    public int getCarrosActualmenteCol(int columna){
        int contador = 0;
        /*para evitar salirse de los limites del arreglo, no se evalua si se 
        introduce una columna mayor al numero que hay*/
        if (columna<this.layout.getNumeroColumnas()){
        for(int i=0; i<this.layout.getCarrosPorColumna(); i++){
            if(this.arregloCarros[columna][i] != null){
                contador++;
            }
        }
        }
        return contador;
    }
    
    //retorna el numero de la primera columna con mas espacio. 0 significa que no hay espacio
    public int getColLibre(){
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
        int columna = this.getColLibre();
        int seccion = this.getCarrosActualmenteCol(columna-1)+1;
        /*solo se procedera si se intenta ingresar a un numero de columna y seccion validos, 
        y si la celda esta vacia*/
        if(columna<=this.layout.getNumeroColumnas() && 
                seccion<=this.layout.getCarrosPorColumna() && 
                columna>0 && seccion>0 && this.arregloCarros[columna-1][seccion-1]==null){
            /*carro.ingresarKarel(int, int) mueve el carro en la representacion grafica
            tambien registra el tiempo inicial de entrada y la columna/seccion a la que ingreso*/
            carro.ingresarKarel(columna, seccion);
            this.arregloCarros[columna-1][seccion-1]=carro;
            exito = true;
        }
        return exito;
    }
    
    //busca una placa en el parqueadero y retorna un arreglo 1x2, con su columna y seccion
    public int[] getLugar_Placa(int placa){
        int[] lugar=new int[2];
        //si retorna [0, 0], se sabe que no se encontro la placa
        lugar[0] = 0;
        lugar[1] = 0;
        for(int i=0; i<this.layout.getNumeroColumnas(); i++){
            for(int j=0; j<this.layout.getCarrosPorColumna(); j++){
                if((this.arregloCarros[i][j]!=null) && (placa==this.arregloCarros[i][j].getPlaca())){
                    lugar[0]=i+1;
                    lugar[1]=j+1;
                    return lugar;
                }
            }
        }
        return lugar;
    }
    
    //retorna el tiempo que el carro estubo alojado, en segundos
    /*tambien incluye el proceso de acomodar los carros que estorban en la seccion 
        temporal, y sacar el carro deseado del */
    public double sacarCarro(int placa){
        int columna = this.getLugar_Placa(placa)[0];
        int seccion = this.getLugar_Placa(placa)[1];
        double tiempoTotal = 0;
        //si la columna o seccion son 0, se entiende que no se encontro el carro
        if(columna<=this.layout.getNumeroColumnas() && 
                seccion<=this.layout.getCarrosPorColumna() && 
                columna>0 && seccion>0 && this.arregloCarros[columna-1][seccion-1]!=null){
            //se reducen las variables para se usadas como indices que comienzan con 0
            columna--;
            seccion--;
            //un contador para llevar la cuenta de cual casilla de la seccion temporal se esta usando
            int indiceTemp=0;
            //para todos los carros que estan al frente del que queremos sacar, en la misma fila
            for(int i=this.layout.getCarrosPorColumna()-1; i>seccion; i--){
                if(this.arregloCarros[columna][i] != null){
                    this.arregloCarros[columna][i].ingresarZonaTempKarel(indiceTemp);
                    this.zonaTemp[indiceTemp]=this.arregloCarros[columna][i];
                    this.arregloCarros[columna][i] = null;
                    //en la siguiente iteracion se ocupara la siguiente casilla de la zona temporal
                    indiceTemp++;
                }
            }
            tiempoTotal = this.arregloCarros[columna][seccion].getTiempoTotal();
            this.arregloCarros[columna][seccion].sacarKarel();
            this.arregloCarros[columna][seccion] = null;
            int contadorSeccion = 1;
            //se nececita saber cuantos carros no se movieron para dejar pasar al que sacabamos
            int restantes = this.getCarrosActualmenteCol(columna);
            for(int i=this.layout.getCarrosPorColumna()-2; i>=0; i--){
                if(this.zonaTemp[i] != null){
                    /*el desplazamiento de la zona temporal a la principal depende de
                        los carros que aun permanezcan en la seccion, y tambien de que
                        los ubicaremos en orden ascendente*/
                    this.zonaTemp[i].regresaDeZonaTempKarel(i, columna+1, restantes+contadorSeccion);
                    this.arregloCarros[columna][restantes+contadorSeccion-1] = zonaTemp[i];
                    zonaTemp[i] = null;
                    //se van ubicando en secciones de numero superior
                    ++contadorSeccion;
                }
            }
        }
        return tiempoTotal;
    }
    
    //imprime las placas de los carros en una columna
    public boolean mostrarSeccion(int seccion){
        boolean exito = false;
        if(seccion<=this.layout.getNumeroColumnas() && seccion>0){
            seccion--;
            for(int i=0; i<this.layout.getCarrosPorColumna(); i++){
                if(this.arregloCarros[seccion][i] != null){
                    System.out.println("Carro "+(i+1)+": "+this.arregloCarros[seccion][i].getPlaca());
                    System.out.println("En la columna "+this.arregloCarros[seccion][i].getColumna()+
                                        " y la seccion "+this.arregloCarros[seccion][i].getSeccion());
                    System.out.println("Lleva "+(int)(this.arregloCarros[seccion][i].getTiempoTotal())+
                                        " segundos en el parqueadero");
                }
            }
            exito = true;
        }
        return exito;
    }
    
    
    private Layout layout;  
    private Carro[][] arregloCarros;
    private Carro[] zonaTemp;
}
