package com.spantons.tileMap;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

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

	// dibujado
	private int rowOffSet;
	private int colOffSet;
	private int numRowDraw;
	private int numColDraw;

	// tileset
	private TileSet tileSet;

	public TileMap(int tileWidthSize, int tileHeightSize) {

		tileSet = new TileSet("/tilesets/isometric_grass_and_water.png",
				64, 64, 0, 0);

		this.tileWidthSize = tileWidthSize;
		this.tileHeightSize = tileHeightSize;
		// Obtenemos el numero de filas y columnas a dibujar segun la
		// resolucion, y sumamos 2 para mantener el Buffer un poco mas largo
		numRowDraw = (GamePanel.RESOLUTION_HEIGHT / tileHeightSize) + 2;
		numColDraw = (GamePanel.RESOLUTION_WIDTH / tileWidthSize) + 2;
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
			mapHeight = numRowsMap * tileHeightSize;

			xMin = GamePanel.RESOLUTION_WIDTH - mapWidth;
			xMax = 0;
			yMin = GamePanel.RESOLUTION_HEIGHT - mapHeight;
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

	public void setPosition(double x, double y) {

		// Probando efecto suavisado el seguimiento de la camara
		//this.x += (x - this.x) * 0.1;
		//this.y += (y - this.y) * 0.1;

		this.x = x;
		this.y = y;
		
		//fixBounds();

		// donde comenzar a dibujar
		colOffSet = (int) -this.x / tileWidthSize;
		rowOffSet = (int) -this.y / tileHeightSize;
	}

	private void fixBounds() {
		if (x < xMin)
			x = xMin;
		if (y < yMin)
			y = yMin;
		if (x > xMax)
			x = xMax;
		if (y > yMax)
			y = yMax;
	}

	public void draw(Graphics2D g) {

		g.setColor(Color.gray);
		g.fillRect(0, 0, GamePanel.RESOLUTION_WIDTH,
				GamePanel.RESOLUTION_HEIGHT);

		Tile[] tiles = tileSet.getTiles();
		int px, py, tileToDraw;
		
		
		for (int row = 0; row < mapHeight; row++) {
			// No dibujar mas de las filas que tiene el mapa
			if (row >= numRowsMap)
				break;

			for (int col = 0; col < mapWidth; col++) {
				// No dibujar mas de las columnas que tiene el mapa
				if (col >= numColMap)
				break;
		
				tileToDraw = map[row][col] -1;
				px = (int) ((col - row) * (tileWidthSize / 2) - this.x);
				py = (int) ((col + row) * (tileHeightSize / 2) - this.y);

				g.drawImage(tiles[tileToDraw].getImage(), px, py, null);
			}
		}
		
		
	}
	
	
	public int getTileWidthSize() {
		return tileWidthSize;
	}

	public int getTileHeightSize() {
		return tileHeightSize;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public int getMapWidth() {
		return mapWidth;
	}

	public int getMapHeight() {
		return mapHeight;
	}

	
}
