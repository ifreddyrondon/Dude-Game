package com.spantons.gameState;

import com.spantons.gameState.interfaces.ITransformTransparentWalls;
import com.spantons.object.Door;
import com.spantons.tileMap.TileMap;

public class TransformTransparentWallsLv1Stage1 implements ITransformTransparentWalls{

	private TileMap tileMap;
	private Door doorOfWall;
	
	/****************************************************************************************/
	public TransformTransparentWallsLv1Stage1(TileMap _tileMap, Door _doorOfWall) {
		tileMap = _tileMap;
		doorOfWall = _doorOfWall;
	}
	
	/****************************************************************************************/
	@Override
	public void transformToTransparentWalls() {
		tileMap.setTransparentWalls("bathroom");
		doorOfWall.setStatusOpen(Door.OPEN);
	}

	/****************************************************************************************/
	@Override
	public void transformToOriginalWalls() {
		tileMap.setTransparentWalls("");
		doorOfWall.setStatusOpen(Door.CLOSE);
	}

}
