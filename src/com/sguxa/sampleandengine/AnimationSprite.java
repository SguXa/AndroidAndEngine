package com.sguxa.sampleandengine;

import org.andengine.engine.camera.Camera; 
import org.andengine.engine.options.EngineOptions; 
import org.andengine.engine.options.ScreenOrientation; 
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy; 
import org.andengine.entity.scene.Scene; 
import org.andengine.entity.scene.background.Background; 
import org.andengine.entity.sprite.AnimatedSprite; 
import org.andengine.opengl.texture.TextureOptions; 
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas; 
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory; 
import org.andengine.opengl.texture.region.TiledTextureRegion; 
import org.andengine.ui.activity.SimpleBaseGameActivity; 
import org.andengine.util.color.Color;

import android.view.Menu;
import android.R;

public class AnimationSprite extends SimpleBaseGameActivity {
	
	 private BitmapTextureAtlas texCat;
	 private TiledTextureRegion regCat;
	 private AnimatedSprite  sprCat;
	 private Scene m_Scene;
	 private Camera    m_Camera;
//	 private static int   SPR_COLUMN  = 2;
	 private static int   SPR_ROWS  = 5;
	  
	 private static final int CAMERA_WIDTH = 480;
	 private static final int CAMERA_HEIGHT = 320;
	  
	 @Override
	 public EngineOptions onCreateEngineOptions() 
	 {
	  m_Camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
	  EngineOptions en = new EngineOptions(true,ScreenOrientation.LANDSCAPE_SENSOR,new RatioResolutionPolicy(CAMERA_WIDTH,CAMERA_HEIGHT),m_Camera);
	  return en;
	 }
	  
	 @Override
	 protected void onCreateResources() 
	 {
	  texCat = new BitmapTextureAtlas(this.getTextureManager(), 10, 18, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	  regCat = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(texCat, this,"gfx/chel_run.png", 0, 0, 1, SPR_ROWS);
	  texCat.load();
	 }

	 @Override
	 protected Scene onCreateScene() 
	 {
	  m_Scene = new Scene();
	  m_Scene.setBackground(new Background(Color.WHITE));
	  
	  
	    
	  sprCat = new AnimatedSprite(0, 0, regCat, this.getVertexBufferObjectManager());
	  m_Scene.attachChild(sprCat);
	    
	  sprCat.animate(100);
	  return m_Scene;
	 }
	  
//	 @Override
//	 public boolean onCreateOptionsMenu(Menu menu) {
//	  // Inflate the menu; this adds items to the action bar if it is present.
//	  getMenuInflater().inflate(R.menu.class, menu);
//	  return true;
//	 }
	 }
