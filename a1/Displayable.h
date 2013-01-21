#include <X11/Xlib.h>
#include <X11/Xutil.h>
#include <iostream>
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
	int width, height,x, y;
	double ratio;
public:
	Plane();
	Plane(XInfo &xinfo);
	virtual void paint(XInfo &xinfo);
	
};


