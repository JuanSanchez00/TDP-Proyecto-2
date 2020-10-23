package gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import logica.*;
public class GUITest extends JFrame {

	private JPanel panelTablero;
	private JPanel panelReloj;
	private JPanel panelBoton;
	private Juego juego;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUITest frame = new GUITest();
					frame.setVisible(true);
				} 
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUITest() {
		this.setTitle("Sudoku");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 450, 523);
		panelTablero = new JPanel();
		panelTablero.setBorder(new EmptyBorder(5, 5, 5, 5));
		panelTablero.setPreferredSize(new Dimension(456,469));
		juego = new Juego();
		if (!juego.getSolucionValida()) {
			JOptionPane cartelError = new JOptionPane();
			cartelError.showMessageDialog(panelTablero, "Sudoku no ha podido iniciar correctamente.", "Sudoku", JOptionPane.ERROR_MESSAGE);
			System.exit(0);//finaliza la ejecucion
		}
		panelTablero.setLayout(new GridLayout(juego.getCantFilaSubPanel(),juego.getCantFilaSubPanel(),0,0));
		panelTablero.setVisible(false);//arranca con el tablero deshabilitado
		panelReloj = new JPanel();
		JLabel dosPuntosII = new JLabel();
		JLabel dosPuntosI = new JLabel();
		JLabel horasII = new JLabel();
		JLabel horasI = new JLabel();
		JLabel minutosII = new JLabel();
		JLabel minutosI = new JLabel();
		JLabel segundosI = new JLabel();
		JLabel segundosII = new JLabel();
		panelReloj.setPreferredSize(new Dimension(260,60));
		panelReloj.add(horasII);
		panelReloj.add(horasI);
		panelReloj.add(dosPuntosII);
		panelReloj.add(minutosII);
		panelReloj.add(minutosI);
		panelReloj.add(dosPuntosI);
		panelReloj.add(segundosII);
		panelReloj.add(segundosI);
		EntidadGraficaReloj e = new EntidadGraficaReloj();
		dosPuntosII.setIcon(e.getDosPuntos());
		dosPuntosI.setIcon(e.getDosPuntos());
		horasII.setIcon(e.getImagen(0));
		horasI.setIcon(e.getImagen(0));
		minutosII.setIcon(e.getImagen(0));
		minutosI.setIcon(e.getImagen(0));
		segundosII.setIcon(e.getImagen(0));
		segundosI.setIcon(e.getImagen(0));
		panelBoton = new JPanel();
		JButton btn = new JButton("Iniciar partida");
		btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				EntidadGraficaReloj e = new EntidadGraficaReloj();
				Cronometro cr = new Cronometro();
				ActionListener accion = new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						if (!juego.gano()) {
							horasII.setIcon(e.getImagen(cr.getHoras() / 10));
							horasI.setIcon(e.getImagen(cr.getHoras() % 10));
							minutosII.setIcon(e.getImagen(cr.getMinutos() / 10));
							minutosI.setIcon(e.getImagen(cr.getMinutos() % 10));
							segundosII.setIcon(e.getImagen(cr.getSegundos() / 10));
							segundosI.setIcon(e.getImagen(cr.getSegundos() % 10)); 
						}				
					}
				};
				Timer timer = new Timer(cr.getActualizarCada(), accion);
				timer.start();
				btn.setVisible(false);//oculta el boton
				panelTablero.setVisible(true);//muestra el tablero
			}
		});
		panelBoton.add(btn);
		getContentPane().add(panelReloj,BorderLayout.NORTH);
		getContentPane().add(panelTablero,BorderLayout.CENTER);
		getContentPane().add(panelBoton,BorderLayout.SOUTH);
		int cantFilaSubPanel = juego.getCantFilaSubPanel();
		JPanel paneles[][] = new JPanel[cantFilaSubPanel][cantFilaSubPanel];
		for (int i=0; i<cantFilaSubPanel; i++) {//crea los subpaneles
			for (int j=0; j<cantFilaSubPanel; j++) {
				paneles[i][j] = new JPanel();
				paneles[i][j].setLayout(new GridLayout(juego.getCantFilaSubPanel(),juego.getCantFilaSubPanel(),0,0));
				panelTablero.add(paneles[i][j]);
				if (i==0 && j==0) {//fila 0 y columna 0
	                paneles[i][j].setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.BLACK));
	            }
	             else {
	                if (i==0) {//fila 0
	                	paneles[i][j].setBorder(BorderFactory.createMatteBorder(4, 0, 4, 4, Color.BLACK));
	                }
	                else {
	                	if (j==0) {//columna 0
	                		paneles[i][j].setBorder(BorderFactory.createMatteBorder(0, 4, 4, 4, Color.BLACK));
	                	}
	                	else {//resto de paneles
	                		paneles[i][j].setBorder(BorderFactory.createMatteBorder(0, 0, 4, 4, Color.BLACK));
	                	}
	               }
	           }
			}
		}
		for (int i=0; i<juego.getCantFilas(); i++) {//inicializa las celdas
			int m = i / cantFilaSubPanel;
			for (int j=0; j<juego.getCantFilas(); j++){
				int n = j / cantFilaSubPanel;
				JLabel label = new JLabel();
				paneles[m][n].add(label);
				Celda c = juego.getCelda(i,j);
				ImageIcon grafico = c.getEntidadGrafica().getGrafico();
				c.getEntidadGrafica().setJLabel(label);//guarda el label en la entidad gráfica de la celda
				label.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
                label.setOpaque(true);
                if (c.getCeldaFija()) {
                	label.setBackground(new Color(216,216,216));//pinta las celdas fijas de color gris
                }
				label.addComponentListener(new ComponentAdapter() {
					public void componentResized(ComponentEvent e) {
						reDimensionar(label, grafico);
						label.setIcon(grafico);
					}
				});
				label.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						if (c.getCeldaFija()==false) {//si no es una celda inicial
							juego.accionar(c);
							comprobarSolucion();
							reDimensionar(label,grafico);
							if (juego.gano()) {
								JOptionPane cartelGano = new JOptionPane();
								cartelGano.showMessageDialog(panelTablero, "Felicidades! Ha ganado la partida.", "Sudoku", JOptionPane.INFORMATION_MESSAGE);
								System.exit(0);//finaliza la ejecucion
							}
						}
					}
				});
			}
		}
	}
	
	private void comprobarSolucion() {
		Integer valor;
		for (int f=0; f<juego.getCantFilas(); f++) {//chequea cada celda para ver si cumple con las reglas
			for (int c=0; c<juego.getCantFilas() ;c++) {
				valor = juego.getCelda(f, c).getValor();
				if (valor!=null) {//chequea solo las celdas que tienen numeros
					if  (juego.cumpleReglaFila(f, c, valor) && juego.cumpleReglaColumna(f, c, valor) && juego.cumpleReglaPanel(f, c, valor)) {
						if (juego.getCelda(f, c).getCeldaFija()) {
							juego.getCelda(f, c).getEntidadGrafica().getJLabel().setBackground(new Color(216,216,216));//si es celda fija se pinta de gris
						}
						else {
							juego.getCelda(f, c).getEntidadGrafica().getJLabel().setBackground(null);//sin fondo
						}
					}
					else {
						juego.getCelda(f, c).getEntidadGrafica().getJLabel().setBackground(Color.red);//las celdas que no cumplen las reglas se pintan de rojo
					}
				}
			}
		}
	}
	
	private void reDimensionar(JLabel label, ImageIcon grafico) {
		Image image = grafico.getImage();
		if (image!=null) {  
			Image newimg = image.getScaledInstance(label.getWidth(), label.getHeight(),  java.awt.Image.SCALE_SMOOTH);
			grafico.setImage(newimg);
			label.repaint();
		}
	}

}