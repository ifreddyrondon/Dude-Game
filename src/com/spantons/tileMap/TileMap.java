package com.spantons.tileMap;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import utilities.ArraysUtil;
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
	private String src;
	private String delimsChar;
	private String line;
	private String[] tokens;
	private int[][] map;
	private int[][] walls;
	private int[][] wallsRestore;
	private int[][] objects;
	private String transparentWalls;
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
	private Tile[] tiles;
	private Entity[][] entitysDeadToDraw;
	private Entity[][] entitysToDraw;
	private Object[][] objectsToDraw;

	/****************************************************************************************/
	public TileMap(String _src) {
		src = _src;
		loadMap();

		if (GamePanel.RESOLUTION_WIDTH % tileSize.x == 0
				&& GamePanel.RESOLUTION_HEIGHT % tileSize.y == 0) {
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
	private void loadMap() {

		try {
			InputStream in = getClass().getResourceAsStream(src);
			BufferedReader br = 
				new BufferedReader(new InputStreamReader(in));

			delimsChar = "=";
			
			br.readLine();
			tokens = br.readLine().split(delimsChar);
			numColMap = Integer.parseInt(tokens[1]);
			tokens = br.readLine().split(delimsChar);
			numRowsMap = Integer.parseInt(tokens[1]);
			
			tokens = br.readLine().split(delimsChar);
			int tileWidth = Integer.parseInt(tokens[1]);
			tokens = br.readLine().split(delimsChar);
			int tileHeight = Integer.parseInt(tokens[1]);
			tileSize = new Point(tileWidth,tileHeight);
			
			br.readLine();
			br.readLine();
			
			ArrayList<String> stringTileSet = new ArrayList<String>();
			line = br.readLine();
			
			while (!line.equals("")) {
				 stringTileSet.add(line);
				line = br.readLine();
			}
			
			for (int i = 0; i < stringTileSet.size(); i++) {
				line = stringTileSet.get(i);
				tokens = line.split("=");
				delimsChar = ",";
				tokens = tokens[1].split(delimsChar);
				line = tokens[0];
				int tileWidthSet = Integer.parseInt(tokens[1]);
				int tileHeightSet = Integer.parseInt(tokens[2]);
				int xDrawingOffSet = Integer.parseInt(tokens[3]);
				int yDrawingOffSet = Integer.parseInt(tokens[4]);
				if (tiles == null) 
					tiles = TileSet.getTileSet(line, tileWidthSet, tileHeightSet, xDrawingOffSet, yDrawingOffSet);
				else 
					tiles = ArraysUtil.concat(tiles, TileSet.getTileSet(line, tileWidthSet, tileHeightSet, xDrawingOffSet, yDrawingOffSet));
			}
				
			map = new int[numRowsMap][numColMap];
			walls = new int[numRowsMap][numColMap];
			objects = new int[numRowsMap][numColMap];
			
			entitysToDraw = new Entity[numRowsMap][numColMap];
			entitysDeadToDraw = new Entity[numRowsMap][numColMap];
			objectsToDraw = new Object[numRowsMap][numColMap];

			br.readLine();
			br.readLine();
			br.readLine();
			
			// llenamos la matriz map
			for (int row = 0; row < numRowsMap; row++) {
				line = br.readLine();
				tokens = line.split(delimsChar);
				for (int col = 0; col < numColMap; col++)
					map[row][col] = Integer.parseInt(tokens[col]);
			}
			
			br.readLine();
			br.readLine();
			br.readLine();
			br.readLine();

			for (int row = 0; row < numRowsMap; row++) {
				line = br.readLine();
				tokens = line.split(delimsChar);
				for (int col = 0; col < numColMap; col++)
					walls[col][row] = Integer.parseInt(tokens[col]);
			}
			
			wallsRestore = new int[numRowsMap][numColMap];
			for (int i = 0; i < numRowsMap; i++) {
				for (int j = 0; j < numColMap; j++) {
					wallsRestore[i][j] = walls[i][j];
				}
			}
			
			br.readLine();
			br.readLine();
			br.readLine();
			br.readLine();

			for (int row = 0; row < numRowsMap; row++) {
				line = br.readLine();
				tokens = line.split(delimsChar);
				for (int col = 0; col < numColMap; col++)
					objects[col][row] = Integer.parseInt(tokens[col]);
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
		
		xMax = -mapToAbsolute(0, numColMap - 1).x - GamePanel.RESOLUTION_WIDTH
				+ tileSize.x * 2;
		yMax = (mapToAbsolute(numRowsMap - 1, numColMap - 1).y - GamePanel.RESOLUTION_HEIGHT)
				+ tileSize.y * 3;
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
	/****************************************************************************************/
	public void setPosition(int _x, int _y) {
		x = _x;
		y = _y;
		fixBounds();
	}
	/****************************************************************************************/
	public void setPositionByCharacter(Entity _entity){
		setPosition(
			x + (_entity.getX() - RESOLUTION_WIDTH_FIX / 2)
			, y + (_entity.getY() - RESOLUTION_HEIGHT_FIX / 2));
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
		coorMapBottomRight = absoluteToMap(x + RESOLUTION_WIDTH_FIX, 
				y + RESOLUTION_HEIGHT_FIX);

		// Desplazamos cada esquina para tener el buffer con un poquito mas
		coorMapTopLeft = TileWalk.walkTo("NW", coorMapTopLeft, 2);
		coorMapTopRight = TileWalk.walkTo("NE", coorMapTopRight, 2);
		coorMapBottomLeft = TileWalk.walkTo("SW", coorMapBottomLeft, 2);
		coorMapBottomRight = TileWalk.walkTo("SE", coorMapBottomRight, 2);

		// banderas de dibujado
		boolean completed, completedRow;
		Point firstTileOfRowToDraw, finalTileOfRowToDraw, currentTile;
		int rowCounter = 0;

		completed = false;
		firstTileOfRowToDraw = coorMapTopLeft;
		finalTileOfRowToDraw = coorMapTopRight;

		while (!completed) {
			completedRow = false;
			currentTile = firstTileOfRowToDraw;

			while (!completedRow) {
				
				drawImages(g,currentTile);
				
				if (currentTile.x == finalTileOfRowToDraw.x)
					completedRow = true;
				else
					currentTile = 
					TileWalk.walkTo("E", currentTile, 1);
			}

			if (	firstTileOfRowToDraw.x > coorMapBottomLeft.x
				&& firstTileOfRowToDraw.y > coorMapBottomLeft.y
				&& finalTileOfRowToDraw.x > coorMapBottomRight.x
				&& finalTileOfRowToDraw.y > coorMapBottomRight.y)
				completed = true;

			else {

				if (rowCounter % 2 != 0) {
					firstTileOfRowToDraw = TileWalk.walkTo("SW",
							firstTileOfRowToDraw, 1);
					finalTileOfRowToDraw = TileWalk.walkTo("SE",
							finalTileOfRowToDraw, 1);

				} else {
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
	private void drawImages(Graphics2D g, Point currentTile) {
		
		if (currentTile.x >= 0 && currentTile.y >= 0
				&& currentTile.x < numColMap
				&& currentTile.y < numRowsMap) {

			Point coorAbsolute = 
				mapToAbsolute(currentTile.x,currentTile.y);

			g.drawImage(tiles[map[currentTile.y][currentTile.x] - 1]
					.getImage(), 
					(coorAbsolute.x - this.x) - tileSize.x / 2,
					(coorAbsolute.y - this.y) - tileSize.y, null);
			
			if (walls[currentTile.x][currentTile.y] != 0) {
				
				if (!transparentWalls.equals("")) {
					if (transparentWalls.equals("bathroom")) {
						for (int i = 3; i < 10; i++) 
							walls[i][8] = 37;
						for (int i = 4; i < 8; i++) 
							walls[11][i] = 36;
						walls[11][3] = 33;
					}
				}
				else {
					for (int i = 0; i < numRowsMap; i++) {
						for (int j = 0; j < numColMap; j++) {
							walls[i][j] = wallsRestore[i][j];
						}
					}
				}
				
				g.drawImage(tiles[walls[currentTile.x][currentTile.y] - 1]
						.getImage(), 
						(coorAbsolute.x - this.x) - tileSize.x / 2,
						(coorAbsolute.y - this.y) - 192, null);
			}
			
			if (objects[currentTile.x][currentTile.y] != 0) {
				g.drawImage(tiles[objects[currentTile.x][currentTile.y] - 1]
						.getImage(), 
						(coorAbsolute.x - this.x) - tileSize.x / 2,
						(coorAbsolute.y - this.y) - 192, null);
			}
			
			Object object = objectsToDraw[currentTile.x][currentTile.y];
			Entity entity = entitysToDraw[currentTile.x][currentTile.y];
			Entity entityDead = entitysDeadToDraw[currentTile.x][currentTile.y];
			
			if (entityDead != null) 
				entityDead.draw(g);
			if (object != null) 
				object.draw(g);
			if (entity != null) 
				entity.draw(g);
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
	public int getWallPosition(int a, int b) {
		return walls[a][b];
	}
	public int getObjectsPosition(int a, int b) {
		return objects[a][b];
	}
	public String getTransparentWalls() {
		return transparentWalls;
	}
	public void setTransparentWalls(String transparentWalls) {
		this.transparentWalls = transparentWalls;
	}
	
}