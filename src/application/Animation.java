package application;

import java.util.ArrayDeque;
import java.util.Deque;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

//ブロックを動かす関係
public class Animation{
	final static  int width = 12;
	final static int height = 22;
	/**
	 * downInterval ブロックが一段落ちる時間間隔 ナノ秒
	 */
	private static SampleController scr;
	/**
	 * downInterval 操作中のブロックが一段落ちる時間
	 * downIntervalB 落ちる速度を速くするときの倍率
	 * downIntervalCcnt ブロック何回ごとに速度を速くするか
	 */
	private long downInterval = 1000000000L;
	private float downIntervalB = 0.85f;
	private int downIntervalCcnt = 5;
	final private long rotateInterval = 200000000L;//回転時、次の回転を受け付けるまでの時間
	final private long moveInterval = 200000000L;//左右移動時、次の移動を受け付けるまでの時間
	private long downTime = 0;//ブロックが落ちる時間カウント用
	private long keyTime = 0;//左右移動待ち時間カウント用
	private boolean keyflag = true;//左右移動待ち時間中はfalse
	private  boolean rotateflag = true;//回転待ち時間中はfalse
	private long rotateTime = 0;
	private Deque<Integer> drquan = new ArrayDeque<>(height);
	private long dropAnimationTime;
	private long dropAnimationInterval = 80000000L;
	public boolean dropAnimationFlag = false;
	/**
	 * deleteAnimationSize
	 * ブロックを消すときの小さくするアニメーションのため
	 * 一列ごとにサイズ補正値を格納
	 * サイズ = 20 - サイズ補正値
	 * deleteAnimationSize[Y座標]で対応
	 */
	private int[] deleteAnimationSize = new int[height];
	public boolean deleteAnimationFlag = false;//消すアニメーション時はtrue
	/**
	 * deleteAnimationYFlag
	 * ブロックを消すときの小さくするアニメーションの
	 * 消す列はtrue
	 * deleteAnimationYFlag[Y座標]で対応
	 */
	public boolean[] deleteAnimationYFlag = new boolean[height];
	public long deleteAnimationInterval = 500000L;//消えるアニメの1フレームの時間
	public long[] deleteAnimationTime = new long[height];//消えるアニメの時間保存用
	public int deleteCnt = 0;//消す列が何行あるかカウント


	private int settingX = 4;
	private int settingY = 0;
	private int blSize = 20;
	public int bsx = settingX;//操作中のブロックのx座標を格納
	public int bsy = settingY;//同y座標
	private int memox;//当たり判定のため一つ前のx座標を格納
	private int memoy;//同y座標


