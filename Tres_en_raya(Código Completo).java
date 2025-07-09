import java.util.Scanner;

public class TresEnRaya {
    public static void main(String[] args) {
        JuegoTresEnRaya juego = new JuegoTresEnRaya();
        juego.iniciarJuego();
    }
}

class Tablero {
    private char[][] tablero;
    private int tamaño;
    
    public Tablero(int tamaño) {
        this.tamaño = tamaño;
        this.tablero = new char[tamaño][tamaño];
        inicializarTablero();
    }
    
    private void inicializarTablero() {
        for (int i = 0; i < tamaño; i++) {
            for (int j = 0; j < tamaño; j++) {
                tablero[i][j] = ' ';
            }
        }
    }
    
    public void mostrarTablero() {
        System.out.println("\n=== TABLERO ===");
        System.out.print("  ");
        for (int j = 0; j < tamaño; j++) {
            System.out.print("  " + j + " ");
        }
        System.out.println();
        
        for (int i = 0; i < tamaño; i++) {
            System.out.print(i + " |");
            for (int j = 0; j < tamaño; j++) {
                System.out.print(" " + tablero[i][j] + " |");
            }
            System.out.println();
            
            System.out.print("  ");
            for (int j = 0; j < tamaño; j++) {
                System.out.print("----");
            }
            System.out.println("-");
        }
        System.out.println();
    }
    
    public boolean realizarMovimiento(int fila, int columna, char jugador) {
        if (fila < 0 || fila >= tamaño || columna < 0 || columna >= tamaño) {
            return false; 
        }
        
        if (tablero[fila][columna] != ' ') {
            return false; 
        }
        
        tablero[fila][columna] = jugador;
        return true;
    }
    
    public boolean verificarGanador(char jugador) {
        return verificarFilas(jugador) || verificarColumnas(jugador) || verificarDiagonales(jugador);
    }
    
    private boolean verificarFilas(char jugador) {
        for (int i = 0; i < tamaño; i++) {
            boolean ganador = true;
            for (int j = 0; j < tamaño; j++) {
                if (tablero[i][j] != jugador) {
                    ganador = false;
                    break;
                }
            }
            if (ganador) return true;
        }
        return false;
    }
    
    private boolean verificarColumnas(char jugador) {
        for (int j = 0; j < tamaño; j++) {
            boolean ganador = true;
            for (int i = 0; i < tamaño; i++) {
                if (tablero[i][j] != jugador) {
                    ganador = false;
                    break;
                }
            }
            if (ganador) return true;
        }
        return false;
    }
    
    private boolean verificarDiagonales(char jugador) {
        
        boolean diagonalPrincipal = true;
        for (int i = 0; i < tamaño; i++) {
            if (tablero[i][i] != jugador) {
                diagonalPrincipal = false;
                break;
            }
        }
        
        
        boolean diagonalSecundaria = true;
        for (int i = 0; i < tamaño; i++) {
            if (tablero[i][tamaño - 1 - i] != jugador) {
                diagonalSecundaria = false;
                break;
            }
        }
        
        return diagonalPrincipal || diagonalSecundaria;
    }
    
    public boolean estaLleno() {
        for (int i = 0; i < tamaño; i++) {
            for (int j = 0; j < tamaño; j++) {
                if (tablero[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }
    
    public int getTamaño() {
        return tamaño;
    }
}

class JuegoTresEnRaya {
    private Tablero tablero;
    private Scanner scanner;
    private char jugadorActual;
    
    public JuegoTresEnRaya() {
        this.scanner = new Scanner(System.in);
        this.jugadorActual = 'X';
    }
    
    public void iniciarJuego() {
        mostrarBienvenida();
        int tamaño = solicitarTamañoTablero();
        tablero = new Tablero(tamaño);
        
        System.out.println("\n¡Juego iniciado! El jugador X comienza.");
        System.out.println("Para realizar un movimiento, ingresa las coordenadas (fila columna).");
        
        boolean juegoTerminado = false;
        
        while (!juegoTerminado) {
            tablero.mostrarTablero();
            
            if (realizarTurno()) {
                if (tablero.verificarGanador(jugadorActual)) {
                    tablero.mostrarTablero();
                    System.out.println(" ¡Felicitaciones! El jugador " + jugadorActual + " ha ganado!");
                    juegoTerminado = true;
                } else if (tablero.estaLleno()) {
                    tablero.mostrarTablero();
                    System.out.println(" ¡Es un empate! El tablero está lleno.");
                    juegoTerminado = true;
                } else {
                    cambiarJugador();
                }
            }
        }
        
        preguntarJugarNuevamente();
    }
    
    private void mostrarBienvenida() {
        System.out.println("====================================");
        System.out.println("     BIENVENIDO AL TRES EN RAYA");
        System.out.println("====================================");
    }
    
    private int solicitarTamañoTablero() {
        int tamaño = 0;
        boolean entradaValida = false;
        
        while (!entradaValida) {
            try {
                System.out.print("Ingresa el tamaño del tablero (mínimo 3): ");
                tamaño = Integer.parseInt(scanner.nextLine().trim());
                
                if (tamaño < 3) {
                    System.out.println(" Error: El tamaño debe ser al menos 3.");
                } else {
                    entradaValida = true;
                    System.out.println("
                 Tablero de " + tamaño + "x" + tamaño + " creado correctamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println(" Error: Por favor ingresa un número válido.");
            }
        }
        
        return tamaño;
    }
    
    private boolean realizarTurno() {
        System.out.println("\nTurno del jugador " + jugadorActual);
        System.out.print("Ingresa tu movimiento (fila columna): ");
        
        try {
            String entrada = scanner.nextLine().trim();
            String[] coordenadas = entrada.split("\\s+");
            
            if (coordenadas.length != 2) {
                System.out.println(" Error: Debes ingresar exactamente dos números (fila columna).");
                return false;
            }
            
            int fila = Integer.parseInt(coordenadas[0]);
            int columna = Integer.parseInt(coordenadas[1]);
            
            if (tablero.realizarMovimiento(fila, columna, jugadorActual)) {
                System.out.println(" Movimiento realizado correctamente.");
                return true;
            } else {
                if (fila < 0 || fila >= tablero.getTamaño() || columna < 0 || columna >= tablero.getTamaño()) {
                    System.out.println(" Error: Las coordenadas están fuera del rango del tablero.");
                } else {
                    System.out.println(" Error: Esa posición ya está ocupada. Intenta con otra.");
                }
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println(" Error: Por favor ingresa números válidos.");
            return false;
        } catch (Exception e) {
            System.out.println(" Error: Entrada inválida. Intenta nuevamente.");
            return false;
        }
    }
    
    private void cambiarJugador() {
        jugadorActual = (jugadorActual == 'X') ? 'O' : 'X';
    }
    
    private void preguntarJugarNuevamente() {
        System.out.print("\n¿Quieres jugar otra vez? (s/n): ");
        String respuesta = scanner.nextLine().trim().toLowerCase();
        
        if (respuesta.equals("s") || respuesta.equals("si") || respuesta.equals("sí")) {
            jugadorActual = 'X'; 
            iniciarJuego();
        } else {
            System.out.println("¡Gracias por jugar! ¡Hasta la próxima! ");
        }
    }
}
