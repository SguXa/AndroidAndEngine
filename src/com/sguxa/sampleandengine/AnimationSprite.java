package com.sguxa.sampleandengine;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.LoopEntityModifier.ILoopEntityModifierListener;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.RotationByModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.RepeatingSpriteBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.AssetBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.LoopModifier;
import org.andengine.util.modifier.ease.EaseSineInOut;

import android.opengl.GLES20;
import android.widget.Toast;

public class AnimationSprite extends SimpleBaseGameActivity {
	
	private BuildableBitmapTextureAtlas texCat;
	private BitmapTextureAtlas mPlayer;
	private BitmapTextureAtlas mLenght;
	private TiledTextureRegion regPlayer;
	private AnimatedSprite  sprPlayer;
	private Scene m_Scene;
	private Camera    m_Camera;

	//This represents the sprite sheet(image) rows and columns
	//We have 4 Rows and 2 Columns 
	private static int   SPR_COLUMN  = 1;
	private static int   SPR_ROWS  = 5;
	private RepeatingSpriteBackground bBackGrouand;

	//Set the camera Width and Height 
	private static final int CAMERA_WIDTH = 720;
	private static final int CAMERA_HEIGHT = 480;

	//Override the below method from base activity class 

	@Override
	public EngineOptions onCreateEngineOptions() 
	{
	 m_Camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
	 EngineOptions en = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(
	 CAMERA_WIDTH, CAMERA_HEIGHT), m_Camera);
	 return en;
	}

	//Override the below method from base activity class 

	@Override
	protected void onCreateResources() 
	{
		 
		 this.bBackGrouand= new RepeatingSpriteBackground (CAMERA_WIDTH,CAMERA_HEIGHT,this.getTextureManager(),AssetBitmapTextureAtlasSource.create(this.getAssets(), "gfx/background_grass.png"),this.getVertexBufferObjectManager());
		 this.mPlayer= new BitmapTextureAtlas(this.getTextureManager(),128,128);
		 this.mLenght=new BitmapTextureAtlas(this.getTextureManager(),128,128,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		 this.regPlayer= BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mPlayer, this, "gfx/player.png", 0, 0,3,4);
		 this.mPlayer.load();
	}


	//Override the below method from base activity class 

	@Override
	protected Scene onCreateScene() 
	{
	 m_Scene = new Scene();
	 m_Scene.setBackground(this.bBackGrouand);
//	 m_Scene.setBackground(new Background(Color.TRANSPARENT));
//	 mLenght.
	   
	 sprPlayer = new AnimatedSprite(10, 10, regPlayer, this.getVertexBufferObjectManager());
	 final Path path = new Path(5).to(10, 10).to(10, CAMERA_HEIGHT - 74).to(CAMERA_WIDTH - 58, CAMERA_HEIGHT - 74).to(CAMERA_WIDTH - 58, 10).to(10, 10);
//	 m_Scene.attachChild(sprCat);
	 
	 sprPlayer.registerEntityModifier(new LoopEntityModifier(new PathModifier(30, path, null, new IPathModifierListener() {
			@Override
			public void onPathStarted(final PathModifier pPathModifier, final IEntity pEntity) {
				Debug.d("onPathStarted");
			}

			@Override
			public void onPathWaypointStarted(final PathModifier pPathModifier, final IEntity pEntity, final int pWaypointIndex) {
				Debug.d("onPathWaypointStarted:  " + pWaypointIndex);
				switch(pWaypointIndex) {
					case 0:
						 sprPlayer.animate(new long[]{200, 200, 200}, 6, 8, true);
						break;
					case 1:
						 sprPlayer.animate(new long[]{200, 200, 200}, 3, 5, true);
						break;
					case 2:
						 sprPlayer.animate(new long[]{200, 200, 200}, 0, 2, true);
						break;
					case 3:
						 sprPlayer.animate(new long[]{200, 200, 200}, 9, 11, true);
						break;
				}
			}

			@Override
			public void onPathWaypointFinished(final PathModifier pPathModifier, final IEntity pEntity, final int pWaypointIndex) {
				Debug.d("onPathWaypointFinished: " + pWaypointIndex);
			}

			@Override
			public void onPathFinished(final PathModifier pPathModifier, final IEntity pEntity) {
				Debug.d("onPathFinished");
			}
		}, EaseSineInOut.getInstance())));
	 
	 



//	 sprCat.registerEntityModifier(entityModifier.deepCopy());

	 m_Scene.attachChild(sprPlayer);
//			scene.attachChild(rect);
	 return m_Scene;
	 //Proverka git
	 
	}
	  
//	 @Override
//	 public boolean onCreateOptionsMenu(Menu menu) {
//	  // Inflate the menu; this adds items to the action bar if it is present.
//	  getMenuInflater().inflate(R.menu.class, menu);
//	  return true;
//	 }
	 }
