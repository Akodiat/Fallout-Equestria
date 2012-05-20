package screenCore;

import graphics.Color;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import animation.AnimationPlayer;


import math.Vector2;
import misc.AnimationFromCharacterHelper;
import misc.PlayerCharacteristics;
import GUI.controls.AnimationBox;
import GUI.controls.Button;
import GUI.controls.Label;
import GUI.controls.Panel;
import content.ContentManager;
import utils.EventArgs;
import utils.IEventListener;
import utils.time.TimeSpan;

public class PonySelector extends TransitioningGUIScreen{
	private List<PlayerCharacteristics> characters;
	private List<Panel> ponyPanelsList;
	private List<Label> ponyLabelsList;
	private List<AnimationBox> ponyBoxesList;
	private int numOfPonies;
	private int selectedPony;
	private static final File characterFolder = new File("resources\\characters");
	private File[] characterFiles;
	private ContentManager manager;

	private Button selectBtn;
	private Button deleteBtn;
	private Button createBtn;

	public PonySelector(String lookAndFeelPath) {
		super(false, TimeSpan.fromSeconds(1.0f), TimeSpan.fromSeconds(1.0f), lookAndFeelPath);
	}

	public void initialize(ContentManager manager) {
		super.initialize(manager);
		this.manager = manager;

		this.characterFiles = characterFolder.listFiles();
		this.characters = new ArrayList<PlayerCharacteristics>();

		this.numOfPonies = characterFiles.length < 3 ? characterFiles.length : 3;

		for (int i = 0; i < numOfPonies  ; i++) {
			characters.add(manager.load(characterFiles[i].getName(), PlayerCharacteristics.class));
		}

		Label myPoniesLabel = new Label();
		myPoniesLabel.setBounds(50, 50, 200, 50);
		myPoniesLabel.setText("Select a pony:");
		this.addGuiControl(myPoniesLabel, new Vector2(-200, 50), new Vector2(50, 50), new Vector2(-200, 50));

		this.ponyPanelsList = new ArrayList<Panel>();
		this.ponyLabelsList = new ArrayList<Label>();
		this.ponyBoxesList = new ArrayList<AnimationBox>();

		this.selectBtn = new Button();
		selectBtn.setBounds(-1000, -1000, 200, 50);
		selectBtn.setText("Select");
		selectBtn.setEnabled(false);
		this.addGuiControl(selectBtn, new Vector2(1366/2 -100, 768), new Vector2(1366/2 -100, 500), new Vector2(1366/2 -100, 768));

		selectBtn.addClicked(new IEventListener<EventArgs>() {
			public void onEvent(Object sender, EventArgs e) {
				setPlayerCharacteristics();
				gotoMultiplayer();
			}
		});

		this.deleteBtn = new Button();
		deleteBtn.setBounds(-1000, -1000, 200, 50);
		deleteBtn.setText("Delete");
		deleteBtn.setEnabled(false);
		this.addGuiControl(deleteBtn, new Vector2(1366/2 -100, 768), new Vector2(1366/2 -100, 570), new Vector2(1366/2 -100, 768));

		deleteBtn.addClicked(new IEventListener<EventArgs>() {
			public void onEvent(Object sender, EventArgs e) {
				delete();
			}
		});

		this.createBtn = new Button();
		createBtn.setBounds(-1000, -1000, 200, 50);
		createBtn.setText("Create new pony");
		this.addGuiControl(createBtn, new Vector2(1366/2 + 150, 768), new Vector2(1366/2 + 150, 535), new Vector2(1366/2 + 150, 768));

		createBtn.addClicked(new IEventListener<EventArgs>() {
			public void onEvent(Object sender, EventArgs e) {
				gotoCreator();
			}
		});

		Button backBtn = new Button();
		backBtn.setBounds(-1000,-1000,200,50);
		backBtn.setText("Back");
		this.addGuiControl(backBtn, new Vector2(1366/2- 350, 768), new Vector2(1366/2- 350, 535), new Vector2(1366/2- 350, 768));

		backBtn.addClicked(new IEventListener<EventArgs>() {
			@Override
			public void onEvent(Object sender, EventArgs e) {
				goBack();
			}
		});
	}

	protected void gotoMultiplayer() {
		this.ScreenManager.addScreen("Multiplayer");
	}

	protected void setPlayerCharacteristics() {
		PlayerCharacteristics pc = characters.get(selectedPony);
		this.ScreenManager.setPlayerCharacteristics(pc);
	}

	protected void delete() {
		
		this.ponyBoxesList.get(selectedPony).setVisible(false);
		this.ponyPanelsList.get(selectedPony).setVisible(false);
		this.ponyLabelsList.get(selectedPony).setVisible(false);
		numOfPonies -= 1;
		if(!characterFiles[selectedPony].delete()) {
			throw new Error();
		}
	}

	public void onClicked(AnimationPlayer player) {
		this.selectBtn.setEnabled(true);
		this.deleteBtn.setEnabled(true);
		player.startAnimation("walk");
	}

	public void disableCreateBtn() {
		this.createBtn.setEnabled(false);
	}

	public void gotoCreator() {
		this.ScreenManager.addScreen("PonyCreator");
	}

	public void goBack() {
		this.exitScreen();
	}
	
