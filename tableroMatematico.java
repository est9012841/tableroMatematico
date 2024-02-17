import java.util.*;

public class tableroMatematico {

        // area de variables y constantes globales
    private static final int SIZE  = 8;
    private static Scanner scanner = new Scanner(System.in);
    private static String  
                            posicionPenalizar [][]  = new String [SIZE][SIZE],
                            casilla,
                            respuestaMatrices,
                            posicion                = "   ";
    private static boolean  penalizaciones    [][]  = new boolean[SIZE][SIZE],
                            exit                    = false,
                            salida                  = false;
    private static int      numerosTablero          = 64,
                            posicionTablero         = 1,
                            validacion              = 0,
                            tableroJuego [][]       = new int    [SIZE][SIZE];
    
    public static void main(String[] args) {
    
        Scanner scanner = new Scanner(System.in);
        
        while (!exit) {

            clearScreen();   // limpiar pantalla
            menuJuego();  //: Despliegue de opciones del menu

            System.out.println();
            System.out.println("Presione Enter para continuar...");
            scanner.next();
        }

        // Finalizacion de sesion
        System.out.println();
        System.out.println("Gracias por jugar con nosotros!");
        scanner.close();
        
    } //: final public static void main

                        //: area de funciones

    private static void menuJuego() {

        System.out.println("\n      +++-----o-o-o Menu o-o-o------+++\n");
        System.out.println("\n      1.    Iniciar juego");
        System.out.println("\n      2.    Retomar juego");
        System.out.println("\n      3.    Mostrar informacion del estudiante");
        System.out.println("\n      4.    Salir");         
        System.out.print("\n         Seleccione su opcion:    ");  //: Recibir opcion de usuario
        int opcion = scanner.nextInt();
       
        switch (opcion) {
            case 1:
                configurarPenalizaciones();
                iniciarJuego();                     
                break;

            case 2:
                iniciarJuego();
                break;
            
            case 3:
                datosAlumno();
                break;
            case 4:
                exit = true;
                break;
        }
    } //: final private static void menuJuego

    private static void iniciarJuego() {
        int numeroDado;
        String  tirarDado;

        while (!salida) {
            
            clearScreen();        
            dibujoTablero(posicion);
            System.out.println("\n Posicion del jugador en el tablero ...: " + posicionTablero);
            System.out.println("\n Presione (d) para tirar el dado o (p) para pausa");
            tirarDado = scanner.nextLine();

            if (tirarDado.equals("d") || tirarDado.equals("D")) {
                numeroDado = generarNumeroAleatorio(6,2);
                System.out.print("\n numero del dado ...: " + numeroDado + "\n");
                posicionTablero = posicionTablero + numeroDado;
                //scanner.nextLine();
                scanner.next("d");

                for (int i = 0; i < tableroJuego.length; i++) {
                    for (int j = 0; j < tableroJuego[i].length; j++) {
                        if (tableroJuego[i][j] == posicionTablero && penalizaciones[i][j] == true) {
                            System.out.println("\n +++--- Tiene una penalizacion ---+++");
                            System.out.println("\n ...Presione Enter para ejecutarla...");
                            //scanner.nextLine();
                            scanner.next();

                            if (posicionTablero < 65 && posicionTablero > 40) {
                                

                                divisionMatrices();
                                scanner.nextLine();
                            } 
                            if (posicionTablero < 41 && posicionTablero > 16) {
                                sumaMatrices();
                                scanner.nextLine();
                            } 
                            if (posicionTablero < 17) {
                                leyCosenos();
                                scanner.nextLine();
                            }      
                        }
                    }
                }
                //scanner.nextLine();
            }else {
                if (tirarDado.equals("p") || tirarDado.equals("P")) {
                    clearScreen();
                    menuJuego();
                }                
            }

            if (posicionTablero >= 64) {
                System.out.println("Juego finalizado...");
                scanner.nextLine();
                scanner.next();
                posicionTablero = 1;
                salida = true;   
            }
        } 
    } //: final private static void iniciarJuego
    
    private  static int generarNumeroAleatorio(int maximo, int minimo) { //genera un valor aleatorio entre valores definidos
        return (int)(Math.random()*(maximo - minimo + 1) + minimo);   
    } //: final public static int generarNumeroAleatorio

