import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

/*
 * 원본 미니프로젝트의 images/, music/, data/ 리소스는 보고서(PDF)에 들어있지 않다.
 * 그래서 실행만이라도 되도록 "자리표시(placeholder)" 리소스를 만들어 주는 도구다.
 *
 * 실행 방법 (프로젝트 폴더에서):
 *   javac -encoding UTF-8 -d out/tools tools/GenerateAssets.java
 *   java -cp out/tools GenerateAssets
 *
 * 이미 같은 이름의 파일이 있으면 건너뛰므로,
 * 나중에 진짜 이미지/음악으로 하나씩 바꿔 넣어도 덮어쓰지 않는다.
 */
public class GenerateAssets {

	public static void main(String[] args) throws Exception {
		new File("images").mkdirs();
		new File("music").mkdirs();
		new File("data").mkdirs();

		// 배경 이미지들 (화면 전체를 채우므로 크게 만든다)
		background("images/karmen.jpg", 1920, 1080, new Color(18, 20, 40), new Color(70, 20, 60), "KARMEN");
		background("images/leaderboard.png", 1920, 1080, new Color(12, 16, 32), new Color(40, 55, 110), "");
		background("images/normalBackground.jpg", 1920, 1080, new Color(14, 24, 44), new Color(30, 70, 110), "NORMAL");
		background("images/hardBackground.jpg", 1920, 1080, new Color(40, 10, 14), new Color(110, 30, 30), "HARD");
		background("images/clashBackground.jpg", 1920, 1080, new Color(8, 8, 20), new Color(60, 20, 90), "");

		// 난이도 선택 아이콘
		icon("images/normal.jpg", 100, 120, new Color(45, 60, 100), "NOR");
		icon("images/normalClicked.jpg", 100, 120, new Color(0, 140, 255), "NOR");
		icon("images/hard.jpg", 120, 120, new Color(90, 40, 40), "HARD");
		icon("images/hardClicked.jpg", 120, 120, new Color(230, 60, 60), "HARD");

		// 상태(모코코) 아이콘
		icon("images/mokoko.jpg", 200, 200, new Color(60, 120, 70), "^_^");
		icon("images/decrease.jpg", 200, 200, new Color(130, 50, 50), "T_T");
		icon("images/power.jpg", 200, 200, new Color(150, 110, 30), "!!!");
		icon("images/usePotion.jpg", 200, 200, new Color(50, 110, 140), "+HP");

		// 배틀 아이템 아이콘
		icon("images/empty.jpg", 100, 100, new Color(45, 45, 45), "-");
		icon("images/healingPotion.jpg", 100, 100, new Color(190, 60, 90), "HP");
		icon("images/atropine.jpg", 100, 100, new Color(200, 150, 40), "ATK");
		icon("images/destructionBomb.jpg", 100, 100, new Color(70, 70, 90), "BOMB");

		// 에스더 스킬 아이콘 (미구현 기능이지만 화면에는 출력된다)
		icon("images/nineveh.jpg", 150, 150, new Color(50, 50, 70), "NIN");
		icon("images/nineveh_Full.jpg", 150, 150, new Color(120, 100, 220), "NIN");
		icon("images/wei.jpg", 150, 150, new Color(50, 50, 70), "WEI");
		icon("images/wei_Full.jpg", 150, 150, new Color(120, 100, 220), "WEI");
		icon("images/inanna.jpg", 150, 150, new Color(50, 50, 70), "INA");
		icon("images/inanna_Full.jpg", 150, 150, new Color(120, 100, 220), "INA");

		// 암흑 게이지 배경
		icon("images/darkGage.jpg", 180, 180, new Color(25, 25, 35), "DARK");
		icon("images/darkFull.jpg", 180, 180, new Color(90, 20, 90), "DARK");

		// 배경 음악 자리표시 - 무음 wav
		silentWav("music/lobby.wav", 2);
		silentWav("music/backgroundMusic.wav", 2);

		// 단어 목록과 랭킹 파일
		words("data/word.txt");
		emptyFile("data/ranking.txt");

		System.out.println("자리표시 리소스 생성 완료");
	}

	// 그라디언트 배경 + 가운데 큰 글자
	private static void background(String path, int w, int h, Color from, Color to, String text) throws IOException {
		File f = new File(path);
		if (f.exists()) return;

		boolean png = path.endsWith(".png");
		BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = img.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g.setPaint(new GradientPaint(0, 0, from, w, h, to));
		g.fillRect(0, 0, w, h);

		if (!text.isEmpty()) {
			g.setColor(new Color(255, 255, 255, 40));
			g.setFont(new Font("Arial", Font.BOLD, 220));
			int tw = g.getFontMetrics().stringWidth(text);
			g.drawString(text, (w - tw) / 2, h / 2 + 80);
		}
		g.dispose();

		ImageIO.write(img, png ? "png" : "jpg", f);
		System.out.println("생성 : " + path);
	}

	// 단색 아이콘 + 짧은 글자
	private static void icon(String path, int w, int h, Color base, String text) throws IOException {
		File f = new File(path);
		if (f.exists()) return;

		BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = img.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g.setColor(base);
		g.fillRect(0, 0, w, h);
		g.setColor(base.brighter());
		g.drawRect(2, 2, w - 5, h - 5);

		g.setColor(Color.WHITE);
		int size = Math.max(12, Math.min(w, h) / 4);
		g.setFont(new Font("Arial", Font.BOLD, size));
		int tw = g.getFontMetrics().stringWidth(text);
		g.drawString(text, (w - tw) / 2, h / 2 + size / 3);
		g.dispose();

		ImageIO.write(img, "jpg", f);
		System.out.println("생성 : " + path);
	}

	// 지정한 초 길이의 무음 wav 파일
	private static void silentWav(String path, int seconds) throws IOException {
		File f = new File(path);
		if (f.exists()) return;

		AudioFormat format = new AudioFormat(44100f, 16, 1, true, false);
		int frames = (int) (format.getFrameRate() * seconds);
		byte[] data = new byte[frames * format.getFrameSize()];   // 전부 0 = 무음

		AudioInputStream ais = new AudioInputStream(new ByteArrayInputStream(data), format, frames);
		AudioSystem.write(ais, AudioFileFormat.Type.WAVE, f);
		ais.close();
		System.out.println("생성 : " + path);
	}

	// 게임에서 떨어질 단어 목록
	private static void words(String path) throws IOException {
		File f = new File(path);
		if (f.exists()) return;

		String[] list = { "apple", "sword", "shield", "dragon", "karmen", "clash", "thread", "object", "class",
				"method", "public", "static", "swing", "panel", "label", "button", "event", "listener", "runnable",
				"synchronized", "abyss", "raid", "potion", "bomb", "score", "ranking", "damage", "dark", "boss",
				"attack" };

		PrintWriter pw = new PrintWriter(f, "UTF-8");
		for (String s : list) {
			pw.println(s);
		}
		pw.close();
		System.out.println("생성 : " + path);
	}

	// 빈 파일 생성 (랭킹 파일)
	private static void emptyFile(String path) throws IOException {
		File f = new File(path);
		if (f.exists()) return;

		f.createNewFile();
		System.out.println("생성 : " + path);
	}
}
