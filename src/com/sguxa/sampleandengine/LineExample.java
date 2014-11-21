package com.sguxa.sampleandengine;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.shape.IAreaShape;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.input.sensor.acceleration.AccelerationData;
import org.andengine.input.sensor.acceleration.IAccelerationListener;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;
 
import android.content.res.Resources;
import android.hardware.SensorManager;
 
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
 
public class LineExample extends SimpleBaseGameActivity implements
  IAccelerationListener {
 
 private static int CAMERA_WIDTH;
 private static int CAMERA_HEIGHT;
 private static final int MAX_STEPS_PER_UPDATE = 60;
 private static final int PHYSICS_STEPS_PER_SECOND = 60;
 private static final int PHYSICS_VELOCITY_ITERATIONS = 5;
 private static final int PHYSICS_POSITION_ITERATIONS = 5;
 private TextureRegion mShip;
 private Scene mScene;
 private PhysicsWorld mPhysicsWorld;
 private final FixtureDef FIXTURE_DEF = PhysicsFactory.createFixtureDef(0,
   0.5f, 0.5f);
 
 /**
  * Определяем размеры экрана, создаём камеру и определяем опции движка
  */
 @Override
 public EngineOptions onCreateEngineOptions() {
  Resources res = getResources();
  CAMERA_HEIGHT = res.getDisplayMetrics().heightPixels;
  CAMERA_WIDTH = res.getDisplayMetrics().widthPixels;
  Camera mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
  return new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED,
    new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
 }
 
 /**
  * Загружаем ресурсы
  */
 @Override
 protected void onCreateResources() {
  BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
  Engine engine = getEngine();
  TextureManager tm = engine.getTextureManager();
  BitmapTextureAtlas mTexture = new BitmapTextureAtlas(tm, 1024, 768,
    TextureOptions.NEAREST_PREMULTIPLYALPHA);
  mShip = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
    mTexture, this, "speedship.png", 0, 0);
  tm.loadTexture(mTexture);
 }
 
 /**
  * Создаём сцену, инициализируем физику и выводим на сцену
  * "действующее лицо"
  */
 @Override
 protected Scene onCreateScene() {
  mScene = new Scene();
  mScene.setBackground(new Background(0.09804f, 0.7274f, 0.8f));
  mPhysicsWorld = createPhysicBox(mScene);
  Sprite sprite = new Sprite(CAMERA_WIDTH / 2 - mShip.getWidth() / 2,
    CAMERA_HEIGHT - mShip.getHeight(), mShip,
    getVertexBufferObjectManager());
  mScene.attachChild(sprite);
  Body body = PhysicsFactory.createBoxBody(mPhysicsWorld, sprite,
    BodyType.DynamicBody, FIXTURE_DEF);
  mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(sprite,
    body, true, true));
  return mScene;
 }
 
 /**
  * Инициализация физики в игре. Также устанавливаем статические объекты -
  * "стены"
  * 
  * @param mScene
  * @return PhysicsWorld
  */
 private PhysicsWorld createPhysicBox(Scene mScene) {
  PhysicsWorld mPhysicsWorld = new FixedStepPhysicsWorld(
    PHYSICS_STEPS_PER_SECOND, MAX_STEPS_PER_UPDATE, new Vector2(0,
      SensorManager.GRAVITY_EARTH), false,
    PHYSICS_VELOCITY_ITERATIONS, PHYSICS_POSITION_ITERATIONS);
  VertexBufferObjectManager vbo = this.getVertexBufferObjectManager();
 
  final IAreaShape ground = new Rectangle(0, CAMERA_HEIGHT - 2,
    CAMERA_WIDTH, 2, vbo);
  final IAreaShape roof = new Rectangle(0, 0, CAMERA_WIDTH, 2, vbo);
  final IAreaShape left = new Rectangle(0, 0, 2, CAMERA_HEIGHT, vbo);
  final IAreaShape right = new Rectangle(CAMERA_WIDTH - 2, 0, 2,
    CAMERA_HEIGHT, vbo);
 
  final IAreaShape br1 = new Rectangle(0, CAMERA_HEIGHT / 3, CAMERA_WIDTH
    - CAMERA_WIDTH / 3, 2, vbo);
  final IAreaShape br2 = new Rectangle(CAMERA_WIDTH - CAMERA_WIDTH / 3,
    2 * CAMERA_HEIGHT / 3, 4 * CAMERA_WIDTH - CAMERA_WIDTH / 3, 2,
    vbo);
 
  PhysicsFactory.createBoxBody(mPhysicsWorld, ground,
    BodyType.StaticBody, FIXTURE_DEF);
  PhysicsFactory.createBoxBody(mPhysicsWorld, roof, BodyType.StaticBody,
    FIXTURE_DEF);
  PhysicsFactory.createBoxBody(mPhysicsWorld, left, BodyType.StaticBody,
    FIXTURE_DEF);
  PhysicsFactory.createBoxBody(mPhysicsWorld, right, BodyType.StaticBody,
    FIXTURE_DEF);
 
  PhysicsFactory.createBoxBody(mPhysicsWorld, br1, BodyType.StaticBody,
    FIXTURE_DEF);
  PhysicsFactory.createBoxBody(mPhysicsWorld, br2, BodyType.StaticBody,
    FIXTURE_DEF);
 
  mScene.attachChild(ground);
  mScene.attachChild(roof);
  mScene.attachChild(left);
  mScene.attachChild(right);
 
  mScene.attachChild(br1);
  mScene.attachChild(br2);
 
  mScene.registerUpdateHandler(mPhysicsWorld);
  return mPhysicsWorld;
 }
 
 /**
  * Регистрируем слушатель акселерометра
  */
 @Override
 protected void onResume() {
  super.onResume();
  this.enableAccelerationSensor(this);
 }
 
 /**
  * Удаляем слушатель акселерометра
  */
 @Override
 protected void onPause() {
  this.disableAccelerationSensor();
  super.onPause();
 }
 
 @Override
 public void onAccelerationAccuracyChanged(AccelerationData pAccelerationData) {
 }
 
 /**
  * При изменении вектора гравитации меняем его в виртуальном мире
  */
 @Override
 public void onAccelerationChanged(AccelerationData pData) {
  if (mPhysicsWorld != null) {
   final Vector2 gravity = Vector2Pool.obtain(pData.getX(),
     pData.getY());
   mPhysicsWorld.setGravity(gravity);
   Vector2Pool.recycle(gravity);
  }
 }
}