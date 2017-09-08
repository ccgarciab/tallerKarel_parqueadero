/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tallerparqueadero_karel;
import becker.robots.*;
import java.util.Scanner;
/**
 *
 * @author Cristian
 */
public class TallerParqueadero_Karel {

    /**
     * @param args the command line arguments
     */
    public static Carro crearCarro(Layout layout, int placa){
        Carro carro = new Carro(layout, placa);
        return carro;
    }
    public static void main(String[] args) {
        // TODO code application logic here
        City bogota = new City();
        Layout ly = new Layout(bogota);
        int tamañoTotal=ly.getNumeroColumnas()*ly.getCarrosPorColumna();
        Carro[] arregloCarros = new Carro[tamañoTotal+1];  
        ly.showLayout();
        Parking pking = new Parking(ly);
        int costoSegundo = 6;
        
        Scanner in = new Scanner(System.in);
        int indicador;
        int contador = 0;
        boolean seguir = true;
        int opcion;
        double recaudo;
        double recaudoDiario = 0;
        while(seguir){
            System.out.println("Que quieres hacer?");
            System.out.println("1. ingresar carro");
            System.out.println("2. sacar un carro");
            System.out.println("3. mostrar los carros en una seccion");
            System.out.println("4. mostrar los ingresos el dia de hoy");
            System.out.println("5. reiniciar el dia");
            opcion = in.nextInt();
            switch(opcion){
                case 1: System.out.println("Por favor indique la placa del carro ");
                        indicador = in.nextInt();
                        arregloCarros[contador] = crearCarro(ly, indicador);
                        if(pking.ingresarCarro(arregloCarros[contador])){
                            contador++;
                            System.out.println("El carro ha sido ubicado");
                        }
                        else{
                            System.out.println("No hay espacio, o ha ocurrido un error");
                        }
                        break;
                case 2: System.out.println("Por favor indique la placa del carro ");
                        indicador = in.nextInt();
                        recaudo= pking.sacarCarro(indicador)*costoSegundo;
                        if(recaudo != 0){
                            System.out.println("Se pago $"+recaudo);
                            recaudoDiario=recaudoDiario+recaudo;
                            contador--;
                        }
                        else{
                            System.out.println("No se ha encontrado la placa, o ha ocurrido un error");
                        }
                        break;
                case 3: System.out.println("Por favor indique la seccion que deasea visualizar ");
                        indicador = in.nextInt();
                        if(!(pking.mostrarSeccion(indicador))){
                            System.out.println("El numero de seccion no fue encontrado");
                        }
                        break;
                case 4: System.out.println("Hoy se ha recaudado: $"+recaudoDiario);
                        break;
                case 5: recaudoDiario=0;
                        System.out.println("Se ha reiniciado el recuento del recaudo diario");
                        break;
                default: System.out.println("Por favor ingrese una opcion valida");
                         break;
            }
        }
    }    
}
