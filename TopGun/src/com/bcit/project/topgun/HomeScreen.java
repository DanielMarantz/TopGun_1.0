package com.bcit.project.topgun;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;

public class HomeScreen extends SimpleBaseGameActivity
{
	
	private static final int CAMERA_WIDTH = 720;
	private static final int CAMERA_HEIGHT = 1280;
	private boolean helpMenu = false;
	
	private BitmapTextureAtlas mBitmapTextureAtlas;
	private BitmapTextureAtlas mAutoParallaxBackgroundAtlas;

	private ITextureRegion hudTextureRegion;
	private ITextureRegion roomTextureRegion;
	private ITextureRegion mPlayerTextureRegion;
	private ITextureRegion helpTextureRegion;
	private ITextureRegion computerTextureRegion;
	private ITextureRegion reportcardTextureRegion;
	
	private final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
	
	
	@Override
	public EngineOptions onCreateEngineOptions()
	{
		return new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
	}

	@Override
	protected void onCreateResources()
	{
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
		/* Create front */
		this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 1024, 1024, TextureOptions.NEAREST);
		this.hudTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "hud.png", 0, 0);
		this.helpTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "help_button.png", 8, 7);
		this.computerTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "computer.png", 400, 600);
		this.reportcardTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "folder.png", 400, 400);
		this.mBitmapTextureAtlas.load();
		
		/* Create background and HUD */
		this.mAutoParallaxBackgroundAtlas = new BitmapTextureAtlas(this.getTextureManager(), 1024, 2048, TextureOptions.BILINEAR);
		this.roomTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundAtlas, this, "room.png", 0, 0);
		this.mAutoParallaxBackgroundAtlas.load();

	}

	@Override
	protected Scene onCreateScene()
	{
		this.mEngine.registerUpdateHandler(new FPSLogger());
		final Scene scene = new Scene();
		final VertexBufferObjectManager vertexBufferObjectManager = this.getVertexBufferObjectManager();
		
		/* Instantiate background and HUD */
		final AutoParallaxBackground autoParallaxBackground = new AutoParallaxBackground(0, 0, 0, 5);
		autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(0.0f, new Sprite(CAMERA_WIDTH - this.roomTextureRegion.getWidth(), 0, this.roomTextureRegion, vertexBufferObjectManager)));
		scene.setBackground(autoParallaxBackground);
				
		/* 
		Calculate the coordinates for the player, so its centered on the camera.
		final float playerX = (CAMERA_WIDTH - this.mPlayerTextureRegion.getWidth()) / 2;
		final float playerY = CAMERA_HEIGHT - this.mPlayerTextureRegion.getHeight() - 5; 
		*/
		
		final Sprite hudSprite = new Sprite(0, 0, this.hudTextureRegion, vertexBufferObjectManager);
		
		final Sprite computer = new Sprite(420, 580, this.computerTextureRegion, vertexBufferObjectManager) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if(pSceneTouchEvent.isActionDown())
				{
					openComputer(scene);
				}
				return true;
			}
		};
		computer.setScale((float)1.2);
		
		final Sprite reportCard = new Sprite(450, 420, this.reportcardTextureRegion, vertexBufferObjectManager) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if(pSceneTouchEvent.isActionDown())
				{
					openReportCard(scene);
				}
				return true;
			}
		};
		reportCard.setScale((float)1.4);
		
		final Sprite helpButton = new Sprite(8, 7, this.helpTextureRegion, vertexBufferObjectManager) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if(pSceneTouchEvent.isActionDown())
				{
					helpMenu = !helpMenu;
					if(helpMenu)
					{
						openHelpMenu(scene);
					}
				}
				return true;
			}
		};
		
		scene.attachChild(hudSprite);
		scene.attachChild(helpButton);
		scene.attachChild(computer);
		scene.attachChild(reportCard);
		
		return scene;
	}
	
	protected void openReportCard(Scene scene)
	{
		
	}

	protected void openComputer(Scene scene)
	{
		
	}

	protected void openHelpMenu(Scene scene)
	{
		
	}

}