    private static void clearScreen() {  //limpia la pantalla
        System.out.print("\033[H\033[2J");
        System.out.flush();
    } //: final private static void clearScreen

    private static void dibujoTablero(String posicion) {  // Dibujo de tablero

        System.out.println("                            Tablero de juego\n");
        System.out.println("       a        b        c        d        e        f        g        h ");
        System.out.println(" --------------------------------------------------------------------------");
        int cuadros = 64,  //: valor inicial del tablero en la posicion 8a
            evaluacion,
            fila; 

        for (int i = 0; i < tableroJuego.length; i++) {
            fila = (tableroJuego.length - i);
            System.out.print(fila + " |");
            evaluacion = fila % 2; //capta el valor para determinar condicion de fila

            switch (evaluacion) {
                
                case (0): //condicion para resta a la derecha

                    for (int j = 0; j < tableroJuego[i].length; j++) {
                        casilla = posicionPenalizar[i][j];  
                        if (tableroJuego[i][j] == posicionTablero) {
                            posicion = " @ ";
                        } else {
                            posicion = "   ";
                        }
                        
                        dibujo(cuadros, casilla, posicion); //dibuja fila-numeros
                        cuadros--;  
                    } linea();            
                    break;

                default: //condicion para suma a la derecha

                    cuadros = cuadros -(tableroJuego.length - 1); //reinicio de columna para suma a la derecha
                    for (int j = 0; j < tableroJuego[i].length; j++) {
                        casilla = posicionPenalizar[i][j];
                        if (tableroJuego[i][j] == posicionTablero) {
                            posicion = " @ ";
                        } else {
                            posicion = "   ";
                        }
                        dibujo(cuadros, casilla, posicion); //dibuja fila-numeros
                        cuadros++;
                    } linea();
                    cuadros = cuadros - (tableroJuego.length + 1); //reinicia el conteo de columna a
                break;
            } 
        }
    }//: final private static void dibujoTablero

    private static void configurarPenalizaciones() {
        int analisis,   //condicionante para determinar lugar de penalizaciones
            fila,
            evaluacion,
            cuadros = 64;  
    
        for (int i = 0; i < tableroJuego.length; i++) {
            fila = (tableroJuego.length - i);
            evaluacion = fila % 2;

            switch (evaluacion) {
                case 0:
                    for (int j = 0; j < tableroJuego[i].length; j++) {

                        analisis = generarNumeroAleatorio(4,2);
                        analisis = analisis % 2;

                        if (analisis == 1) {
                            posicionPenalizar[i][j] = "#";
                            penalizaciones   [i][j] = true;
                            tableroJuego     [i][j] = numerosTablero--;
                        } else {
                            posicionPenalizar[i][j] = " ";
                            penalizaciones   [i][j] = false;
                            tableroJuego     [i][j] = numerosTablero--;
                        }
                        cuadros--;
                    }
                    break;

                default:
                    cuadros        = cuadros - (tableroJuego.length - 1);
                    numerosTablero = numerosTablero - (tableroJuego.length - 1);
                    for (int j = 0; j < tableroJuego[i].length; j++) {

                        analisis = generarNumeroAleatorio(4,2);
                        analisis = analisis % 2;

                        if (analisis == 1) {
                            posicionPenalizar[i][j] = "#";
                            penalizaciones   [i][j] = true;
                            tableroJuego     [i][j] = numerosTablero++;
                        } else {
                            posicionPenalizar[i][j] = " ";
                            penalizaciones   [i][j] = false;
                            tableroJuego     [i][j] = numerosTablero++;
                        }
                        cuadros++;
                    }
                    cuadros        = cuadros - (tableroJuego.length + 1);
                    numerosTablero = numerosTablero - (tableroJuego.length + 1);
                    break;
            }
            
        }
        
    } //: final private static void configurarPenalizaciones

