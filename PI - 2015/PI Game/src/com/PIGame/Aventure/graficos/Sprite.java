package com.PIGame.Aventure.graficos;

public class Sprite {

	public final int TAMANHO;
	private int larguraX, alturaY;
	public int[] pixels;
	private SpriteSheet lamina;
	
	public static Sprite floorSprite01 = new Sprite(16, 6, 0, SpriteSheet.tiles);
	public static Sprite floorSprite02 = new Sprite(16, 6, 1, SpriteSheet.tiles);
	public static Sprite floorSprite03 = new Sprite(16, 5, 1, SpriteSheet.tiles);
	public static Sprite vazio = new Sprite(16, 0);
	
	
	
	public static Sprite player_deCosta = new Sprite(32, 0, 5, SpriteSheet.tiles);
	public static Sprite player_deFrente = new Sprite(32, 2, 5, SpriteSheet.tiles);
	public static Sprite player_DireitaEsquerda = new Sprite(32, 1, 5, SpriteSheet.tiles);
	
	public static Sprite player_deCosta1 = new Sprite(32, 0 , 6,SpriteSheet.tiles);
	public static Sprite player_deCosta2 = new Sprite(32, 0 , 7,SpriteSheet.tiles);
	
	public static Sprite player_DireitaEsquerda1 = new Sprite(32, 1 , 6,SpriteSheet.tiles);
	public static Sprite player_DireitaEsquerda2 = new Sprite(32, 1 , 7,SpriteSheet.tiles);

	public static Sprite player_deFrente1 = new Sprite(32, 2 , 6,SpriteSheet.tiles);
	public static Sprite player_deFrente2 = new Sprite(32, 2 , 7,SpriteSheet.tiles);

	public Sprite(int tamanho, int x, int y, SpriteSheet folha) {

		TAMANHO = tamanho;
		pixels = new int[TAMANHO * TAMANHO];
		this.larguraX = x * tamanho;
		this.alturaY = y * tamanho;
		this.lamina = folha;
		Carregar();

	}
	
	public Sprite(int tamanho, int cor){
		TAMANHO = tamanho;
		pixels = new int[TAMANHO*TAMANHO];
		setCor(cor);
	}
	
	private void setCor(int cor){
		for(int i = 0 ;i < TAMANHO*TAMANHO ;i++){
			pixels[i] = cor;
		}
	}

	private void Carregar() {

		for (int y = 0; y < TAMANHO; y++) {
			for (int x = 0; x < TAMANHO; x++) {

				pixels[x + y * TAMANHO] = lamina.pixels[(x + this.larguraX) + (y + this.alturaY) * lamina.TAMANHO];

			}
		}

	}

}
