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

class Plane: public Displayable{

private:
	XSizeHints planeHint;
	XSizeHints bombHint;
	//For simple implementation, or at least for now, only allow one bomb drop at a time
	bool isBombing;
 
public:
	Plane();
	Plane(XInfo &xinfo);
	virtual void paint(XInfo &xinfo);
	void movePlane(KeySym key);
	
};

const int SCENE_HEIGHT = 7;
const int SCENE_WIDTH = 13;
const int TILE_MAX = 60;

class Scene: public Displayable{
private:
	list<int> sceneTile;
	int size;
public:
	Scene();
	Scene(XInfo &xinfo);
	virtual void paint(XInfo &xinfo);
};


