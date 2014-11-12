package com.sguxa.sampleandengine;

import java.util.Random;
//импорт свернут для удобства
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
	private static final int CAMERA_WIDTH = 480;  // ширина экрана
    private static final int CAMERA_HEIGHT = 320; // высота экрана
     
    // ===========================================================
    // Fields
    // ===========================================================
    private Camera sapCamera;     // моя камера
    private ITexture mTexture;     // мой файл с текстурой
    private ITextureRegion mFaceTextureRegion;  // определенный прямоугольный кусок моей текстуры
 
 // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================
     
    //@Override
 // инициализируем движок
    public EngineOptions onCreateEngineOptions()
    {
      // инициализируем камеру
      sapCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
      // инициализируем движок. Его параметры:
      // Первый (true) - полноэкранный режим
      // Второй - ландшафтная (горизонтальная) ориентация экрана.
      // Третий FillResolutionPolicy() - отличная краткая статья здесь http://flexymind.com/blog/?p=253
      // Четвертый - наша камера
         EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, 
         new FillResolutionPolicy(), sapCamera);
         return engineOptions;
    }
 
    // загружаем ресурсы. Картинки\звуки\музыку и т.п.
    @Override
    protected void onCreateResources()
    {
     
     // без конструкции try-catch почему-то не работает, либо я не смог пока правильно сделать (23/09/12)
     // И вообще я с потоками пока что на "вы" - потому пока непросто разобраться.
      
     try {
   this.mTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
    //@Override
    public InputStream open() throws IOException {
     return getAssets().open("gfx/ship.png");
    }
   });
 
  // загрузка текстуры из файла 
   this.mTexture.load();
  // выделение куска текстуры (региона) для нас. в данном случа вся текстура идет в регион  
   this.mFaceTextureRegion = TextureRegionFactory.extractFromTexture(this.mTexture); 
  } 
     // Если текстура не загружена, в дебаг-лист попалет сообщение, содержащее номер ошибки ввода-вывода
     catch (IOException e) {
   Debug.e(e);
  }         
    }
 
    // создаем сцену
    @Override
    protected Scene onCreateScene()
    {
     Scene scene = new Scene();
        // заливаем бэкграунд одним цветом
     scene.setBackground(new Background(0.9804f, 0.0f, 0.5f));
     // создаем спрайт и пихаем его в сцену
        final Sprite face = new Sprite(100, 100, this.mFaceTextureRegion, this.getVertexBufferObjectManager() );
        scene.attachChild(face);
        return scene;
    }
}