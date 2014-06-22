package com.spantons.stagesLevel;


public class TransformTransparentWallsLv1Stage1 implements ITransformTransparentWalls{

	private StagesLevel stage;
	
	/****************************************************************************************/
	public TransformTransparentWallsLv1Stage1(StagesLevel _stage) {
		stage = _stage;		
		stage.tileMap.setWallsToTransformIntoTransparent(stage.wallsToTransform);
	}
	
	/****************************************************************************************/
	@Override
	public void transformToTransparentWalls() {
		stage.tileMap.transformToTransparentWalls();
	}

	/****************************************************************************************/
	@Override
	public void transformToOriginalWalls() {
		stage.tileMap.resetWalls();
	}

}
