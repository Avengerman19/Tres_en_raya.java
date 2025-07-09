import java.util.Scanner;
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
                    System.out.println("¡Felicitaciones! El jugador " + jugadorActual + " ha ganado!");
                    juegoTerminado = true;
                } else if (tablero.estaLleno()) {
                    tablero.mostrarTablero();
                    System.out.println("¡Es un empate! El tablero está lleno.");
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
                    System.out.println(" Tablero de " + tamaño + "x" + tamaño + " creado correctamente.");
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
