package com.spantons.tileMap;

import java.awt.image.BufferedImage;

import com.spantons.singleton.ImageCache;

public class TileSet {

	static Tile[] getTileSet(String src, int _tileWidthSize, int _tileHeightSize, int _xDrawingOffSet, int _yDrawingOffSet) {
		try {
			
			int tileWidthSize = _tileWidthSize;
			int tileHeightSize = _tileHeightSize;
			int xDrawingOffSet = _xDrawingOffSet;
			int yDrawingOffSet = _yDrawingOffSet;
			
			BufferedImage tileSet = ImageCache.getInstance().getImage(src);

			int numRowsTileSet = tileSet.getHeight()
					/ tileHeightSize;
			int numColTileSet = tileSet.getWidth()
					/ tileWidthSize;
			
			// le damos memoria al vector que almacena los tiles
			Tile tiles[] = new Tile[numRowsTileSet * numColTileSet];

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
			
			return tiles;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
