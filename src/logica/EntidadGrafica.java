package logica;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class EntidadGrafica {
	private ImageIcon grafico;
	private String[] imagenes;
	private JLabel label;
	
	public EntidadGrafica() {
		this.grafico = new ImageIcon();
		this.imagenes = new String[]{"/img/1.png", "/img/2.png", "/img/3.png", "/img/4.png", "/img/5.png", "/img/6.png", "/img/7.png", "/img/8.png", "/img/9.png"};
		this.label = new JLabel();
	}
	
	public void actualizar(int indice) {
		if (indice<this.imagenes.length) {
			ImageIcon imageIcon = new ImageIcon(this.getClass().getResource(this.imagenes[indice]));
			this.grafico.setImage(imageIcon.getImage());
		}
	}
	
	public ImageIcon getGrafico() {
		return this.grafico;
	}
	
	public void setGrafico(ImageIcon grafico) {
		this.grafico = grafico;
	}
	
	public String[] getImagenes() {
		return this.imagenes;
	}
	
	public void setImagenes(String[] imagenes) {
		this.imagenes = imagenes;
	}
	
	public JLabel getJLabel() {
		return this.label;
	}
	
	public void setJLabel(JLabel l) {
		this.label = l;
	}
}
