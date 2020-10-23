package logica;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.Timer;

public class Cronometro {
	private int seg,min,hor;
	private int actualizarCada;
	
	public Cronometro() {
		this.actualizarCada = 1000;
		this.seg = this.min = this.hor = 0;
		run();
	}
	
	public void run() {
        ActionListener accion = new ActionListener() {
    		public void actionPerformed(ActionEvent ae) {
    			seg++;
    			if(seg==60) {
    				seg = 0;
    				min++;
    				if(min==60) {
    					min = 0;
    					seg = 0;
    					hor++;
    					if (hor==99) {
    						seg=0;
    						min=0;
    						hor=0;
    					}
    	            }
    			}
    		}
    	};
    	Timer timer = new Timer(actualizarCada, accion);
    	timer.start();
    }
	
	public int getActualizarCada() {
		return this.actualizarCada;
	}
	
    public int getSegundos() {
        return this.seg;
    }
    
    public int getMinutos() {
        return this.min;
    }
    
    public int getHoras() {
        return this.hor;
    }
}