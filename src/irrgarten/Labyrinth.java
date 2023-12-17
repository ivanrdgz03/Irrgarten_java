package irrgarten;

import java.util.ArrayList;
import java.util.Arrays;

public class Labyrinth {

    private static final char BLOCK_CHAR = 'X';
    private static final char EMPTY_CHAR = '-';
    private static final char MONSTER_CHAR = 'M';
    private static final char COMBAT_CHAR = 'C';
    private static final char EXIT_CHAR = 'E';
    private static final int ROW = 0;
    private static final int COL = 1;

    private int nRows;
    private int nCols;
    private int exitRow;
    private int exitCol;
    private Monster monsters[][];
    private char labyrinth[][];
    private Player players[][];

    /**
     * @brief Constructor
     * @param nRows Numero de filas
     * @param nCols Numero de columnas
     * @param exitRow Fila de salida
     * @param exitCol Columna de salida
     */
    public Labyrinth(int nRows, int nCols, int exitRow, int exitCol) {
        this.nRows = nRows;
        this.nCols = nCols;
        this.exitRow = exitRow;
        this.exitCol = exitCol;
        this.monsters = new Monster[this.nRows][this.nCols];
        this.players = new Player[this.nRows][this.nCols];
        this.labyrinth = new char[this.nRows][this.nCols];

        //Inicializa this.labyrinth a Labyrinth.EMPTY_CHAR
        for (char[] row : this.labyrinth) {
            Arrays.fill(row, Labyrinth.EMPTY_CHAR);
        }

        this.labyrinth[this.exitRow][this.exitCol] = Labyrinth.EXIT_CHAR;
    }

    /**
     * @brief Método para poner un array de jugadores en el tablero
     * @param players jugadores a insertar
     */
    public void spreadPlayers(Player[] players) {
        for (Player p : players) {
            int pos[] = this.randomEmptyPos();
            this.putPlayer2D(-1, -1, pos[ROW], pos[COL], p);
        }
    }
    public void setFuzzyPlayer(FuzzyPlayer fuzzy, Player player) {
        this.players[player.getRow()][player.getCol()] = fuzzy;
    }
    /**
     * @brief Este metodo nos indica si existe un ganador
     * @return True en caso de que si y False en caso contrario
     */
    public boolean haveAWinner() {
        boolean salida = false;

        for (Player[] row : this.players) 
            for (Player player : row) 
                if (player != null) 
                    if (player.getRow() == this.exitRow && player.getCol() == this.exitCol) 
                        salida = true;

        return salida;
    }

    @Override
    /**
     * @brief Este método pasa a un String la instancia de la clase
     * @return Devuelve la cadena
     */
    public String toString() {
        String labyrinth_lista = "";

        for (char fila[] : this.labyrinth) {
            labyrinth_lista += "\n[";
            for (int i = 0; i < fila.length; i++) {
                labyrinth_lista += fila[i];
                if (i < fila.length - 1) {
                    labyrinth_lista += ", ";
                }
            }
            labyrinth_lista += "]";
        }
        return "L[" + this.nRows + ", " + this.nCols + ", " + this.exitRow + ", " + this.exitCol + labyrinth_lista + "]";
    }

    /**
     * @brief Añade un monstruo en las posiciones indicadas
     * @param row Fila donde se inserta
     * @param col Columna donde se inserta
     * @param monster Monstruo a insertar
     */
    public void addMonster(int row, int col, Monster monster) {
        assert (this.posOK(row, col) && this.emptyPos(row, col));
        monster.setPos(row, col);
        this.labyrinth[row][col] = Labyrinth.MONSTER_CHAR;
        this.monsters[row][col] = monster;
    }

    /**
     * @brief Método que mueve un jugador en una dirección.
     * @param direction Direccion a la que moverse
     * @param player Jugador a mover
     * @return El monstruo que se haya en la nueva posición, sino hay ninguno
     * devuelve null
     */
    public Monster putPlayer(Directions direction, Player player) {
        int oldRow = player.getRow();
        int oldCol = player.getCol();

        int newPos[] = this.dir2Pos(oldRow, oldCol, direction);
        Monster monster = this.putPlayer2D(oldRow, oldCol, newPos[0], newPos[1], player);

        return monster;
    }

    /**
     * @brief Añade una fila/columna de obstaculos
     * @param orientation Horizontal para una fila o Vertical para una columna
     * @param startRow Fila donde comienza a poner obstaculos
     * @param startCol columna donde empieza a poner obstaculos
     * @param length Cuanta longitud tendran los obstaculos en el sentido dado
     */
    public void addBlock(Orientation orientation, int startRow, int startCol, int length) {
        int incRow, incCol;
        if (orientation == Orientation.VERTICAL) {
            incRow = 1;
            incCol = 0;
        } else {
            incRow = 0;
            incCol = 1;
        }
        int row = startRow;
        int col = startCol;
        while (this.posOK(row, col) && this.emptyPos(row, col) && length > 0) {
            this.labyrinth[row][col] = Labyrinth.BLOCK_CHAR;
            length -= 1;
            row += incRow;
            col += incCol;
        }
    }

