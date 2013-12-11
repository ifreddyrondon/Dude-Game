package com.spantons.tileMap;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.spantons.entity.Entity;
import com.spantons.main.GamePanel;

public class TileMap {

	private int x;
	private int y;

	// limites
	private int xMin;
	private int yMin;
	private int xMax;
	private int yMax;

	// mapa
	private int[][] map;
	public int tileWidthSize;
	public int tileHeightSize;
	private int numRowsMap;
	private int numColMap;
	private int mapWidth;
	private int mapHeight;

	// dibujado
	private Point coorMapTopLeft;
	private Point coorMapTopRight;
	private Point coorMapBottomLeft;
	private Point coorMapBottomRight;

	// tileset
	private TileSet tileSet;
	private Tile[] tiles;

	/****************************************************************************************/
	public TileMap(int _tileWidthSize, int _tileHeightSize) {

		tileSet = new TileSet("/tilesets/isometric_grass_and_water.png",
				64, 64, 0, 0);

		tileWidthSize = _tileWidthSize;
		tileHeightSize = _tileHeightSize;
		 tiles = tileSet.getTiles();
	}
	/****************************************************************************************/
	public Point getMultipleOfTileWidthAndHeight(int _x, int _y){
		
		int x, y;
		
		if (_x % tileWidthSize == 0) 
			x = _x;
		else 
			x = ((int) (_x / tileWidthSize)) * tileWidthSize;
		
		if (_y % tileHeightSize == 0) 
			y = _y;
		else 
			y = ((int) (_y / tileHeightSize)) * tileHeightSize;

		return new Point(x,y);
	}
	/****************************************************************************************/
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
			yMin = tileHeightSize / 2;
			Point fix = getMultipleOfTileWidthAndHeight(xMin, yMin);
			xMin = fix.x;
			yMin = fix.y;
			
			xMax = tileWidthSize / 2;
			yMax = mapHeight - GamePanel.RESOLUTION_HEIGHT + tileHeightSize / 2;
			fix = getMultipleOfTileWidthAndHeight(xMax, yMax);
			xMax = fix.x;
			yMax = fix.y;

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
	/****************************************************************************************/
	public Point absoluteToMap(double x, double y) {
		int mapX = (int) ((x / (tileWidthSize / 2) + y / (tileHeightSize / 2)) / 2);
		int mapY = (int) ((y / (tileHeightSize / 2) - (x / (tileWidthSize / 2))) / 2);

		return new Point(mapX, mapY);
	}
	/****************************************************************************************/
	public Point mapToAbsolute(double x, double y) {
		
		int absoluteX = (int) ((x - y) * (tileWidthSize / 2));
		int absoluteY = (int) ((x + y) * (tileHeightSize / 2));

		return new Point(absoluteX, absoluteY);
	}
	/****************************************************************************************/
	public void fixBounds() {
		if (x < xMin){
			System.out.println(x);
			System.out.println(xMin);
			x = xMin;
			
		}
			
			
		if (x > xMax) x = xMax;
		if (y < yMin) y = yMin;
		if (y > yMax) y = yMax;
	}
	/****************************************************************************************/
	