    private static void dibujo(int cuadros, String casilla, String posicion) { //dibuja el tablero y las casillas con penalizaciones
        if (cuadros < 10 && cuadros > 0) { 
            System.out.print(casilla  + "  0" + (cuadros) + posicion + "|"); //le agrega un 0 si el valor es mayor que 0 y menor que 10
        } else {
            System.out.print(casilla  + "  "  + (cuadros) + posicion + "|"); //imprime valores normales de la cuadricula desde 10 hasta 64
        }
    } //: final private static void dibujo

    private static void linea() { //funcion para dibujar linea divisoria
        System.out.println();
        for (int k = 0; k < SIZE; k++){
            System.out.print("  |      ");
            
        }   System.out.print("  |");
        System.out.println("\n --------------------------------------------------------------------------");
    } //final private static void linea

    private static void datosAlumno() {  //imprime los valores del alumno
        clearScreen();
        String 
            titulo   = "\n +++---  DATOS DEL ESTUDIANTE  ---+++ \n",
            nombre   = "Juan Carlos Moya Garcia",
            carnet   = "0090-12841",
            curso    = "0770 Introduccion a la Programacion y Computacion 1",
            seccion  = "G",
            proyecto = "No. 1: Tablero Matematico";

        System.out.println(titulo);
        System.out.println("\n Nombre...:        " + nombre + "\n");
        System.out.println("\n Carnet...:        " + carnet + "\n");
        System.out.println("\n Curso y seccion..." + curso + " '" + seccion + "' \n");
        System.out.println("\n Proyecto...:      " + proyecto);

    } //: final private static void datosAlumno

    private static void leyCosenos() { //calculo de la ley de Cosenos
        clearScreen();
        System.out.println("\n          Nivel de dificultad ...: FACIL...:\n");
        int opcion = generarNumeroAleatorio(3,1);
        System.out.println("+++++++++---------     Ley de Cosenos     ----------+++++++++\n");
        System.out.println("Por favor, use la Ley de Cosenos para resolver el sistema  " + "[ " + opcion + " ]\n");
        System.out.println(opcion);
        double 
            ladoA,     ladoAR = 0,
            ladoB,     ladoBR = 0,
            ladoC,     ladoCR = 0,
            anguloA, anguloAR = 0,
            anguloB, anguloBR = 0 ,
            anguloC, anguloCR = 0,
            respuesta  []     = new double[3],
            respuestaR []     = new double[3];
        //int validacion        = 0;

        switch (opcion) {
            case (1):
                validacion++;
                if (validacion > 2) {
                    System.out.println(" Este problema ya fue resuelto por lo menos una vez...");
                    System.out.println("Intentelo mas tarde...");
                    scanner.next();
                    break;
                } else {
                    ladoA   = 15;
                    ladoC   = 20;
                    anguloA = 25;

                    calculosGeneralesLeyCosenos(ladoA, ladoC, anguloA, ladoBR, anguloBR, anguloCR, respuesta, respuestaR);
                break;
                } 
                
            case (2):
            validacion++;
                if (validacion > 2) {
                    System.out.println(" Este problema ya fue resuelto por lo menos una vez...");
                    System.out.println("Intentelo mas tarde...");
                    scanner.next();
                    break;
                } else {
                    ladoB   = 10;
                    ladoC   = 25;
                    anguloB = 30;
    
                    calculosGeneralesLeyCosenos(ladoB, ladoC, anguloB, ladoAR, anguloAR, anguloCR, respuesta, respuestaR);
                    break;
                }
               
            case (3):
            validacion++;
                if (validacion > 2) {
                    System.out.println(" Este problema ya fue resuelto por lo menos una vez...");
                    System.out.println("Intentelo mas tarde...");
                    scanner.next();
                    break;
                } else {
                    ladoA   = 18;
                    ladoB   = 25;
                    anguloC = 30;

                    calculosGeneralesLeyCosenos(ladoA, ladoB, anguloC, ladoCR, anguloAR, anguloBR, respuesta, respuestaR);
                    break;
                }
            default:
                    System.out.println("Todos los problemas ya fueron resueltos... felicidades");
                    scanner.next();
                    break;
                
        } scanner.nextLine();
    } //: final private static void leyCosenos