    /**
     * @brief Devuelve una lista con los movimientos validos desde la posicion
     * dada
     * @param row Fila actual
     * @param col Columna actual
     * @return ArrayList de direcciones validas
     */
    public ArrayList<Directions> validMoves(int row, int col) {
        ArrayList<Directions> output = new ArrayList<>();
        if (this.canStepOn(row + 1, col)) {
            output.add(Directions.DOWN);
        }
        if (this.canStepOn(row - 1, col)) {
            output.add(Directions.UP);
        }
        if (this.canStepOn(row, col + 1)) {
            output.add(Directions.RIGHT);
        }
        if (this.canStepOn(row, col - 1)) {
            output.add(Directions.LEFT);
        }

        return output;
    }

    /**
     * @brief Sirve para conocer si la posicion suministrada esta en el
     * laberinto
     * @param row Fila
     * @param col Columna
     * @return True si esta en el tablero False en caso opuesto
     */
    private boolean posOK(int row, int col) {
        return (row >= 0 && row < this.nRows && col < this.nCols && col >= 0);
    }

    /**
     * @brief Sirve para conocer si la posicion suministrada esta vacia
     * @param row Fila
     * @param col Columna
     * @return True si esta vacia False si no
     */
    private boolean emptyPos(int row, int col) {
        return this.labyrinth[row][col] == Labyrinth.EMPTY_CHAR;
    }

    /**
     * @brief Indica si hay un monstruo en la posicion dada
     * @param row Fila
     * @param col Columna
     * @return True si ha un monstruo false en caso contrario
     */
    private boolean monsterPos(int row, int col) {
        return this.labyrinth[row][col] == Labyrinth.MONSTER_CHAR;
    }

    /**
     * @brief Indica si la posicion dada es la casilla de salida
     * @param row Fila
     * @param col Columna
     * @return True en caso de que sea la casilla de salida False en caso
     * contrario
     */
    private boolean exitPos(int row, int col) {
        return (row == this.exitRow && col == this.exitCol);
    }

    /**
     * @brief Indica si se esta produciendo un combate en la casilla dada o no
     * @param row Fila
     * @param col Columna
     * @return True en caso de combate False en caso contrario
     */
    private boolean combatPos(int row, int col) {
        return this.labyrinth[row][col] == Labyrinth.COMBAT_CHAR;
    }

    /**
     * @brief Nos indica si se puede desplazar a la casilla (para evitar acceder
     * a una casilla con un jugador o combate)
     * @param row Fila
     * @param col Columna
     * @return True en caso de poder desplazarse False en caso contrario
     */
    private boolean canStepOn(int row, int col) {
        return (this.posOK(row, col) && (this.emptyPos(row, col) || this.monsterPos(row, col) || this.exitPos(row, col)));
    }

    /**
     * @brief Actualiza la posicion, se usa principalmente para actualizar las
     * casillas cuando se produce y termina un combate.
     * @param row Fila
     * @param col Columna
     */
    private void updateOldPos(int row, int col) {
        if (this.posOK(row, col)) {
            if (this.combatPos(row, col)) {
                this.labyrinth[row][col] = Labyrinth.MONSTER_CHAR;
            } else {
                this.labyrinth[row][col] = Labyrinth.EMPTY_CHAR;
            }
        }
    }

    /**
     * @brief Este método nos devuelve la nueva posición que tendríamos dada una
     * dirección
     * @param row Fila inicial
     * @param col Columna inicial
     * @param direction Direccion de movimiento
     * @return Array de fila y columna nuevas
     */
    private int[] dir2Pos(int row, int col, Directions direction) {
        int salida[] = new int[]{0, 0};
        int posX = row, posY = col;

        switch (direction) {
            case DOWN -> {
                posX++;
            }

            case UP -> {
                posX--;
            }

            case LEFT -> {
                posY--;
            }

            case RIGHT -> {
                posY++;
            }

            default -> {
                System.err.println("Se introdujo una direccion invalida");
                salida = new int[]{row, col};
            }

        }
        if (this.canStepOn(posX, posY)) {
            salida[0] = posX;
            salida[1] = posY;
        }

        return salida;
    }

    /**
     * @brief Este metodo devuelve una posicion vacia aleatoria en el laberinto
     * @return La posicion como un array de dos enteros
     */
    private int[] randomEmptyPos() {
        int row, col;
        do {
            row = Dice.randomPos(this.nRows);
            col = Dice.randomPos(this.nCols);
        } while (!(this.posOK(row, col) && this.emptyPos(row, col)));

        return new int[]{row, col};
    }

    /**
     * @brief Este método mueve un jugador de una posicion anterior a una nueva.
     * @param oldRow Antigua fila
     * @param oldCol Antigua columna
     * @param row Fila nueva
     * @param col Columna nueva
     * @param player Jugador a mover
     * @return El monstruo que habita la casilla a la que se desplaza (null en
     * caso de que no haya).
     */
    private Monster putPlayer2D(int oldRow, int oldCol, int row, int col, Player player) {
        Monster output = null;
        if (this.canStepOn(row, col)) {
            if (this.posOK(oldRow, oldCol)) {
                Player p = players[oldRow][oldCol];
                if (p == player) {
                    this.updateOldPos(oldRow, oldCol);
                    players[oldRow][oldCol] = null;
                }
            }
            boolean monsterPos = this.monsterPos(row, col);
            if (monsterPos) {
                this.labyrinth[row][col] = Labyrinth.COMBAT_CHAR;
                output = monsters[row][col];
            } else {   //Preguntar si esto esta bien por el UML
                char number = player.getNumber();
                this.labyrinth[row][col] = number;
            }
            this.players[row][col] = player;
            player.setPos(row, col);
        }
        return output;
    }
}
