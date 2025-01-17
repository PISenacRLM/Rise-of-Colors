package com.PIGame.Aventure.level;

import com.PIGame.Aventure.graficos.Screen;
import com.PIGame.Aventure.level.tile.Tile;

public class Level {

	
	protected int largura, altura;
	protected int[] tiles;
	
	public Level(int x, int y){
		
		this.largura = x;
		this.altura = y;
		tiles = new int[largura * altura];
		criarLevelRandomico();
		
	}
	
	public Level(String path){
		CarregarLevel(path);
	}

	protected void criarLevelRandomico() {
		
	}
	protected void CarregarLevel(String path) {
		
	}
	
	
	public void update(){
		
	}
	
	private void tempo(){
		
	}
	
	//localiza a posi��o onde cada tile vai ficar no mapa
	
	public void render(int XScroll, int YScroll, Screen tela){
	    tela.atualizacao(XScroll, YScroll);
		int x0 = XScroll >> 4;
		int x1 = (XScroll + tela.largura + 16) >> 4;
		int y0 = YScroll >> 4;
		int y1 = (YScroll + tela.altura + 16) >> 4;
		
		//faz um render da posicao 0.0 para esquerda 0.1,0.2 e assim por diante
		for(int y = y0;y < y1; y++){
			for(int x = x0; x < x1; x++){
				getTile(x, y).render(x, y, tela);
				
			}
		}
	}
	
	public Tile getTile(int posicaoX, int posicaoY){
		if(posicaoX < 0 || posicaoY < 0 || posicaoX >= largura || posicaoY >= altura) return Tile.vazio;
		
		if(tiles[posicaoX+posicaoY*largura] == 0xff707070) return Tile.floorSprite01;
		if(tiles[posicaoX+posicaoY*largura] == 0xffaaaaaa) return Tile.floorSprite02;
		if(tiles[posicaoX+posicaoY*largura] == 0xffd6d6d6) return Tile.floorSprite03;
		return Tile.vazio;
	}
}
