package com.spantons.tileMap;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import com.spantons.main.GamePanel;

public class TileMap {

	/**
	 * Posicion X en el TileMap
	 */
	private double x;
	/**
	 * Posicion Y en el TileMap
	 */
	private double y;

	// limites
	private int xMin;
	private int yMin;
	private int xMax;
	private int yMax;

	// mapa
	private int[][] map;
	private int tileWidthSize;
	private int tileHeightSize;
	private int numRowsMap;
	private int numColMap;
	private int mapWidth;
	private int mapHeight;

	// tileset
	private BufferedImage tileSet;
	private int numRowsTileSet;
	private int numColTileSet;
	private Tile[][] tiles;

	// dibujado
	private int rowOffSet;
	private int colOffSet;
	private int numRowDraw;
	private int numColDraw;

	public TileMap(int tileWidthSize, int tileHeightSize) {
		this.tileWidthSize = tileWidthSize;
		this.tileHeightSize = tileHeightSize;
		// Obtenemos el numero de filas y columnas a dibujar segun la
		// resolucion, y sumamos 2 para mantener el Buffer un poco mas largo
		numRowDraw = (GamePanel.RESOLUTION_HEIGHT / tileHeightSize) + 2;
		numColDraw = (GamePanel.RESOLUTION_WIDTH / tileWidthSize) + 2;
	}

	public void loadTiles(String s) {
		try {
			// Cargamos la imagen completa del tileset
			tileSet = ImageIO.read(getClass().getResourceAsStream(s));
			
			// Obtenemos el numero de tiles a lo largo y ancho del tileset
			numRowsTileSet = tileSet.getHeight() / tileWidthSize;
			numColTileSet = tileSet.getWidth() / tileWidthSize;
			// le damos memoria a la matriz que almacena los tiles
			tiles = new Tile[numRowsTileSet][numColTileSet];
			
			System.err.println(numRowsTileSet);
			System.err.println(numColTileSet);
			
			// llenamos la matriz con los tiles
			BufferedImage subImage;
			for (int row = 0; row < numRowsTileSet; row++) {
				for (int col = 0; col < numColTileSet; col++) {
					subImage = tileSet.getSubimage(col * tileWidthSize, row * tileHeightSize,
							tileWidthSize, tileHeightSize);
					tiles[row][col] = new Tile(subImage, Tile.NORMAL);
				}
			}
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadMap(String s) {

		try {
			InputStream in = getClass().getResourceAsStream(s);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					in));

			// Numero de filas y columnas del mapa
			numColMap = Integer.parseInt(br.readLine());
			numRowsMap = Integer.parseInt(br.readLine());
			// Memoria a la matriz del mapa
			map = new int[numRowsMap][numColMap];
			mapWidth = numColMap * tileWidthSize;
			mapHeight = numRowsMap * tileWidthSize;
			
			xMin = GamePanel.WIDTH - mapWidth;
			xMax = 0;
			yMin = GamePanel.HEIGHT - mapHeight;
			yMax = 0;
			
			// llenamos la matriz map
			String delimsChar = "\\s+";
			for (int row = 0; row < numRowsMap; row++) {
				String line = br.readLine();
				String[] tokens = line.split(delimsChar);
				for (int col = 0; col < numColMap; col++) {
					map[row][col] = Integer.parseInt(tokens[col]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Obtenedores
	public int getTamanoTile() {
		return tileWidthSize;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public int getAnchoMapa() {
		return mapWidth;
	}

	public int getAltoMapa() {
		return mapHeight;
	}

	
	/**
	 * Obtiene el tipo de tile dentro del mapa 
	 * @param row (Fila del mapa)
	 * @param col  (Columna del mapa)
	 * @return
	 */
	public int getTypeOfTile(int row, int col) {
		// Obtenemos el numero del tile en la matriz map
		int rc = map[row][col];
		// Obtenemos la fila y la columna en la matriz tileset
		int r = rc / numRowsTileSet;
		int c = rc % numRowsTileSet;
		return tiles[r][c].getType();
	}

	public void setPosition(double x, double y) {
		
		// Probando efecto suavisado el seguimiento de la camara
		this.x += (x - this.x) * 0.1;
		this.y += (y - this.y) * 0.1;

		fixBounds();
		
		// donde comenzar a dibujar
		colOffSet = (int) -this.x / tileWidthSize;
		rowOffSet = (int) -this.y / tileWidthSize;
	}

	private void fixBounds() {
		if (x < xMin) x = xMin;
		if (y < yMin) y = yMin;
		if (x > xMax) x = xMax;
		if (y > yMax) y = yMax;
	}

	public void draw(Graphics2D g) {
		for (int row = 0; row < numRowsTileSet; row++) {
			for (int col = 0; col < numColTileSet; col++) 
				g.drawImage(tiles[row][col].getImage(), col * tileWidthSize, row * tileHeightSize, null);
		}

		
		/*
		for (int row = rowOffSet; row < rowOffSet + numRowDraw; row++) {
			// No dibujar mas de las filas que tiene el mapa
			if (row >= numRowsMap)
				break;

			for (int col = colOffSet; col < colOffSet + numColDraw; col++) {
				// No dibujar mas de las columnas que tiene el mapa
				if (col >= numColMap)
					break;
				// Si la posicion en el map es 0 es transparente no me
				// molesto en dibujarla
				if (map[row][col] == 0)
					continue;
				
				int rc = map[row][col];
				int r = rc / numRowsTileSet;
				int c = rc % numRowsTileSet;
				
				g.drawImage(tiles[r][c].getImage(), (int)x + col*tileWidthSize, (int)y + row *tileWidthSize, null);
			}
	
		}
		*/
	}

}