	public Animation(SampleController s) {
		scr = s;
	}
	public void moveIntervalCheck(long now) {
		if(now - keyTime > moveInterval) {
    		keyflag = true;
    	}
	}
	public void rotateIntervalCheck(long now) {
		if(now - rotateTime > rotateInterval) {
    		rotateflag = true;
    	}
	}
	public void blockmove(long now) {
		memox = bsx;
    	memoy = bsy;
		if(scr.vec[2] == true && keyflag) {
    		bsx--;
    		keyflag = false;
    		keyTime = now;
    	}
    	else if(scr.vec[3] == true && keyflag) {
    		bsx++;
    		keyflag = false;
    		keyTime = now;
    	}
    	else if(scr.vec[1] == true) {
    		bsy++;
    		keyflag = false;
    		keyTime = now;
    	}
    	if((scr.vec[4] == true || scr.vec[5] == true) && rotateflag) {
    		if(scr.vec[4] == true) {
    			scr.nowblockprevPattern = scr.nowblockPattern;
    			scr.nowblockPattern = (scr.nowblockPattern + 1) % 4;
    		}
    		else {
    			scr.nowblockprevPattern = scr.nowblockPattern;
    			scr.nowblockPattern = (scr.nowblockPattern - 1 + 4) % 4;
    		}
    		scr.block = Block.blkPtrn[scr.nowblock][scr.nowblockPattern].clone();
    		rotateflag = false;
    		rotateTime = now;
    		rotateCollisionCheck();
    	}
		collisionCheck();
	}
	public void blockDisplay(GraphicsContext gc,long now) {
		int wk,wk2 = 0,size,hosei;
		for(int i = 0;i < height;i++) {
			/**
			 * ブロックが一列並んでるとき
			 * ブロックを少しずつ小さくしていく
			 * アニメーションのため変数を調整
			 */
			int b = blSize;
    		if(deleteAnimationYFlag[i] == true) {
    			//今のブロックの表示サイズ
    			wk = 20 - deleteAnimationSize[i];
    			//ブロックのサイズに合わせて表示する座標調整用
    			wk2 = deleteAnimationSize[i] / 2;
    			for(int j = 0;j < width;j++) {
    				//背景を描画
    				gc.drawImage(scr.blockImage[0], j*b, i*b,b,b);
    			}
    			if(now - deleteAnimationTime[i] > deleteAnimationInterval) {
    				deleteAnimationTime[i] = now;
    				deleteAnimationSize[i]++;
    			}
    			if(deleteAnimationSize[i] == b) {
    				deleteAnimationSize[i] = 0;
    				deleteAnimationYFlag[i] = false;
    				deleteLine(i);
    				deleteCnt--;
    			}
    			if(deleteCnt == 0) {
    				deleteAnimationFlag = false;
    			}
    		}
    		else {
    			wk2 = 0;
    			wk = b;
    		}
    		for(int j = 0;j < width;j++) {
    			if(j == 0 || j == width - 1) {
    				size = b;
    				hosei = 0;
    			}
    			else {
    				size = wk;
    				hosei = wk2;
    			}
    			gc.drawImage(scr.blockImage[scr.tetstage[i][j]], j*b+hosei, i*b+hosei,
    					size,size);

    		}
    	}
	}
	public void sousablockDisplay(GraphicsContext gc,long now) {
		int b = blSize;
		if(scr.gameflag) {
	    	for(int i = 0;i < 4;i++) {
	    		for(int j = 0;j < 4;j++) {
	    			if(scr.block[i][j] >= 1) {
	    				gc.drawImage(scr.blockImage[scr.block[i][j]], bsx*b+b*j, bsy*b+i*b,
	        					b,b);
	    			}
	    		}
	    	}
    	}
	}
	public void setblock() { //下まで落ちたブロックをテトリス盤面に転写
		scr.wkblock = scr.block.clone();
		scr.block = null;
		for(int i = 0;i < 4;i++) {
			for(int j = 0;j < 4;j++) {
				if(scr.wkblock[i][j] >= 1) {
					scr.tetstage[bsy+i][bsx+j] = scr.wkblock[i][j];
				}
			}
		}
	}
	public void downBlock(long now) { //ブロックを一段落す処理
		memox = bsx;
    	memoy = bsy;

    	if(now - downTime > downInterval && scr.gameflag) {
    		bsy++;
    		downTime = now;
    		collisionCheck();
    		if(memoy == bsy) {//これ以上下に落ちることができない場合
    			scr.gameflag = false;
    			setblock();
    			scr.DeleteLineCheck();
    			if(gameEndJudge()) {//終了判定
    				scr.endflag = true;
    			}
    			bsy = settingY;
    			bsx = settingX;
    			downTime = now;
    		}
    	}
    	else if(scr.gameflag == false && now - downTime > 1000000000) {
    		if(scr.blockCnt % downIntervalCcnt == 0) {
    			//一定回数たまったらブロックの落ちる速度を加速
    			downInterval = (long) (downInterval * downIntervalB);
    			scr.scoreFloor += 100;
    		}
    		scr.changeBlock();
			scr.gameflag = true;
    	}
	}
	public boolean gameEndJudge() {
		if(settingX == bsx && settingY == bsy) {
			return true;
		}
		return false;
	}
	public void collisionCheck() {//ブロックの当たり判定
		for(int i = 0;i < 4;i++) {
			for(int j = 0;j < 4;j++) {
				if(((bsy+i < 0 || bsy+i >= height + 1)
				|| (bsx+j < 0 || bsx+j >= width + 1))
				|| scr.tetstage[bsy+i][bsx+j] != 0
				&& scr.block[i][j] >= 1) {
					bsx = memox;
					bsy = memoy;
				}
			}
		}
	}

