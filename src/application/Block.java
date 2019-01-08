package application;

import javafx.scene.paint.Color;

public class Block {
	public static Color[] colorlist =
		{Color.BLACK,Color.RED,Color.ORANGE,Color.BLUE,
			Color.CYAN,Color.YELLOW,Color.GREEN,Color.MAGENTA,
			Color.YELLOW,Color.GRAY};


	public static String[] imgpath =
		{
			"block/haikei.png","block/blockRED.png","block/blockORANGE.png","block/blockBULE.png",
			"block/blockCYAN.png","block/blockYELLOW.png","block/blockGREEN.png","block/blockMAGENTA.png",
			"block/blockNULL.png","block/wall.png"
		};
	public static final int[][][][] blkPtrn=
		{
			{//ダミー(ブロックの種類0番目は使用しない)
				{//向き初期状態(回転0度)
					{0,0,0,0},
					{0,0,0,0},
					{0,0,0,0},
					{0,0,0,0}
				},
				{//(時計回り90度)
					{0,0,0,0},
					{0,0,0,0},
					{0,0,0,0},
					{0,0,0,0}
				},
				{//(時計回り180度)
					{0,0,0,0},
					{0,0,0,0},
					{0,0,0,0},
					{0,0,0,0}
				},
				{//(時計回り270度)
					{0,0,0,0},
					{0,0,0,0},
					{0,0,0,0},
					{0,0,0,0}
				}
			},
			{//Ｉ型(一文字)
				{//向き初期状態(回転0度)
					{0,0,0,0},
					{0,0,0,0},
					{1,1,1,1},
					{0,0,0,0}
				},
				{//(時計回り90度)
					{0,1,0,0},
					{0,1,0,0},
					{0,1,0,0},
					{0,1,0,0}
				},
				{//(時計回り180度)
					{0,0,0,0},
					{0,0,0,0},
					{1,1,1,1},
					{0,0,0,0}
				},
				{//(時計回り270度)
					{0,1,0,0},
					{0,1,0,0},
					{0,1,0,0},
					{0,1,0,0}
				}
			},
			{//Ｌ型
				{//向き初期状態(回転0度)
					{0,0,0,0},
					{2,2,2,0},
					{2,0,0,0},
					{0,0,0,0}
				},
				{//(時計回り90度)
					{0,2,0,0},
					{0,2,0,0},
					{0,2,2,0},
					{0,0,0,0}
				},
				{//(時計回り180度)
					{0,0,0,0},
					{0,0,2,0},
					{2,2,2,0},
					{0,0,0,0}
				},
				{//(時計回り270度)
					{2,2,0,0},
					{0,2,0,0},
					{0,2,0,0},
					{0,0,0,0}
				}
			},
			{//逆Ｌ型
				{//向き初期状態(回転0度)
					{0,0,0,0},
					{3,3,3,0},
					{0,0,3,0},
					{0,0,0,0}
				},
				{//(時計回り90度)
					{0,3,3,0},
					{0,3,0,0},
					{0,3,0,0},
					{0,0,0,0}
				},
				{//(時計回り180度)
					{0,0,0,0},
					{3,0,0,0},
					{3,3,3,0},
					{0,0,0,0}
				},
				{//(時計回り270度)
					{0,3,0,0},
					{0,3,0,0},
					{3,3,0,0},
					{0,0,0,0}
				}
			},
			{//Ｔ型
				{//向き初期状態(回転0度)
					{0,0,0,0},
					{4,4,4,0},
					{0,4,0,0},
					{0,0,0,0}
				},
				{//(時計回り90度)
					{0,4,0,0},
					{0,4,4,0},
					{0,4,0,0},
					{0,0,0,0}
				},
				{//(時計回り180度)
					{0,0,0,0},
					{0,0,0,0},
					{0,4,0,0},
					{4,4,4,0}
				},
				{//(時計回り270度)
					{0,4,0,0},
					{4,4,0,0},
					{0,4,0,0},
					{0,0,0,0}
				}
			},
			{//四角
				{//向き初期状態(回転0度)
					{0,0,0,0},
					{0,5,5,0},
					{0,5,5,0},
					{0,0,0,0}
				},
				{//(時計回り90度)
					{0,0,0,0},
					{0,5,5,0},
					{0,5,5,0},
					{0,0,0,0}
				},
				{//(時計回り180度)
					{0,0,0,0},
					{0,5,5,0},
					{0,5,5,0},
					{0,0,0,0}
				},
				{//(時計回り270度)
					{0,0,0,0},
					{0,5,5,0},
					{0,5,5,0},
					{0,0,0,0}
				}
			},
			{//Ｚ型
				{//向き初期状態(回転0度)
					{0,0,0,0},
					{6,6,0,0},
					{0,6,6,0},
					{0,0,0,0}
				},
				{//(時計回り90度)
					{0,0,6,0},
					{0,6,6,0},
					{0,6,0,0},
					{0,0,0,0}
				},
				{//(時計回り180度)
					{0,0,0,0},
					{6,6,0,0},
					{0,6,6,0},
					{0,0,0,0}
				},
				{//(時計回り270度)
					{0,0,6,0},
					{0,6,6,0},
					{0,6,0,0},
					{0,0,0,0}
				}
			},
			{//Ｓ型
				{//向き初期状態(回転0度)
					{0,0,0,0},
					{0,7,7,0},
					{7,7,0,0},
					{0,0,0,0}
				},
				{//(時計回り90度)
					{7,0,0,0},
					{7,7,0,0},
					{0,7,0,0},
					{0,0,0,0}
				},
				{//向き初期状態(回転0度)
					{0,0,0,0},
					{0,7,7,0},
					{7,7,0,0},
					{0,0,0,0}
				},
				{//(時計回り90度)
					{7,0,0,0},
					{7,7,0,0},
					{0,7,0,0},
					{0,0,0,0}
				}
			},

		};

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