    private static void sumaMatrices() {
        clearScreen();
        System.out.println("\n          Nivel de dificultad ...: INTERMEDIO...:\n");
        int opcion = generarNumeroAleatorio(3,1);
        System.out.println("+++++++++---------     Suma de matrices     ----------+++++++++\n");
        System.out.println("Por favor, solucione el sistema de matrices " + "[ " + opcion + " ]\n");
        //System.out.println("La opcion elegida por el sistema es ...: " + opcion + "\n");
        int 
            matrizA [][] = new int[5][5],
            matrizB [][] = new int[5][5],
            matrizC [][] = new int[5][5],
            matrizR [][] = new int[5][5],
            validacion   = 0 ;

        switch (opcion) {
            case (1):
                validacion++;
                if (validacion > 1) {
                    System.out.println(" Este problema ya fue resuelto por lo menos una vez...");
                    System.out.println("Intentelo mas tarde...");
                    scanner.next();
                    break;
                } else {
                    int 
                    listadoA [] = {7,48,5,0,1,57,8,4,6,14,0,5,6,78,15,21,14,8,19,54,32,20,26,47,12},
                    listadoB [] = {9,5,2,1,8,4,2,3,47,8,48,55,32,19,6,7,56,32,14,8,32,87,0,1,7};
                
                    operacionesMatriz(listadoA, listadoB, matrizA, matrizB, matrizC, matrizR);
                    System.out.println("\n Respuesta ......\n");
                    imprimirMatriz(matrizC);
                    scanner.nextLine();
                break;
                }
                

            case (2):
                validacion++;
                if (validacion > 1) {
                    System.out.println(" Este problema ya fue resuelto por lo menos una vez...");
                    System.out.println("Intentelo mas tarde...");
                    scanner.next();
                    break;
                } else {
                    int 
                    listadoC [] = {4,9,7,45,18,7,51,26,8,38,48,26,37,21,19,1,0,6,8,1,2,19,55,25,15},
                    listadoD [] = {0,2,15,1,66,21,48,62,7,33,4,88,0,68,4,25,18,24,7,55,24,15,36,5,98};
                
                    operacionesMatriz(listadoC, listadoD, matrizA, matrizB, matrizC, matrizR);
                    System.out.println("\n Respuesta .......\n");
                    imprimirMatriz(matrizC);
                    scanner.nextLine();
                break;
                }
                

            case (3):
                validacion++;
                if (validacion > 1) {
                    System.out.println(" Este problema ya fue resuelto por lo menos una vez...");
                    System.out.println("Intentelo mas tarde...");
                    scanner.next();
                    break;
                } else {
                    int 
                    listadoE [] = {0,1,15,5,36,1,78,65,32,4,48,66,39,0,55,14,98,63,20,15,11,39,84,7,1},
                    listadoF [] = {78,25,66,48,98,0,45,2,3,1,2,9,14,10,20,35,87,65,2,32,25,8,4,9,39};
            
                    operacionesMatriz(listadoE, listadoF, matrizA, matrizB, matrizC, matrizR);
                    System.out.println("\n Respuesta ......\n");
                    imprimirMatriz(matrizC);
                    scanner.nextLine();
                break;
                }
            default:
                break;
                
        }
    } //: final private static void sumaMatrices

