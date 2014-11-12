package com.sguxa.sampleandengine;

import java.util.Random;
//������ ������� ��� ��������
import java.io.IOException;
import java.io.InputStream;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.debug.Debug;
 

/**
 * (c) 2010 Nicolas Gramlich 
 * (c) 2011 Zynga Inc.
 * 
 * @author Nicolas Gramlich
 * @since 11:54:51 - 03.04.2010
 */
public class LineExample extends SimpleBaseGameActivity {
	// ===========================================================
    // Constants
    // ===========================================================
	private static final int CAMERA_WIDTH = 480;  // ������ ������
    private static final int CAMERA_HEIGHT = 320; // ������ ������
     
    // ===========================================================
    // Fields
    // ===========================================================
    private Camera sapCamera;     // ��� ������
    private ITexture mTexture;     // ��� ���� � ���������
    private ITextureRegion mFaceTextureRegion;  // ������������ ������������� ����� ���� ��������
 
 // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================
     
    //@Override
 // �������������� ������
    public EngineOptions onCreateEngineOptions()
    {
      // �������������� ������
      sapCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
      // �������������� ������. ��� ���������:
      // ������ (true) - ������������� �����
      // ������ - ����������� (��������������) ���������� ������.
      // ������ FillResolutionPolicy() - �������� ������� ������ ����� http://flexymind.com/blog/?p=253
      // ��������� - ���� ������
         EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, 
         new FillResolutionPolicy(), sapCamera);
         return engineOptions;
    }
 
    // ��������� �������. ��������\�����\������ � �.�.
    @Override
    protected void onCreateResources()
    {
     
     // ��� ����������� try-catch ������-�� �� ��������, ���� � �� ���� ���� ��������� ������� (23/09/12)
     // � ������ � � �������� ���� ��� �� "��" - ������ ���� �������� �����������.
      
     try {
   this.mTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
    //@Override
    public InputStream open() throws IOException {
     return getAssets().open("gfx/ship.png");
    }
   });
 
  // �������� �������� �� ����� 
   this.mTexture.load();
  // ��������� ����� �������� (�������) ��� ���. � ������ ����� ��� �������� ���� � ������  
   this.mFaceTextureRegion = TextureRegionFactory.extractFromTexture(this.mTexture); 
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
     Scene scene = new Scene();
        // �������� ��������� ����� ������
     scene.setBackground(new Background(0.9804f, 0.0f, 0.5f));
     // ������� ������ � ������ ��� � �����
        final Sprite face = new Sprite(100, 100, this.mFaceTextureRegion, this.getVertexBufferObjectManager() );
        scene.attachChild(face);
        return scene;
    }
}