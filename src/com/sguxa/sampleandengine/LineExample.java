package com.sguxa.sampleandengine;

import java.util.Random;
//������ ������� ��� ��������
import java.io.IOException;
import java.io.InputStream;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.debug.Debug;
 

/**
 SguXa
 */
public class LineExample extends SimpleBaseGameActivity {
	// ===========================================================
    // Constants
    // ===========================================================
	private static final int CAMERA_WIDTH = 720;  // ������ ������
    private static final int CAMERA_HEIGHT = 480; // ������ ������
     
    // ===========================================================
    // Fields
    // ===========================================================
    private ITexture mTexture;     // ��� ���� � ���������
    private ITextureRegion mFaceTextureRegion;  // ������������ ������������� ����� ���� ��������
    public SpriteChel Chel;
    private BitmapTextureAtlas mFontTexture;
    private BitmapTextureAtlas mBitmapTextureAtlas;
    private TextureRegion chelTexture;  
 // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================
     
    //@Override
 // �������������� ������
    public EngineOptions onCreateEngineOptions()
    {
      // �������������� ������
      final Camera camera=new Camera(0,0,CAMERA_WIDTH,CAMERA_HEIGHT);
      // �������������� ������. ��� ���������:
      // ������ (true) - ������������� �����
      // ������ - ����������� (��������������) ���������� ������.
      // ������ FillResolutionPolicy() - �������� ������� ������ ����� http://flexymind.com/blog/?p=253
      // ��������� - ���� ������
         
         return new EngineOptions(true,ScreenOrientation.LANDSCAPE_SENSOR,new RatioResolutionPolicy(CAMERA_WIDTH,CAMERA_HEIGHT),camera);
    }
 
    // ��������� �������. ��������\�����\������ � �.�.
    @Override
    protected void onCreateResources()
    {
    	
//    	this.mBitmapTextureAtlas= new BitmapTextureAtlas(null, 256,256,null, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
    	try{
    		this.mTexture=new BitmapTexture(this.getTextureManager(), new IInputStreamOpener(){
    			
    			public InputStream open() throws IOException{
    				return getAssets().open("gfx/GTA2_PED_130.bmp");
    			}
    		});
    	
    	this.mTexture.load();
    	this.mFaceTextureRegion=TextureRegionFactory.extractFromTexture(this.mTexture);
//     try {
//   this.mTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
//    //@Override
//    public InputStream open() throws IOException {
//     return getAssets().open("gfx/ship.png");
//    }
//   });
// 
//  // �������� �������� �� ����� 
//   this.mTexture.load();
//  // ��������� ����� �������� (�������) ��� ���. � ������ ����� ��� �������� ���� � ������  
//   this.mFaceTextureRegion = TextureRegionFactory.extractFromTexture(this.mTexture); 
  } 
     // ���� �������� �� ���������, � �����-���� ������� ���������, ���������� ����� ������ �����-������
     catch (IOException e) {
   Debug.e(e);
  }         
    }
 
    // ������� �����
    @Override
    protected Scene onCreateScene()
    {
    	this.mEngine.registerUpdateHandler(new FPSLogger());
    	final Scene scene=new Scene();
    	scene.setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
    	
    	final float centerX=(CAMERA_WIDTH-this.mFaceTextureRegion.getWidth()) /2;
    	final float centerY=(CAMERA_HEIGHT-this.mFaceTextureRegion.getWidth()) /2;
    	
    	final Sprite face = new Sprite(centerX, centerY, this.mFaceTextureRegion, this.getVertexBufferObjectManager());
		scene.attachChild(face);
    	
    	return scene;
//    	Chel = new SpriteChel(CAMERA_WIDTH / 2, CAMERA_HEIGHT - 200, this.monsterTexture);
    }
//     Scene scene = new Scene();
//        // �������� ��������� ����� ������
//     scene.setBackground(new Background(0.9804f, 0.0f, 0.5f));
//     // ������� ������ � ������ ��� � �����
//        final Sprite face = new Sprite(100, 100, this.mFaceTextureRegion, this.getVertexBufferObjectManager() );
//        scene.attachChild(face);
//        return scene;
//    }
    
}