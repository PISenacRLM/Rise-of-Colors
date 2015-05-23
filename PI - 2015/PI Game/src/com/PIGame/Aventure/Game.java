package com.PIGame.Aventure;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.PIGame.Musica;
import com.PIGame.Aventure.entidades.mob.Jogador;
import com.PIGame.Aventure.graficos.Screen;
import com.PIGame.Aventure.input.KeyBoard;
import com.PIGame.Aventure.level.CarregandoLevel;
import com.PIGame.Aventure.level.Level;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	private static int largura = 300;
	private static int altura = largura / 16 * 10;
	private static int escala = 3;
	public static String titulo = "Raise of Color";
	// FPS
	private int FPS = 30;
	@SuppressWarnings("unused")
	private double averageFPS;

	private Thread thread;
	private JFrame quadro;
	private KeyBoard botao;
	private Level mapa;
	private Jogador player;
	private boolean rodando = false;

	// controle do menu
	private boolean menu = true;
	public int value = 0;
	private String menuInteiro;
	private Screen screen;
	Musica BMG;
	private BufferedImage imagem = new BufferedImage(largura, altura,
			BufferedImage.TYPE_INT_RGB); // cria.
	private int[] pixels = ((DataBufferInt) imagem.getRaster().getDataBuffer())
			.getData(); // l�.

	public Game() {
		Dimension tamanho = new Dimension(largura * escala, altura * escala);
		setPreferredSize(tamanho);

		screen = new Screen(largura, altura);
		quadro = new JFrame();
		botao = new KeyBoard();
		mapa = new CarregandoLevel("/Mapa/mapa01.png");
		player = new Jogador(botao);
		addKeyListener(botao);
		BMG = new Musica();

		// menu();
	}

	private void menu() {
		quadro.setLayout(new FlowLayout(FlowLayout.CENTER));

		quadro.setContentPane(new JLabel(
				new ImageIcon(
						"D:/WorkSpace/Rise of Colors/PI - 2015/PI Game/res/Mapa/Titulo.png")));

		String menu1 = "new Game";
		String menu2 = "Opc�es";
		String menu3 = "quit";

		menuInteiro = menu1 + "\n\n" + menu2 + "\n\n" + menu3;

		String mensagem = "Essa Area est� em Manuten��o.";

		switch (player.value) {
		case 5:
			quadro.dispose();

			break;
		case 1:
			menu1 = "New Game";
			menu2 = "OP��ES";
			menu3 = "quit";
			MudarMenu(menu1, menu2, menu3);
			break;

		case 2:
			menu1 = "New Game";
			menu2 = "Opc�es";
			menu3 = "QUIT!!";
			MudarMenu(menu1, menu2, menu3);
			rodando = false;
			break;

		default:
			menu1 = "NEW GAME!!";
			menu2 = "Opc�es";
			menu3 = "quit";
			MudarMenu(menu1, menu2, menu3);
			System.out.println(mensagem);
			break;
		}

	}

	public void MudarMenu(String menu1, String menu2, String menu3) {
		Label opcoes;
		menuInteiro = menu1 + "\n\n" + menu2 + "n\n" + menu3;
		opcoes = new Label(menuInteiro);
		quadro.add(opcoes);
	}

	public static int LarguraJanela() {
		return largura * escala;
	}

	public static int AlturaJanela() {
		return altura * escala;
	}

	public synchronized void inciar() {
		rodando = true;
		thread = new Thread(this, "Display");
		thread.start();
	}

	public synchronized void stop() {
		rodando = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {

		// FPS vers�o nova
		long starttime;
		long urdTimeMillis;// U : Update. R: render. D: Draw timeMillis(tempo
							// atual de execu��o)
		long waitTime;
		long totalTime = 0;
		int frameCount = 0;
		int maxFrameCount = 30;
		long targetTime = 1000 / FPS;

		while (rodando) {
			starttime = System.nanoTime();

			// urdTimeMillis:Faz corre��o de diferen�a criada entre nano e
			// milles durante o run time, se tornando o tempo real em que �
			// feito cada frame.
			urdTimeMillis = (System.nanoTime() - starttime) / 1000000;
			waitTime = targetTime - urdTimeMillis;

			try {
				// Reference https://www.youtube.com/watch?v=eYzBZ-uNZEg
				while (urdTimeMillis >= 0) {
					quadro.setTitle(titulo + "|| FPS:" + targetTime);
					update();
					render();
					// � dividido por 3, por causa do Render e Draw que est�o
					// juntos e
					// o game loop ser constituido por dois while.
					Thread.sleep(waitTime / 3);
				}

			} catch (Exception e) {

				totalTime += System.nanoTime() - starttime;
				frameCount++;
				if (frameCount == maxFrameCount) {
					// para diminuir o tamanho do valor para calculo diminuir o
					// tamanho do long
					averageFPS = 1000.0 / ((totalTime / frameCount) / 100000);
					frameCount = 0;
					totalTime = 0;

				}

			}

		}

	}

	public void update() {

		// atualiza a movimenta��o no mapa.

		botao.update();
		player.updated();

	}

	public void render() {
		BufferStrategy bs = getBufferStrategy(); // bs - buffer strategy;
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		screen.limpar();
		// Centraliza a camera no personagem
		int CenterX = player.posicaoX - screen.largura / 2;
		int CenterY = player.posicaoY - screen.altura / 2;
		mapa.render(CenterX, CenterY, screen);
		player.render(screen);

		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}

		// Criando e descartando os graficos para n�o sobrecarregar a memoria

		Graphics g = bs.getDrawGraphics(); // g = gr�ficos
		g.drawImage(imagem, 0, 0, getWidth(), getHeight(), null);
		g.setColor(Color.ORANGE);
		g.setFont(new Font("Old book style", 0, 18));
		g.drawString("X: " + player.posicaoX + " Y: " + player.posicaoY, 750,
				450);
		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {

		Game game = new Game();
		game.quadro.setResizable(false);
		game.quadro.setTitle(Game.titulo);
		game.quadro.add(game);
		game.quadro.pack();
		game.quadro.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.quadro.setLocationRelativeTo(null);
		game.quadro.setVisible(true);

		game.inciar();
	}

}
