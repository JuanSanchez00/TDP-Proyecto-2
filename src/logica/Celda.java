package logica;

import javax.swing.JLabel;

public class Celda {
	private Integer valor;
	private EntidadGrafica entidadGrafica;
	private boolean celdaFija;
	private boolean cumpleRegla;
	
	public Celda() {
		this.valor = null;
		this.entidadGrafica = new EntidadGrafica();
		this.celdaFija = true;
		this.cumpleRegla = true;
	}
	
	public void actualizar() {
		if (this.valor!=null && this.valor+1<this.getCantElementos()) {
			this.valor++;
		}
		else {
			this.valor = 0;
		}
		entidadGrafica.actualizar(this.valor);
	}
	
	public int getCantElementos() {
		return this.entidadGrafica.getImagenes().length;
	}
	
	public Integer getValor() {
		return this.valor;
	}
	
	public void setValor(Integer valor) {
		if (valor!=null && valor<getCantElementos()) {
			this.valor = valor;
			this.entidadGrafica.actualizar(this.valor);
		}
		else {
			this.valor = null;
		}
	}
	
	public EntidadGrafica getEntidadGrafica() {
		return this.entidadGrafica;
	}
	
	public boolean getCeldaFija() {
		return this.celdaFija;
	}
	
	public boolean getCumpleRegla() {
		return this.cumpleRegla;
	}
	
	public void setGrafica(EntidadGrafica g) {
		this.entidadGrafica = g;
	}
	
	public void setCeldaFija(boolean b) {
		this.celdaFija = b;
	}
	
	public void setCumpleRegla(boolean b) {
		this.cumpleRegla = b;
	}
}