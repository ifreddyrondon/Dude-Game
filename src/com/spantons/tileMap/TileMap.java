package com.spantons.tileMap;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import utilities.Multiple;
import utilities.TileWalk;

import com.spantons.entity.Entity;
import com.spantons.main.GamePanel;
import com.spantons.object.Object;

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
	private Set<Integer> unlockedTiles;
	public Point tileSize;
	private int numRowsMap;
	private int numColMap;

	// dibujado
	private Point coorMapTopLeft;
	private Point coorMapTopRight;
	private Point coorMapBottomLeft;
	private Point coorMapBottomRight;

	// resolucion arreglada para el tamano del tile
	public int RESOLUTION_WIDTH_FIX;
	public int RESOLUTION_HEIGHT_FIX;

	// tileset
	private TileSet tileSet;
	private Tile[] tiles;
	private Entity[][] entitysDeadToDraw;
	private Entity[][] entitysToDraw;
	private Object[][] objectsToDraw;

	/****************************************************************************************/
	public TileMap(int _tileWidthSize, int _tileHeightSize, TileSet _tileSet) {

		tileSet = _tileSet;
		tileSize = new Point(_tileWidthSize,_tileHeightSize);
		tiles = tileSet.getTiles();
		
		if (GamePanel.RESOLUTION_WIDTH % _tileWidthSize == 0
				&& GamePanel.RESOLUTION_HEIGHT % _tileHeightSize == 0) {
			RESOLUTION_WIDTH_FIX = GamePanel.RESOLUTION_WIDTH;
			RESOLUTION_HEIGHT_FIX = GamePanel.RESOLUTION_HEIGHT;

		} else {
			Point fixResolution = Multiple
					.findPointCloserTo(new Point(
							GamePanel.RESOLUTION_WIDTH,
							GamePanel.RESOLUTION_HEIGHT),tileSize);

			RESOLUTION_WIDTH_FIX = fixResolution.x;
			RESOLUTION_HEIGHT_FIX = fixResolution.y;
		}
 
	}

	/****************************************************************************************/
	public void loadMap(String s) {

		try {
			InputStream in = getClass().getResourceAsStream(s);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					in));

			numColMap = Integer.parseInt(br.readLine());
			numRowsMap = Integer.parseInt(br.readLine());
			map = new int[numRowsMap][numColMap];
			entitysToDraw = new Entity[numRowsMap][numColMap];
			entitysDeadToDraw = new Entity[numRowsMap][numColMap];
			objectsToDraw = new Object[numRowsMap][numColMap];
			
			// Numero de tiles bloqueados
			int numBlockedTiles = Integer.parseInt(br.readLine());
			// Memoria al Set de tiles bloqueados
			unlockedTiles = new HashSet<Integer>();

			String delimsChar = ",";
			// Tiles bloqueados
			String line = br.readLine();
			String[] tokens = line.split(delimsChar);
			for (int i = 0; i < numBlockedTiles; i++)
				unlockedTiles.add(Integer.parseInt(tokens[i]));

			// llenamos la matriz map
			for (int row = 0; row < numRowsMap; row++) {
				line = br.readLine();
				tokens = line.split(delimsChar);
				for (int col = 0; col < numColMap; col++)
					map[row][col] = Integer.parseInt(tokens[col]);
			}

			getBounds();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/****************************************************************************************/
	private void getBounds() {

		xMin = -mapToAbsolute(numRowsMap - 1, 0).x - tileSize.y;
		yMin = -tileSize.y;
		Point fix = Multiple.findPointCloserTo(new Point(
				xMin, yMin),tileSize);
		xMin = fix.x;
		yMin = fix.y;

		xMax = (-mapToAbsolute(0, numColMap - 1).x - RESOLUTION_WIDTH_FIX)
				+ tileSize.x * 2;
		yMax = (mapToAbsolute(numRowsMap - 1, numColMap - 1).y - RESOLUTION_HEIGHT_FIX)
				+ tileSize.y * 2;
		fix = Multiple.findPointCloserTo(new Point(xMax, yMax),tileSize);
		xMax = fix.x;
		yMax = fix.y;
		
	}

	/****************************************************************************************/
	public Point absoluteToMap(int x, int y) {
		int mapX = ((x / (tileSize.x / 2) + y
				/ (tileSize.y / 2)) / 2);
		int mapY = ((y / (tileSize.y / 2) - (x / (tileSize.x / 2))) / 2);

		return new Point(mapX, mapY);
	}

	/****************************************************************************************/
	public Point mapToAbsolute(int x, int y) {

		int absoluteX = ((x - y) * (tileSize.x / 2));
		int absoluteY = ((x + y) * (tileSize.y / 2));

		return new Point(absoluteX, absoluteY);
	}
	/****************************************************************************************/
	public void fixBounds() {
		if (x < xMin)
			x = xMin;
		if (y < yMin)
			y = yMin;
		if (x > xMax)
			x = xMax;
		if (y > yMax)
			y = yMax;
	}
	/****************************************************************************************/
	public void setPosition(int _x, int _y) {
		if (_x % tileSize.x == 0 && _y % tileSize.y == 0) {
			x = _x;
			y = _y;
		} else {
			Point multiple = Multiple.findPointCloserTo(
					new Point(_x, _y), tileSize);
			x = multiple.x;
			y = multiple.y;
		}
		fixBounds();
	}

	/****************************************************************************************/
	public void update() {
		setPosition(x, y);
	}

	/****************************************************************************************/
	public void draw(Graphics2D g) {
		// Pintamos el fondo de gris
		g.setColor(Color.gray);
		g.fillRect(0, 0, GamePanel.RESOLUTION_WIDTH,
				GamePanel.RESOLUTION_HEIGHT);

		coorMapTopLeft = absoluteToMap(x, y);
		coorMapTopRight = absoluteToMap(x + RESOLUTION_WIDTH_FIX, y);
		coorMapBottomLeft = absoluteToMap(x, y + RESOLUTION_HEIGHT_FIX);
		coorMapBottomRight = absoluteToMap(x + RESOLUTION_WIDTH_FIX, y
				+ RESOLUTION_HEIGHT_FIX);

		// Desplazamos cada esquina para tener el buffer con un poquito mas
		coorMapTopLeft = TileWalk.walkTo("NW", coorMapTopLeft, 3);
		coorMapTopRight = TileWalk.walkTo("NE", coorMapTopRight, 3);
		coorMapBottomLeft = TileWalk.walkTo("SW", coorMapBottomLeft, 3);
		coorMapBottomRight = TileWalk.walkTo("SE", coorMapBottomRight, 3);

		// Desplazamos las esquinas inferiores 2 pasos al sur para compensar
		// por los objetos altos
		coorMapBottomLeft = TileWalk.walkTo("S", coorMapBottomLeft, 2);
		coorMapBottomRight = TileWalk.walkTo("S", coorMapBottomRight, 2);

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

					Object object = objectsToDraw[currentTile.x][currentTile.y];
					Entity entity = entitysToDraw[currentTile.x][currentTile.y];
					Entity entityDead = entitysDeadToDraw[currentTile.x][currentTile.y];
					
					coorAbsolute = mapToAbsolute(currentTile.x,
							currentTile.y);

					g.drawImage(tiles[map[currentTile.y][currentTile.x] - 1]
							.getImage(), 
							(coorAbsolute.x - this.x),
							(coorAbsolute.y - this.y), null);
					
					if (entityDead != null) 
						entityDead.draw(g);
					if (object != null) 
						object.draw(g);
					if (entity != null) 
						entity.draw(g);
				}

				// Si llego al final de la fila nos salimos
				if (currentTile.x == finalTileOfRowToDraw.x
						&& currentTile.y == finalTileOfRowToDraw.y)
					completedRow = true;
				else
					currentTile = TileWalk
							.walkTo("E", currentTile, 1);

			}

			// Comprobamos si la fila recorrida era la ultima
			if (firstTileOfRowToDraw.x > coorMapBottomLeft.x
					&& firstTileOfRowToDraw.y > coorMapBottomLeft.y
					&& finalTileOfRowToDraw.x > coorMapBottomRight.x
					&& finalTileOfRowToDraw.y > coorMapBottomRight.y)
				completed = true;

			else {
				// Si no lo era, movemos las casillas de inicio y fin
				// hacia abajo para comenzar con la siguiente

				if (rowCounter % 2 != 0) {
					// Fila impar
					firstTileOfRowToDraw = TileWalk.walkTo("SW",
							firstTileOfRowToDraw, 1);
					finalTileOfRowToDraw = TileWalk.walkTo("SE",
							finalTileOfRowToDraw, 1);

				} else {
					// Fila par
					firstTileOfRowToDraw = TileWalk.walkTo("SE",
							firstTileOfRowToDraw, 1);
					finalTileOfRowToDraw = TileWalk.walkTo("SW",
							finalTileOfRowToDraw, 1);

				}
				rowCounter++;
			}
		}
	}
	/****************************************************************************************/

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getNumColMap() {
		return numColMap;
	}

	public int getNumRowsMap() {
		return numRowsMap;
	}

	public int getXMin() {
		return xMin;
	}

	public int getYMin() {
		return yMin;
	}

	public int getXMax() {
		return xMax;
	}

	public int getYMax() {
		return yMax;
	}

	public Entity[][] getEntitysToDraw() {
		return entitysToDraw;
	}
	
	public Entity[][] getEntitysDeadToDraw() {
		return entitysDeadToDraw;
	}
	
	public Object[][] getObjectsToDraw() {
		return objectsToDraw;
	}
	
}
