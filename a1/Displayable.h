#include <X11/Xlib.h>
#include <X11/Xutil.h>
#include <iostream>
#include <list>

using namespace std;

struct XInfo{  
	Display  *display;
   	Window   window;
   	GC       gc;
	int 	 height,width;
	double   hRatio,wRatio;
	void setRatio(int newHeight,int newWidth){
		hRatio = (double)newHeight/(double)height;
		wRatio = (double)newWidth/(double)width;
	}
	//Pixmap	pixmap;	
};


class Displayable{ 
	public:
      	virtual void paint(XInfo &xinfo) = 0;
		XSizeHints makeHint(XSizeHints aHint, XInfo &xinfo);
		XSizeHints makeHint(int , int y,int width, int height, XInfo &xinfo);
		bool isHintCollide(XSizeHints a, XSizeHints b);
};       


class Text : public Displayable{  
public:
      virtual void paint(XInfo &xinfo);
      Text(int x, int y, string s):x(x), y(y), s(s)  {}
      void setText(string t);

private:
      int x, y;
      string s;
};

const int SCENE_HEIGHT = 7;
const int SCENE_WIDTH = 15;
const int TILE_MAX = 60;
const int MAX_BOMB_COUNT = 6;

class Tile:public Displayable{
	private:
		XSizeHints tileHint;
		XSizeHints enemyHint;
		XSizeHints bulletHint;
		int x_t,y_t;
		bool hasEnemy;
	public:
		Tile(int size, int x);
		virtual void paint(XInfo &xinfo);
		XSizeHints getEnemeyHint(XInfo &xinfo);
		XSizeHints getTileHint(XInfo &xinfo);
		void setEnemy(bool enemy);
		bool isEnemy();
		bool isTileOutside();
		bool isCollide(XInfo &xinfo, XSizeHints hint);
		
};

class Plane: public Displayable{

private:
	XSizeHints planeHint;
	XSizeHints bombHint;
	list<XSizeHints> bombArray;
 	int t;
	bool god;
	bool alive;
public:
	
	Plane(XInfo &xinfo);
	virtual void paint(XInfo &xinfo);
	void movePlane(KeySym key);
	bool isCollide(XInfo &xinfo, XSizeHints hint);
	void setGod();
	bool isGod();
	bool isAlive();
	void reset();
	void setAlive(bool live);
	XSizeHints getPlaneHint(XInfo &xinfo);
private:
	void drawPlane(XInfo &xinfo);
	void drawBomb(XInfo &xinfo);
	void addBomb();
	
};

class Scene: public Displayable{
private:
	list<Tile*> tileList;
	Plane * plane;
	Text * loseText;
	Text * winText;
	Text * scoreText;
	int size;
	int tilePassed;
	int score;
public:
	Scene(XInfo &xinfo);
	virtual void paint(XInfo &xinfo);
	void updateCollide(XInfo &xinfo);
	bool isHintCollide(XSizeHints a, XSizeHints b);
	void reset();
	void getInut(KeySym key,char ch);
};







