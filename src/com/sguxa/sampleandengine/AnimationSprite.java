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
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.RotationByModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.LoopModifier;

import android.opengl.GLES20;
import android.widget.Toast;

public class AnimationSprite extends SimpleBaseGameActivity {
	
	private BuildableBitmapTextureAtlas texCat;
	private TiledTextureRegion regCat;
	private AnimatedSprite  sprCat;
	private Scene m_Scene;
	private Camera    m_Camera;

	//This represents the sprite sheet(image) rows and columns
	//We have 4 Rows and 2 Columns 
	private static int   SPR_COLUMN  = 1;
	private static int   SPR_ROWS  = 5;


	//Set the camera Width and Height 
	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 320;

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
		 texCat = new BuildableBitmapTextureAtlas(this.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		 regCat = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(texCat, this,"gfx/chel_run.png", SPR_COLUMN, SPR_ROWS);
		 try{
			 texCat.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
			 texCat.load();
		 }catch(TextureAtlasBuilderException e){
			 Debug.e(e);
		 }
	}


	//Override the below method from base activity class 

	@Override
	protected Scene onCreateScene() 
	{
	 m_Scene = new Scene();
	 m_Scene.setBackground(new Background(Color.BLACK));
	   
	 sprCat = new AnimatedSprite(240, 150, regCat, this.getVertexBufferObjectManager());
//	 m_Scene.attachChild(sprCat);
	 sprCat.animate(100);
	 
	 final LoopEntityModifier entityModifier =
			 //LoopEntityModifier Ч зацикливает выполнение модификаторов; сылка по модификаторам http://flexymind.com/andengine-animation.html
				new LoopEntityModifier(
						//SequenceEntityModifier Ч выполн€ет модификаторы последовательно, в пор€дке их добавлени€;
						new SequenceEntityModifier(
								new RotationModifier(3, 0, 360)//модификатор, позвол€ющий повернуть объект
//								new AlphaModifier(2, 1, 0)//модификатор, предназначенный дл€ изменени€ альфа-канала объекта 
//								new AlphaModifier(1, 0, 1)
//								new ScaleModifier(2, 1, 0.5f),//позвол€ет увеличивать/уменьшать объекты
//								new DelayModifier(0.5f)//модификатор задержки. ќн необходим дл€ вставки паузы между модификаторами.
//ParallelEntityModifier Ч выполн€ет несколько модификаторов одновременно;/
//								new ParallelEntityModifier(
//										new ScaleModifier(3, 0.5f, 5),
//										new RotationByModifier(3, 90)
//								)
//								new ParallelEntityModifier(
//										new ScaleModifier(3, 5, 1),
//										new RotationModifier(3, 180, 0)
//								)
						)
				);

	 sprCat.registerEntityModifier(entityModifier);
//	 sprCat.registerEntityModifier(entityModifier.deepCopy());

	 m_Scene.attachChild(sprCat);
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
