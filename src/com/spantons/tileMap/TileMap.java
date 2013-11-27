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
	private Point coorMapTopLeft;
	private Point coorMapTopRight;
	private Point coorMapBottomLeft;
	private Point coorMapBottomRight;

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

	public Point absoluteToMap(int x, int y) {
		int mapX = (x / (tileWidthSize / 2) + y / (tileHeightSize / 2)) / 2;
		int mapY = (y / (tileHeightSize / 2) - (x / (tileWidthSize / 2))) / 2;

		return new Point(mapX, mapY);
	}

	public Point mapToAbsolute(int x, int y) {
		int absoluteX = (int) ((x - y) * (tileWidthSize / 2));
		int absoluteY = (int) ((x + y) * (tileHeightSize / 2));

		return new Point(absoluteX, absoluteY);
	}

	public void setPosition(double x, double y) {

		// Probando efecto suavisado el seguimiento de la camara
		// this.x += (x - this.x) * 0.1;
		// this.y += (y - this.y) * 0.1;

		this.x = x;
		this.y = y;

		fixBounds();

		/************************************************/
		coorMapTopLeft = absoluteToMap((int) x - tileWidthSize, (int) y);
		coorMapTopRight = absoluteToMap(
				(int) (x + GamePanel.RESOLUTION_WIDTH), (int) y);
		coorMapBottomLeft = absoluteToMap((int) x - tileWidthSize,
				(int) (y + GamePanel.RESOLUTION_HEIGHT));
		coorMapBottomRight = absoluteToMap(
				(int) (x + GamePanel.RESOLUTION_WIDTH),
				(int) (y + GamePanel.RESOLUTION_HEIGHT));

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
		// Pintamos el fondo de gris
		g.setColor(Color.gray);
		g.fillRect(0, 0, GamePanel.RESOLUTION_WIDTH,
				GamePanel.RESOLUTION_HEIGHT);

		Tile[] tiles = tileSet.getTiles();
		
		// banderas de dibujado
		boolean completed, completedRow;
		
		Point firstTileOfRowToDraw, finalTileOfRowToDraw, currentTile;
		int contadorFilas = 0;

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
					
					Point coorToDraw = mapToAbsolute(currentTile.x,
							currentTile.y);
					int px = (int) (coorToDraw.x - this.x);
					int py = (int) (coorToDraw.y - this.y);

					int tileToDraw = map[currentTile.y][currentTile.x] - 1;
					g.drawImage(tiles[tileToDraw].getImage(), px, py,
							null);
				}
				// Si llego al final de la fila nos salimos
				if (currentTile.x == finalTileOfRowToDraw.x
						&& currentTile.y == finalTileOfRowToDraw.y)
					completedRow = true;
				else
					currentTile = Entity.tileWalk("este", currentTile,
							1);

			}

			// Comprobamos si la fila recorrida era la ultima
			if (firstTileOfRowToDraw.x == coorMapBottomLeft.x
					&& firstTileOfRowToDraw.y == coorMapBottomLeft.y
					&& finalTileOfRowToDraw.x == coorMapBottomRight.x
					&& finalTileOfRowToDraw.y == coorMapBottomRight.y)
				completed = true;

			else {

				// Si no lo era, movemos las casillas de inicio y fin
				// hacia abajo para comenzar con la siguiente

				if (contadorFilas % 2 != 0) {
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
				++contadorFilas;
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
