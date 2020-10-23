package gui;

import javax.swing.ImageIcon;

public class EntidadGraficaReloj {
	private String[] imagenes;
	
	public EntidadGraficaReloj() {
		this.imagenes = new String[]{"/img/0_reloj.png", "/img/1_reloj.png", "/img/2_reloj.png", "/img/3_reloj.png", "/img/4_reloj.png", "/img/5_reloj.png", "/img/6_reloj.png", "/img/7_reloj.png", "/img/8_reloj.png", "/img/9_reloj.png"};
	}
	
	public ImageIcon getImagen(int i) {
		return new ImageIcon(this.getClass().getResource(this.imagenes[i]));
	}
	
	public ImageIcon getDosPuntos() {
		return new ImageIcon(this.getClass().getResource("/img/2_puntos.png"));
	}
}
