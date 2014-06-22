package com.spantons.stagesLevel;

import com.spantons.tileMap.TileMap;

public class TransformTransparentWallsLv1Stage1 implements ITransformTransparentWalls{

	private TileMap tileMap;
	
	/****************************************************************************************/
	public TransformTransparentWallsLv1Stage1(TileMap _tileMap) {
		tileMap = _tileMap;		
	}
	
	/****************************************************************************************/
	@Override
	public void transformToTransparentWalls() {
		tileMap.transformToTransparentWalls();
	}

	/****************************************************************************************/
	@Override
	public void transformToOriginalWalls() {
		tileMap.resetWalls();
	}

}
