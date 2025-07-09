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
        // Diagonal principal
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

