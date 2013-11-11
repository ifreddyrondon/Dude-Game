package com.spantons.tileMap;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class TileSet {

	private int tileWidthSize;
	private int tileHeightSize;

	private int xDrawingOffSet;
	private int yDrawingOffSet;

	private BufferedImage tileSet;
	private int numRowsTileSet;
	private int numColTileSet;
	private Tile[] tiles;

	public TileSet(String src, int _tileWidthSize, int _tileHeightSize, int _xDrawingOffSet, int _yDrawingOffSet) {
		try {
			
			tileWidthSize = _tileWidthSize;
			tileHeightSize = _tileHeightSize;
			xDrawingOffSet = _xDrawingOffSet;
			yDrawingOffSet = _yDrawingOffSet;
			
			// Cargamos la imagen completa del tileset
			tileSet = ImageIO.read(getClass().getResourceAsStream(src));

			// Obtenemos el numero de tiles a lo largo y ancho del tileset
			numRowsTileSet = tileSet.getHeight()
					/ tileHeightSize;
			numColTileSet = tileSet.getWidth()
					/ tileWidthSize;
			
			// le damos memoria a la matriz que almacena los tiles
			tiles = new Tile[numRowsTileSet * numColTileSet];

			// llenamos la matriz con los tiles
			BufferedImage subImage;
			int contador = 0;
			for (int row = 0; row < numRowsTileSet; row++) {
				for (int col = 0; col < numColTileSet; col++) {
					subImage = tileSet.getSubimage(
							col * tileWidthSize, 
							row * tileHeightSize,
							tileWidthSize - xDrawingOffSet, 
							tileHeightSize - yDrawingOffSet);
					
					tiles[contador] = new Tile(subImage, Tile.NORMAL);
					contador++;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public int getTypeOfTile(int numMatrixMap) {
		return tiles[numMatrixMap -1].getType();
	}
	
	
	public int getTileWidthSize(){
		return tileWidthSize;
	}
	
	public int getTileHeightSize(){
		return tileHeightSize;
	}
	
	public int getXDrawingOffSet(){
		return xDrawingOffSet;
	}
	
	public int getYDrawingOffSet(){
		return yDrawingOffSet;
	}
	
	public Tile[] getTiles(){
		return tiles;
	}
	
	public int getNumRowsTileSet(){
		return numRowsTileSet;
	}
	
	public int getNumColTileSet(){
		return numColTileSet;
	}
		

}
