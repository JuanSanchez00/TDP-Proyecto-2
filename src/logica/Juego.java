package logica;

import java.io.*;
import java.util.Random;

public class Juego {
	private Celda [][] tablero;
	private int cantFilas;
	private int cantCeldasAEliminar;
	private boolean solucionValida;
	
	public Juego() {
		this.cantCeldasAEliminar = 45;
		this.solucionValida = true;
		this.cantFilas = 9;
		this.tablero = new Celda[this.cantFilas][this.cantFilas];
		inicializarJuego();
		if (this.solucionValida && comprobarSolucion()) {
		    this.solucionValida = true;
		   	eliminarCeldas(cantCeldasAEliminar);
		}
		else {
		    this.solucionValida=false;
		}
	}
	
	public void accionar(Celda c) {
		c.actualizar();
	}
	
	public Celda getCelda(int i, int j) {
		return this.tablero[i][j];
	}
	
	public int getCantFilas() {
		return this.cantFilas;
	}
	
	public int getCantFilaSubPanel() {
		return (int) Math.sqrt(cantFilas);
	}
	
	public boolean getSolucionValida() {
		return this.solucionValida;
	}
	
	private void inicializarJuego() {
		int fila = 0, columna = 0, valor = 0;
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;
		String linea = null;
		char caracter;
		try {
			archivo = new File ("src/arch/archivo.txt");
			fr = new FileReader(archivo);
		    br = new BufferedReader(fr);
		    while((linea=br.readLine())!=null && this.solucionValida) {//por cada linea del archivo
			    for (int i=0; i<linea.length() && this.solucionValida; i++) {//por cada caracter de la linea
			   		if (columna<cantFilas) {
				   		caracter = linea.charAt(i);	    		
				    	if (caracter!=' ') {
				    		if (caracter!='0' && Character.isDigit(caracter)) {//acepta todos los digitos menos el 0
								tablero[fila][columna] = new Celda();
								valor = Character.getNumericValue(caracter);//pasa el char a int
								tablero[fila][columna].setValor(valor-1);//-1 ya que el parametro representa posiciones de un arreglo
								columna++;
				    		}
				    		else {//no es un digito del 1 a 9
				    			this.solucionValida=false;
				    		}
						}
			    	}
				   	else {//si el archivo tiene mas columnas que cantFilas
				    	this.solucionValida=false;
				   	}
				}
			   	columna = 0;
			   	fila++;
		    }
		    br.close(); fr.close();
		}
		catch (Exception e) {
			this.solucionValida=false;
		}
	}
	
	private boolean comprobarSolucion() {
		boolean solucion = true;
		for (int f=0; f<cantFilas && solucion; f++) {
			for (int c=0;c<cantFilas && solucion;c++) {
				solucion=cumpleReglaFila(f,c,tablero[f][c].getValor()) && cumpleReglaColumna(f,c,tablero[f][c].getValor()) && cumpleReglaPanel(f,c,tablero[f][c].getValor());
			}
		}
		return solucion;
	}
	
	public boolean cumpleReglaFila(int fila, int columna, int valor) {
		boolean cumple = true;
		for (int i=0; i<cantFilas && cumple; i++) {
			if (columna!=i && tablero[fila][i].getValor()!=null && tablero[fila][i].getValor()==valor) {
				cumple = false;
			}
		}
		if (cumple) {
			tablero[fila][columna].setCumpleRegla(true);
		}
		else {
			tablero[fila][columna].setCumpleRegla(false);
		}
		return cumple;
	}
	
	public boolean cumpleReglaColumna(int fila, int columna, int valor) {
		boolean cumple = true;
		for (int i=0; i<cantFilas && cumple; i++) {
			if (fila!=i && tablero[i][columna].getValor()!=null && tablero[i][columna].getValor()==valor) {
				cumple = false;
			}
		}
		if (cumple) {
			tablero[fila][columna].setCumpleRegla(true);
		}
		else {
			tablero[fila][columna].setCumpleRegla(false);
		}
		return cumple;
	}
	
	public boolean cumpleReglaPanel(int fila, int columna, int valor) {
		boolean cumple = true;
		int minFila = 0, minColumna = minFila, maxFila = this.getCantFilaSubPanel()-1, maxColumna = maxFila;
		while (!(fila>=minFila && fila<=maxFila)) {//busca entre que filas se encuentra la celda
			minFila = minFila+this.getCantFilaSubPanel();
			maxFila = maxFila+this.getCantFilaSubPanel();
		}
		while (!(columna>=minColumna && columna<=maxColumna)) {//busca entre que columnas se encuentra la celda
			minColumna = minColumna+this.getCantFilaSubPanel();
			maxColumna = maxColumna+this.getCantFilaSubPanel();
		}
		cumple = cumpleReglaPanelAux(fila,columna,minFila,maxFila,minColumna,maxColumna,valor);
		if (cumple) {
			tablero[fila][columna].setCumpleRegla(true);
		}
		else {
			tablero[fila][columna].setCumpleRegla(false);
		}
		return cumple;
	}
	
	public boolean cumpleReglaPanelAux(int fila, int columna, int minFila, int maxFila, int minColumna, int maxColumna, int valor) {
		boolean cumple = true;
		for (int f=minFila; f<=maxFila && cumple; f++) {
			for (int c=minColumna; c<=maxColumna && cumple ;c++) {
				if (fila!=f && columna!=c && tablero[f][c].getValor()!=null && tablero[f][c].getValor()==valor) {
					cumple = false;
				}
			}
		}
		return cumple;
	}
	
	private void eliminarCeldas(int cant) {
		int cont = 0;
		while (cont<cant) {
			Random rand = new Random();
			int fila = rand.nextInt(cantFilas);
			int columna = rand.nextInt(cantFilas);
			if (tablero[fila][columna].getValor()!=null) {//si la celda no fue eliminada
				tablero[fila][columna] = new Celda();//elimino la celda
				tablero[fila][columna].setCeldaFija(false);//la celda deja de ser una celda fija
				cont++;
			}
		}
	}
	
	public int cantCeldasCumplenRegla() {
		int cont = 0;
		for (int f = 0; f < cantFilas; f++) {
			for (int c = 0; c < cantFilas; c++) {
				if (tablero[f][c].getValor()!=null && tablero[f][c].getCumpleRegla()) {
					cont++;
				}
			}
		}
		return cont;
	}
	
	public boolean gano() {
		return cantCeldasCumplenRegla() == cantFilas * cantFilas;
	}
}

