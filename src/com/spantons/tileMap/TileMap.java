package com.spantons.tileMap;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import utilities.Coordinate;

import com.spantons.entity.Entity;
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
	
	// coordenadas de las esquinas
	private int xTopLeft;
	private int yTopLeft;
	private int xTopRight;
	private int yTopRight;
	private int xBottomLeft;
	private int yBottomLeft;
	private int xBottomRight;
	private int yBottomRight;
	

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
		
		fixBounds();		
			
		// fila y columna absoluta 
		
		xTopLeft = (int)(this.x / tileHeightSize); 
		yTopLeft = (int) (this.y / tileHeightSize);
		
		xTopRight = (int) (this.x + GamePanel.RESOLUTION_WIDTH) / tileWidthSize;
		yTopRight = yTopLeft;
		
		xBottomLeft = xTopLeft;
		yBottomLeft = (int) (this.y + GamePanel.RESOLUTION_HEIGHT) / tileHeightSize;
	
		xBottomRight = xTopRight;
		yBottomRight = yBottomLeft;
		
		Coordinate casilla = Entity.tileWalk ("este", 0, xBottomRight);
		casilla = Entity.tileWalk ("sur", casilla.getX(), yBottomRight);		
		
		System.out.println(casilla.getX());
		System.out.println(casilla.getY());
		System.err.println(map[casilla.getX()][casilla.getY()]);

		
		/*
		System.out.println("Esquina Superior IZQ");
		System.out.println(xTopLeft);
		System.out.println(yTopLeft);
		//System.err.println(map[xTopLeft][yTopLeft]);
		System.out.println();
		System.out.println("Esquina Superior DER");
		System.out.println(xTopRight);
		System.out.println(yTopRight);
		//System.err.println(map[xTopRight][yTopRight]);
		System.out.println();
		System.out.println("Esquina INFERIOR IZQ");
		System.out.println(xBottomLeft);
		System.out.println(yBottomLeft);
		//System.err.println(map[xBottomLeft][yBottomLeft]);
		System.out.println();
		System.out.println("Esquina INFERIOR DEr");
		System.out.println(xBottomRight);
		System.out.println(yBottomRight);
		System.err.println(map[xBottomRight][yBottomRight]);
		*/
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
		
		
		for (int row = 0; row < numRowsMap; row++) {
			// No dibujar mas de las filas que tiene el mapa
			if (row >= numRowsMap)
				break;

			for (int col = 0; col <  numColMap; col++) {
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