    private static void divisionMatrices() {
        clearScreen();
        int opcion = generarNumeroAleatorio(2,1);
        System.out.println("\n    Nivel de dificultad ... : AVANZADO...:\n");
        System.out.println(": +++--- Division de Matrices ---+++\n");
        System.out.println("\n Problema seleccionado... [ " + opcion + " ]\n");
        int 
            valor               = 4,
            determinante,
            matrizA [][]        = new int[4][4],     
            matrizB [][]        = new int[4][4];
        double 
            matrizBI [][]        = new double[4][4],
            matrizInversa [][]  = new double [4][4];

        switch (opcion) {
            case (1):
                int 
                    listadoA [] = {5,10,1,3,9,14,2,6,7,8,15,3,6,8,9,2},
                    listadoB [] = {5,13,9,4,1,9,6,3,8,11,69,33,25,6,7,4};
                
                System.out.println("    Matriz A\n");
                llenarMatriz(listadoA, matrizA);
                System.out.println("\n    Matriz B\n");
                llenarMatriz(listadoB, matrizB);
                System.out.println();
                determinante = calcularDeterminante(matrizB, valor);
                System.out.println("[ " + determinante + " ]");
                System.out.println("\n matriz B\n");
                for (int i = 0; i < valor; i++) {
                    for (int j = 0; j < valor; j++) {
                        matrizBI[i][j] = matrizB[i][j];
                        System.out.print(matrizBI[i][j] + " ");
                    }
                    System.out.println();
                }
                matrizInversa = calcularMatrizInversa(matrizBI);
                System.out.println("\nMatriz inversa\n");
                for (int i = 0; i < valor; i++ ) {
                    for (int j = 0; j < valor; j++) { 
                        {System.out.print(" " + redondeo(matrizInversa[i][j]) + " ");}
                    }
                    System.out.println();
                }
                scanner.next();


                break;

            case (2):
                int 
                    listadoC [] = {1,12,9,8,7,6,3,2,0,5,6,14,6,9,6,10},
                    listadoD [] = {8,19,20,4,12,33,6,8,4,5,9,7,8,22,14,6};
                
                System.out.println("    Matriz A\n");
                llenarMatriz(listadoC, matrizA);
                System.out.println("\n    Matriz B\n");
                llenarMatriz(listadoD, matrizB);
                determinante = calcularDeterminante(matrizB, valor);
                System.out.println("[ " + determinante + " ]");
                System.out.println("\n matriz B\n");
                for (int i = 0; i < valor; i++) {
                    for (int j = 0; j < valor; j++) {
                        matrizBI[i][j] = matrizB[i][j];
                        System.out.print(matrizBI[i][j] + " ");
                    }
                    System.out.println();
                }
                matrizInversa = calcularMatrizInversa(matrizBI);
                System.out.println("\nMatriz inversa\n");
                for (int i = 0; i < valor; i++ ) { 
                    for (int j = 0; j < valor; j++){ 
                        {System.out.print(" " + redondeo(matrizInversa[i][j]) + " ");}
                    }
                    System.out.println();
                }
                scanner.next();


                break;
        }

        System.out.println("\n: Sitio en construccion...");
        System.out.println("\n: Lamentamos los inconvenientes...");
        scanner.next();
        
    } //: finalprivate static void divisionMatrices 

private static void calculosGeneralesLeyCosenos(double ladoA, double ladoC, double anguloA, 
                                                    double ladoBR, double anguloBR, double anguloCR, double [] respuesta, double [ ] respuestaR) {
        
        String responder;
        boolean salida = true;

        calculoLeyCosenos(ladoA, ladoC, anguloA, respuesta);      //: Calcula los valores restantes, se redondean a 3 cifras y espera la respuesta del jugador
        System.out.print("\n Ya realizo este problema...??? [s/n]");
        responder = scanner.next();

        while (salida) {
            if (responder.equals("n") || responder.equals("N")) {
                salida = true;
                System.out.println("\n   Ingrese sus respuestas a continuacion ...:\n");
                tomaDeRespuestas(ladoBR, anguloBR, anguloCR, respuestaR); //: se toman las respuestas del jugador
                redondearRespuestas(respuestaR);                          //: se redondean las respuestas del jugador
                compararRespuestas(respuesta, respuestaR);                //: Se comparan las respuestas del sistema y del jugador
                salida = false;
            } else {
                salida = false;
                leyCosenos();
            }
        } //salida = false;
} //: final private static void calculosGeneralesLeyCosenos

    private static double [] calculoLeyCosenos(double lado1, double lado2, double angulo1, double [] Respuesta) { //realiza los calculos para obtener los datos incognita
        System.out.println("Lado 1 ...  : " +   lado1);
        System.out.println("Lado 2 ...  : " +   lado2);
        System.out.println("Angulo 1 ...: " + angulo1);
        double  lado3   = 0.0,
                angulo2 = 0.0,
                angulo3 = 0.0;

        for (int i = 0; i < Respuesta.length; i++) {
            switch (i) {
                case 0:
                    lado3 = calculoLado(lado1, lado2, angulo1);
                    Respuesta[i] = lado3;
                    break;
                case 1:
                    angulo2 = calculoAngulo(lado1, lado3, lado2);
                    Respuesta[i] = angulo2;
                    break;
                case 2:
                    angulo3 = 180.0 - angulo2 - angulo1;
                    Respuesta[i] = angulo3;
            }
        }
        return Respuesta;  
    } //: final private static void calculoLeyCosenos