	public void rotateCollisionCheck() {//ブロック回転時の当たり判定
		int wk1 = bsx;
		int wk2 = bsy;
		for(int i = 0;i < 4;i++) {
			for(int j = 0;j < 4;j++) {
				if(((bsy+i < 0 || bsy+i >= height + 1)
				|| (bsx+j < 0 || bsx+j >= width + 1))
				|| scr.tetstage[bsy+i][bsx+j] != 0
				&& scr.block[i][j] >= 1) {
					if(j >= 2) {
						bsx--;
					}
					else {
						bsx++;
					}
					if(i >= 2) {
						bsy--;
					}
					else {
						bsy++;
					}
				}
			}
		}
		if(afterCollisionCheck()) {
			bsx = wk1;
			bsy = wk2;
			scr.nowblockPattern = scr.nowblockprevPattern;
			scr.block = Block.blkPtrn[scr.nowblock][scr.nowblockPattern].clone();
		}
	}
	public boolean afterCollisionCheck() {//ブロック回転後の当たり判定
		for(int i = 0;i < 4;i++) {
			for(int j = 0;j < 4;j++) {
				if(((bsy+i < 0 || bsy+i >= height + 1)
				|| (bsx+j < 0 || bsx+j >= width + 1))
				|| scr.tetstage[bsy+i][bsx+j] != 0
				&& scr.block[i][j] >= 1) {
					return true;
				}
			}
		}
		return false;
	}
	public void nextBlockArea() {//次のブロックの表示
		scr.nextGc.setFill(Color.WHITE);
    	scr.nextGc.strokeRect(0, 0, 80, 80);
    	scr.nextGc.fillRect(1, 1, 78, 78);
    	int b = blSize;
    	for(int tate = 0;tate < 4;tate++) {
    		for(int yoko = 0;yoko < 4;yoko++) {
    			if(scr.nextblock[tate][yoko] > 0) {
    				if(scr.nextblocknum == 1) {
    					scr.nextGc.drawImage
    					(scr.blockImage[scr.nextblock[tate][yoko]],
    							yoko*b,
    							tate*b-(b - b / 2),
    							b,b);
    				}
    				else if(scr.nextblocknum == 5) {
    					scr.nextGc.drawImage
    					(scr.blockImage[scr.nextblock[tate][yoko]],
    							yoko*b,
    							tate*b,
    							b,b);
    				}
    				else {
    					scr.nextGc.drawImage
    					(scr.blockImage[scr.nextblock[tate][yoko]],
    							yoko*b+(b - b / 2),
    							tate*b,
    							b,b);
    				}
    			}

    		}
    	}
	}
	public int getDeleteLineCnt() {
		return drquan.size();
	}
	public int scoreCalc(int dsize,int floor) {
		return floor * (dsize * dsize);
	}
	public void dropAnimation(long now) {
		if(now - dropAnimationTime < dropAnimationInterval) {
			return;
		}
		else if(drquan.size() > 0) {
			int wk;
			wk = drquan.poll();
			dropAnimationTime = now;
			for(int i = 1;i < width - 1;i++) {
				deleteAfterDrop(i, wk);
			}
		}
		else {
			dropAnimationFlag = false;
		}
	}
	public void deleteAfterDrop(int x,int dstart) {//一列消した後に上のブロックを落とす処理
		for(int i = dstart;i >= 0;i--) {
			if(i - 1 < 0) {
				scr.tetstage[i][x] = 0;
			}
			else {
				scr.tetstage[i][x] = scr.tetstage[i - 1][x];
			}
		}
	}
	public void deleteLine(int y) { //一列を実際に消す
		drquan.add(y);
		for(int j = 1;j < width - 1;j++) {
			scr.tetstage[y][j] = 0;
		}
	}
}