	public void setPosition(int x, int y) {
		// La nueva posicion debe ser multiplo del tileWidth en X y tileHeight en Y
		if (x % tileWidthSize == 0 && y % tileHeightSize == 0) {
			this.x = x;
			this.y = y;

		} else {
			Point multiple = getMultipleOfTileWidthAndHeight(x, y);
			this.x = multiple.x;
			this.y = multiple.y;
			fixBounds();
		}
						
	}
	/****************************************************************************************/
	public void update(){
		 setPosition(x, y);
	}
	/****************************************************************************************/
	public void draw(Graphics2D g) {
		// Pintamos el fondo de gris
		g.setColor(Color.gray);
		g.fillRect(0, 0, GamePanel.RESOLUTION_WIDTH,
						GamePanel.RESOLUTION_HEIGHT);
		

		coorMapTopLeft = absoluteToMap(x, y);
		coorMapTopRight = absoluteToMap(x + GamePanel.RESOLUTION_WIDTH, y);
		coorMapBottomLeft = absoluteToMap(x, y + GamePanel.RESOLUTION_HEIGHT);
		coorMapBottomRight = absoluteToMap(x + GamePanel.RESOLUTION_WIDTH, y + GamePanel.RESOLUTION_HEIGHT);
		
		//Desplazamos cada esquina para tener el buffer con un poquito mas
		coorMapTopLeft = Entity.tileWalk("norte oeste", coorMapTopLeft, 3);
		coorMapTopRight = Entity.tileWalk ("norte este", coorMapTopRight, 3);
		coorMapBottomLeft = Entity.tileWalk ("sur oeste", coorMapBottomLeft, 3);
		coorMapBottomRight = Entity.tileWalk ("sur este", coorMapBottomRight, 3);

		//Desplazamos las esquinas inferiores 2 pasos al sur para compensar por los objetos altos
		coorMapBottomLeft = Entity.tileWalk ("sur", coorMapBottomLeft, 2);
		coorMapBottomRight = Entity.tileWalk ("sur", coorMapBottomRight, 2);

		// banderas de dibujado
		boolean completed, completedRow;
		Point firstTileOfRowToDraw, finalTileOfRowToDraw, currentTile;
		Point coorAbsolute;
		int rowCounter = 0;

		completed = false;
		firstTileOfRowToDraw = coorMapTopLeft;
		finalTileOfRowToDraw = coorMapTopRight;		
		
		// Para cada fila
		while (!completed) {
			completedRow = false;
			// Seleccionamos la primera casilla
			currentTile = firstTileOfRowToDraw;
			
			// Para cada casilla
			while (!completedRow) {
				
				// Comprobamos que no sea transparente para pintarlo
				if (currentTile.x >= 0 && currentTile.y >= 0
					&& currentTile.x < numColMap
					&& currentTile.y < numRowsMap) {
				
					coorAbsolute = mapToAbsolute(currentTile.x,currentTile.y);
					
					g.drawImage(
						tiles[map[(int) currentTile.y][(int) currentTile.x] - 1].getImage(), 
						(int) (coorAbsolute.x - this.x), 
						(int) (coorAbsolute.y - this.y),
						null);
				}
								 
				// Si llego al final de la fila nos salimos
				if (currentTile.x == finalTileOfRowToDraw.x
						&& currentTile.y == finalTileOfRowToDraw.y)
					completedRow = true;
				else 
					currentTile = Entity.tileWalk("este", currentTile,1);
						
			}
			
			// Comprobamos si la fila recorrida era la ultima
			if (	firstTileOfRowToDraw.x > coorMapBottomLeft.x && 
				firstTileOfRowToDraw.y > coorMapBottomLeft.y &&
				finalTileOfRowToDraw.x > coorMapBottomRight.x && 
				finalTileOfRowToDraw.y > coorMapBottomRight.y)
				completed = true;
			
			else {
				// Si no lo era, movemos las casillas de inicio y fin
				// hacia abajo para comenzar con la siguiente

				if (rowCounter % 2 != 0) {
					// Fila impar
					firstTileOfRowToDraw = Entity.tileWalk("sur oeste",
							firstTileOfRowToDraw, 1);
					finalTileOfRowToDraw = Entity.tileWalk("sur este", finalTileOfRowToDraw, 1);

				} else {
					// Fila par
					firstTileOfRowToDraw = Entity.tileWalk("sur este",
							firstTileOfRowToDraw, 1);
					finalTileOfRowToDraw = Entity
							.tileWalk("sur oeste", finalTileOfRowToDraw, 1);

				}
				rowCounter++;
			}
		}
		
		
		/*
		for (int row = 0; row < mapHeight; row++) {
		//for (int row = 0; row < numRowsMap; row++) {
			 // No dibujar mas de las filas que tiene el mapa
			 if (row >= numRowsMap)
			 	break;
			 
			for (int col = 0; col < mapWidth; col++) {
			//for (int col = 0; col <  numColMap; col++) {
				// No dibujar mas de las columnas que tiene el mapa
			 	if (col >= numColMap)
			 		break;
			 		
			 	int tileToDraw = map[row][col] -1;
				int px = (int) ((col - row) * (tileWidthSize / 2) - this.x);
				int py = (int) ((col + row) * (tileHeightSize / 2) - this.y);
			 
				g.drawImage(tiles[tileToDraw].getImage(), px, py, null);
			 }
		}
		*/
		
	}
	/****************************************************************************************/
	public int getTileWidthSize() {
		return tileWidthSize;
	}

	public int getTileHeightSize() {
		return tileHeightSize;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int[][] getMap() {
		return map;
	}
	
	public int getMapWidth() {
		return mapWidth;
	}

	public int getMapHeight() {
		return mapHeight;
	}
	
	public int getNumColMap() {
		return numColMap;
	}
	
	public int getNumRowsMap() {
		return numRowsMap;
	}
}