    private static double [] tomaDeRespuestas(double lado3, double angulo2, double angulo3, double [] RespuestaR) {
        for (int i = 0; i < RespuestaR.length; i++) {
            switch (i) {
                case 0:
                    System.out.println("\nIngrese valor de lado 3...: ");
                    lado3   = scanner.nextDouble();
                    RespuestaR[i] = lado3;
                    break;
                case 1:
                    System.out.println("Ingrese el valor del angulo 2");
                    angulo2 = scanner.nextDouble();
                    RespuestaR[i] = angulo2;
                    break;
                case 2:
                    System.out.println("Ingrese el valor del angulo 3");
                    angulo3 = scanner.nextDouble();
                    RespuestaR[i] = angulo3;
                    break;
            }    
        }       
         return RespuestaR;
    } //: final private static double [] tomaDeRespuestas

    private static double [] redondearRespuestas(double [] RespuestaR) {
        for (int i = 0; i < RespuestaR.length; i++) {
            RespuestaR[i] = redondeo(RespuestaR[i]);
        }
        return RespuestaR;
    } //: final private static double [] redondearRespuestas

    private static void compararRespuestas(double [] respuesta, double [] RespuestaR) {
        String respuestaS;
        boolean iguales = true;
        for (int i = 0; i < respuesta.length && iguales; i++) {
            if ( respuesta[i] != RespuestaR[i]) {
                iguales = false;
            }
        }
        if (iguales) {
            respuestaS = "\n++++---- Respuesta Correcta ----++++";
            System.out.println(respuestaS);
        } else {
            respuestaS = "\n++++---- Respuesta incorrecta ----++++";
            System.out.println(respuestaS);
            System.out.println("\n La respuesta correcta es ...:");
            System.out.println(": Lado 3 ...:   " + respuesta[0]);
            System.out.println(": Angulo 2 ...: " + respuesta[1]);
            System.out.println(": Angulo 3 ...: " + respuesta[2]);
            salida = false;        
        } 
        salida = false;
    } //: final private static void compararRespuestas

    private static double anguloRadianes(double angulo) {  //traslada los valores de degradianes a radianes
        return (Math.toRadians(angulo));
    } //: final private static double anguloRadianes

    private static double calculoLado(double lado1, double lado2, double anguloMedio) { //calcula el lado faltante
        double original  = Math.sqrt(Math.pow(lado1,2) + Math.pow(lado2,2) - (2 * lado1 * lado2 * Math.cos(anguloRadianes(anguloMedio))));
        double lado      = redondeo(original); 
        return lado;
    } //: final private static double calculoLado

    private static double calculoAngulo (double lado1, double lado2, double lado3) { //calcula uno de los angulos faltantes
        double original  = Math.toDegrees(Math.acos((Math.pow(lado1,2) - Math.pow(lado2,2) - Math.pow(lado3,2))/(0-(2 * lado2 * lado3))));
        double angulo    = redondeo(original);
        return angulo;
    } //: final private static double calculoAngulo

    private static double redondeo(double original) {
        double entero    = Math.round(original);
        double decimal   = original - (Math.floor(original));
        double decimales = Math.round(decimal * 1000);
        double elemento  = entero + (decimales/1000);
        return elemento;     
    } //: final private static double redondeo

