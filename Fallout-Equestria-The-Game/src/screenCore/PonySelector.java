package screenCore;

import graphics.Color;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import animation.AnimationFromCharacterHelper;
import animation.AnimationPlayer;

import common.PlayerCharacteristics;

import math.Vector2;
import misc.EventArgs;
import misc.IEventListener;
import GUI.controls.AnimationBox;
import GUI.controls.Button;
import GUI.controls.Label;
import GUI.controls.Panel;
import GUI.controls.PonyBox;
import content.ContentManager;
import utils.ServerInfo;
import utils.TimeSpan;

public class PonySelector extends TransitioningGUIScreen{
	private List<PlayerCharacteristics> characters;
	private List<Panel> ponyPanelsList;
	private List<Label> ponyLabelsList;
	private List<AnimationBox> ponyBoxesList;

	private Button selectBtn;
	private Button deleteBtn;

	public PonySelector(String lookAndFeelPath) {
		super(false, TimeSpan.fromSeconds(1.0f), TimeSpan.fromSeconds(1.0f), lookAndFeelPath);
	}

	public void initialize(ContentManager manager) {
		super.initialize(manager);

		File characterFolder = new File("resources\\characters");
		File[] characterFiles = characterFolder.listFiles();
		this.characters = new ArrayList<PlayerCharacteristics>();

		int numOfPonies = characterFiles.length < 3 ? characterFiles.length : 3;

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

		this.deleteBtn = new Button();
		deleteBtn.setBounds(-1000, -1000, 200, 50);
		deleteBtn.setText("Delete");
		deleteBtn.setEnabled(false);
		this.addGuiControl(deleteBtn, new Vector2(1366/2 -100, 768), new Vector2(1366/2 -100, 570), new Vector2(1366/2 -100, 768));

		Button createBtn = new Button();
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

		for(int i = 0; i < numOfPonies; i++) {
			Panel p = new Panel();
			p.setBounds(-1000, -1000, 250, 208);
			p.setBgColor(new Color(0,0,0,0.1f));
			p.addFocusGainedEvent(new IEventListener<EventArgs>() {
				@Override
				public void onEvent(Object sender, EventArgs e) {
					enableButtons();
				}
			});

			p.addFocusLostEvent(new IEventListener<EventArgs>() {
				@Override
				public void onEvent(Object sender, EventArgs e) {
					disableButtons();
				}
			});

			Label l = new Label();
			l.setText(characters.get(i).name);
			l.setBounds(-1000, -1000, 250, 50);
			
			AnimationBox a = new AnimationBox();
			AnimationPlayer player = AnimationFromCharacterHelper.animationPlayerFromCharacter(characters.get(i), manager).clone();
			player.startAnimation("idle");
			a.setAnimationPlayer(player);
			a.setScale(new Vector2(2,2));
			a.setBounds(0, 0, 250, 208);
			
			this.ponyBoxesList.add(a);
			this.ponyPanelsList.add(p);
			this.ponyLabelsList.add(l);
		}
		if(ponyPanelsList.size() > 0){
			this.addGuiControl(ponyPanelsList.get(0), new Vector2(-250, 130), new Vector2(150, 130), new Vector2(-250, 130));
			this.addGuiControl(ponyBoxesList.get(0), new Vector2(-250, 130), new Vector2(150, 130), new Vector2(-250, 130));
			this.addGuiControl(ponyLabelsList.get(0), new Vector2(-250, 390), new Vector2(150, 390), new Vector2(-250, 390));
		}
		if(ponyPanelsList.size() > 1){
			this.addGuiControl(ponyPanelsList.get(1), new Vector2(1366/2-125, -250), new Vector2(1366/2-125, 130), new Vector2(1366/2-125, -250));
			this.addGuiControl(ponyBoxesList.get(1), new Vector2(1366/2-125, -250), new Vector2(1366/2-125, 130), new Vector2(1366/2-125, -250));
			this.addGuiControl(ponyLabelsList.get(1), new Vector2(1366/2-125, -250), new Vector2(1366/2-125, 390), new Vector2(1366/2-125, -250));
		}
		if(ponyPanelsList.size() > 2){
			this.addGuiControl(ponyPanelsList.get(2), new Vector2(1366, 130), new Vector2(966, 130), new Vector2(1366, 130));
			this.addGuiControl(ponyBoxesList.get(2), new Vector2(1366, 130), new Vector2(966, 130), new Vector2(1366, 130));
			this.addGuiControl(ponyLabelsList.get(2), new Vector2(1366, 390), new Vector2(966, 390), new Vector2(1366, 390));
		}


	}

	public void enableButtons() {
		this.selectBtn.setEnabled(true);
		this.deleteBtn.setEnabled(true);
	}

	public void disableButtons() {
		this.selectBtn.setEnabled(false);
		this.deleteBtn.setEnabled(false);
	}

	public void gotoCreator() {
		this.ScreenManager.addScreen("PonyCreator");
	}

	public void goBack() {
		this.exitScreen();
	}
}
