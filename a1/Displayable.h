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
};       


class Text : public Displayable{  
public:
      virtual void paint(XInfo &xinfo);
      Text(int x, int y, string s):x(x), y(y), s(s)  {}
      
private:
      int x, y;
      string s;
};

const int SCENE_HEIGHT = 7;
const int SCENE_WIDTH = 13;
const int TILE_MAX = 60;
const int MAX_BOMB_COUNT = 6;

class Enemy:public Displayable{
	private:
		//list<XSizeHints> bulletArray;
		XSizeHints enemyHint;
		XSizeHints bulletHint;
		int x_t,y_t;
	public:
		Enemy(XInfo &xinfo,int x, int y);
		virtual void paint(XInfo &xinfo);
};

class Scene: public Displayable{
private:
	list<int> sceneTile;
	list<Enemy*> enemyList;
	int size;
	int t;
public:
	Scene();
	Scene(XInfo &xinfo);
	virtual void paint(XInfo &xinfo);
	bool isCollide(XInfo &xinfo, XSizeHints hint);
	bool isHintCollide(XSizeHints a, XSizeHints b);
	void reset();
};


class Plane: public Displayable{

private:
	XSizeHints planeHint;
	XSizeHints bombHint;
	list<XSizeHints> bombArray;
 	int t;
	bool isGod;
	bool alive;
public:
	//TODO: testing
	Scene * s;
	Plane(XInfo &xinfo);
	virtual void paint(XInfo &xinfo);
	void movePlane(KeySym key);
	bool isCollide(XInfo &xinfo, XSizeHints hint);
	void setGod();
	bool isAlive();
	void reset();
private:
	void drawPlane(XInfo &xinfo);
	void drawBomb(XInfo &xinfo);
	void addBomb();
	
};