    private static int [][] operacionesMatriz(int [] listadoA, int [] listadoB, int [][] matrizA, int [][] matrizB, int [][] matrizC, int [][] matrizR) {
        String responder;
        boolean salida = true;

        System.out.println("      Matriz A\n");
            llenarMatriz(listadoA, matrizA);
        System.out.println("\n      Matriz B\n");
            llenarMatriz(listadoB, matrizB);
        System.out.println();
        //System.out.println("\n    Matriz Resultado\n");
            mandarASumar(matrizA, matrizB, matrizC);
        System.out.print("\n Ya realizo este problema...??? [s/n]  ");
        responder = scanner.next();

        while (salida) {
            if (responder.equals("n") || responder.equals("N")) {
                salida = true;
                    System.out.println("\n+++-- Ingreso de respuesta --+++\n");
                    ingresoRespuesta(matrizR);
                    clearScreen();
                    System.out.println("\n    Su respuesta es \n");
                    imprimirMatriz(matrizR);
                    System.out.println();
                    compararMatrices(matrizC, matrizR, respuestaMatrices);
                    if (respuestaMatrices == "++++---- Respuesta incorrecta ----++++") {
                        System.out.println(respuestaMatrices);
                        scanner.nextLine();
                        scanner.next();
                        salida = false;
                    } else {
                        System.out.println(respuestaMatrices);
                        salida = false;
                    }
                    //salida = false;
                //return matrizC; 
            } else { 
                salida = false;
                sumaMatrices();
            }
        }
        return matrizC;
    } //: final private static int [][] operacionesMatriz

    private static int [][] llenarMatriz(int [] listado, int [][] matriZ) {
        int vectorPosicion = 0;
        for (int i = 0; i < matriZ.length; i++) {
            System.out.print("| ");
            for (int j = 0; j < matriZ[i].length; j++){
                matriZ[i][j] = listado[vectorPosicion++];
                if (matriZ[i][j] >=0 && matriZ[i][j] < 10) {
                    System.out.print("0" + matriZ[i][j] + " ");
                } else {
                    System.out.print(matriZ[i][j] + " ");
                }               
            }
            System.out.println("|");
        }
        return matriZ;
    } //: final private static int [][] llenarMatriz

    private static int [][] mandarASumar(int [][] matriz1, int [][] matriz2, int [][]matriz3) {
        for (int i = 0; i < matriz1.length; i++) {
            for (int j = 0; j < matriz2.length; j++) {
                matriz3[i][j] = matriz1[i][j] + matriz2[i][j];
            }
        }
        return matriz3;
    } //: final private static int [][] mandarASumar

    private static int [][] ingresoRespuesta(int [][] MatrizR) {
        for (int i = 0; i < MatrizR.length; i++) {
            System.out.println("Ingrese valores de fila ..." + "[ " + (i + 1) + " ]\n");
            System.out.print("-->> ");
            for (int j = 0; j < MatrizR[i].length; j++) {
                
                MatrizR[i][j] = scanner.nextInt(); 
            }  
        }
        return MatrizR;
    } //: final private static int [][] ingresoRespuesta

    private static void imprimirMatriz (int [][] matrizI) {
        for (int i = 0; i < matrizI.length; i++) {
            System.out.print("| ");
            for (int j = 0; j < matrizI[i].length; j++) {
                if (matrizI[i][j] >= 0 && matrizI[i][j] < 10) {
                    System.out.print("0" + matrizI[i][j] + "  ");
                } else {
                    if (matrizI[i][j] >= 100) {
                        System.out.print(matrizI[i][j] + " ");    
                    }
                    else {
                        System.out.print(matrizI[i][j] + "  ");
                    }   
                }
            }
            System.out.println("|");
        }   
    } //: final private static void imprimirMatriz

    private static void compararMatrices(int [][] matrizC, int [][] matrizR, String respuestaMatrices) {
        if (Arrays.deepEquals(matrizC, matrizR)) {
            respuestaMatrices = "++++---- Respuesta Correcta ----++++";
            System.out.println(respuestaMatrices);
        } else {
            respuestaMatrices = "++++---- Respuesta incorrecta ----++++";
            System.out.println(respuestaMatrices);
        }
    } //: final private static void compararMatrices

    private static int calcularDeterminante(int [][] matriz, int valor) {

        int resp            = 0;
            
        switch (valor) {
            case 2:
                resp = ((matriz[0][0] * matriz[1][1]) - (matriz[1][0] * matriz[0][1]));
                break;
            case 3:
                resp = ((matriz[0][0]) * (matriz[1][1]) * (matriz[2][2])
                    + (matriz[1][0]) * (matriz[2][1]) * (matriz[0][2])
                    + (matriz[2][0]) * (matriz[0][1]) * (matriz[1][2]))
                    - ((matriz[2][0]) * (matriz[1][1]) * (matriz[0][2])
                            + (matriz[1][0]) * (matriz[0][1]) * (matriz[2][2])
                            + (matriz[0][0]) * (matriz[2][1]) * (matriz[1][2]));
                break;
            default:
                for (int i = 0; i < matriz.length; i++) {
                    resp += (matriz[i][0] * cofactor(matriz, i, 0));
                }
        }
        return resp;
    } //: final private static int calcularDeterminante