	@Override
	public void onTransitionFinished(){
		File[] files = characterFolder.listFiles();
		if(this.numOfPonies<files.length){
			if(numOfPonies == 0){
				numOfPonies++;
				characters.add(this.manager.load(files[0].getName(), PlayerCharacteristics.class));
				addPonyPanel(0);
				this.addGuiControl(ponyPanelsList.get(0), new Vector2(-250, 130), new Vector2(150, 130), new Vector2(-250, 130));
				this.addGuiControl(ponyBoxesList.get(0), new Vector2(-250, 130), new Vector2(150, 130), new Vector2(-250, 130));
				this.addGuiControl(ponyLabelsList.get(0), new Vector2(-250, 390), new Vector2(150, 390), new Vector2(-250, 390));
				this.characterFiles[0] = files[0];
			}else if(numOfPonies == 1){
				numOfPonies++;
				characters.add(this.manager.load(files[1].getName(), PlayerCharacteristics.class));
				addPonyPanel(1);
				this.addGuiControl(ponyPanelsList.get(1), new Vector2(1366/2-125, -250), new Vector2(1366/2-125, 130), new Vector2(1366/2-125, -250));
				this.addGuiControl(ponyBoxesList.get(1), new Vector2(1366/2-125, -250), new Vector2(1366/2-125, 130), new Vector2(1366/2-125, -250));
				this.addGuiControl(ponyLabelsList.get(1), new Vector2(1366/2-125, -250), new Vector2(1366/2-125, 390), new Vector2(1366/2-125, -250));
				this.characterFiles[1] = files[1];
			}else if(numOfPonies == 2){
				numOfPonies++;
				characters.add(this.manager.load(files[2].getName(), PlayerCharacteristics.class));
				addPonyPanel(2);
				this.addGuiControl(ponyPanelsList.get(2), new Vector2(1366, 130), new Vector2(966, 130), new Vector2(1366, 130));
				this.addGuiControl(ponyBoxesList.get(2), new Vector2(1366, 130), new Vector2(966, 130), new Vector2(1366, 130));
				this.addGuiControl(ponyLabelsList.get(2), new Vector2(1366, 390), new Vector2(966, 390), new Vector2(1366, 390));
				this.characterFiles[2] = files[2];
			}
		}
	}
	
	@Override
	public void onEnter() {
		for(int i = 0; i < numOfPonies; i++) {
			addPonyPanel(i);
		}
		if(numOfPonies > 0){
			this.addGuiControl(ponyPanelsList.get(0), new Vector2(-250, 130), new Vector2(150, 130), new Vector2(-250, 130));
			this.addGuiControl(ponyBoxesList.get(0), new Vector2(-250, 130), new Vector2(150, 130), new Vector2(-250, 130));
			this.addGuiControl(ponyLabelsList.get(0), new Vector2(-250, 390), new Vector2(150, 390), new Vector2(-250, 390));
		}
		if(numOfPonies > 1){
			this.addGuiControl(ponyPanelsList.get(1), new Vector2(1366/2-125, -250), new Vector2(1366/2-125, 130), new Vector2(1366/2-125, -250));
			this.addGuiControl(ponyBoxesList.get(1), new Vector2(1366/2-125, -250), new Vector2(1366/2-125, 130), new Vector2(1366/2-125, -250));
			this.addGuiControl(ponyLabelsList.get(1), new Vector2(1366/2-125, -250), new Vector2(1366/2-125, 390), new Vector2(1366/2-125, -250));
		}
		if(numOfPonies > 2){
			this.addGuiControl(ponyPanelsList.get(2), new Vector2(1366, 130), new Vector2(966, 130), new Vector2(1366, 130));
			this.addGuiControl(ponyBoxesList.get(2), new Vector2(1366, 130), new Vector2(966, 130), new Vector2(1366, 130));
			this.addGuiControl(ponyLabelsList.get(2), new Vector2(1366, 390), new Vector2(966, 390), new Vector2(1366, 390));
		}
		
	}

	private void addPonyPanel(int i) {
		Panel p = new Panel();
		p.setBounds(-1000, -1000, 250, 208);
		p.setBgColor(new Color(0,0,0,0.1f));

		Label l = new Label();
		l.setBounds(-1000, -1000, 250, 50);
		l.setText("Empty");

		final AnimationBox a = new AnimationBox();
		AnimationPlayer player = AnimationFromCharacterHelper.animationPlayerFromCharacter(characters.get(i), manager).clone();
		player.startAnimation("idle");
		a.setAnimationPlayer(player);
		a.setScale(new Vector2(2,2));
		a.setBounds(0, 0, 250, 208);
		l.setText(characters.get(i).name);

		if(numOfPonies == 3) {
			disableCreateBtn();
		}

		a.addClicked(new IEventListener<EventArgs>() {
			@Override
			public void onEvent(Object sender, EventArgs e) {
				for(int i = 0; i < numOfPonies; i++) {
					ponyBoxesList.get(i).getAnimationPlayer().startAnimation("idle");
				}

				onClicked(a.getAnimationPlayer());
				selectedPony = ponyBoxesList.indexOf(sender);
			}
		});

		this.ponyBoxesList.add(a);
		this.ponyPanelsList.add(p);
		this.ponyLabelsList.add(l);
	}
	
	
	
}