    private static int cofactor(int [][] matriz, int i, int j) {
        int adjunto,
            matrizApoyo [][] = new int[matriz.length - 1][matriz.length - 1],
            m,n;
            for (int k = 0; k < matrizApoyo.length; k++) {
                if (k < i ) {
                    m = k;
                } else {
                    m = k + 1;
                }
                for (int l = 0; l < matrizApoyo.length; l++) {
                    if (l < j) {
                        n = l;
                    } else {
                        n = l + 1;
                    }
                    matrizApoyo[k][l] = matriz[m][n];
                }
            }
        adjunto = (int)Math.pow(-1, i + j) * calcularDeterminante(matrizApoyo, matrizApoyo.length);
        return adjunto;
    } //: final private static int cofactor

    private static double [][] calcularMatrizInversa(double [][] matriz) {
        int gradoMatriz = matriz.length;

        double matrizB1[][] = new double [gradoMatriz][gradoMatriz];
        double matrizB2[][] = new double [gradoMatriz][gradoMatriz];

        int indice []       = new int [gradoMatriz];
        for (int i = 0; i < gradoMatriz; i++) {
            matrizB2[i][i] = 1;
        }

        metodoGaussiano(matriz, indice);

        for (int i = 0; i < gradoMatriz - 1; i++) {
            for (int j = i + 1; j < gradoMatriz; j++) {
                for (int k = 0; k < gradoMatriz; k++) {
                    matrizB2[indice[j]][k] -= matriz[indice[j]][i] * matrizB2[indice[i]][k];
                } 
            }
        }

        for (int i = 0; i < gradoMatriz; i++){
            matrizB1[gradoMatriz - 1][i] = matrizB2[indice[gradoMatriz - 1]][i]/matriz[indice[gradoMatriz - 1]][gradoMatriz - 1];
            for (int j = gradoMatriz -2; j >= 0; j--) {
                matrizB1[j][i] = matrizB2[indice [j]][i];
                for (int k = j + 1; k < gradoMatriz; k++) {
                    matrizB1[j][i] -= matriz[indice[j]][k] * matrizB1[k][i];
                }
                matrizB1[j][i] /= matrizB1[indice[j]][j];
            }
        }
        return matrizB1;
    } //: final private static double [][] calcularMatrizInversa

    public static void metodoGaussiano(double matriz [][], int indice []) {
        int gradoMatriz   = indice.length;
        double indice2 [] = new double [gradoMatriz];

        for (int i = 0; i < gradoMatriz; i++) {
            indice[i] = i;
        }

        for (int i = 0; i < gradoMatriz; i++) {
            double c1 = 0;
            for (int j = 0; j < gradoMatriz; j++) {
                double c0 = Math.abs(matriz[i][j]);
                if (c0 > c1) {
                    c1 = c0;
                }
            }
            indice2[i] = c1;
        }

        int k = 0;
        for (int j = 0; j < gradoMatriz - 1; j++) {
            double pi1 = 0;
            for (int i = j; i < gradoMatriz; i++) {
                double pi0 = Math.abs(matriz[indice[i]][j]);
                pi0 /= indice2[indice[i]];
                if (pi0 > pi1) {
                    pi1 = pi0;
                    k = i;
                }
            }
            int itmp = indice[j];
            indice[j] = indice[k];
            indice[k] = itmp;
            for (int i = j + 1; i < gradoMatriz; i++) {
                double pj = matriz[indice[i]][j]/matriz[indice[j]][j];
                
                matriz[indice[i]][j] = pj;

                for (int l = j + 1; l < gradoMatriz; l++) {
                    matriz[indice[i]][l] -= pj * matriz[indice[j]][l];
                }
            }
        }        
    } //: final public static void metodoGaussiano
    
} //: final public Class tableroMatematico
