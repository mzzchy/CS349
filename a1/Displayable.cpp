#include "Displayable.h"

void Text::paint(XInfo &xinfo) {  
	XDrawImageString( xinfo.display, xinfo.window, xinfo.gc, 
		this->x, this->y, this->s.c_str(), this->s.length() );
//XDrawText
}
      


Plane::Plane(XInfo &xinfo){
	x = xinfo.width/2;
	y = xinfo.height/2;
	width = 30;
	height = 40;
}

void Plane::paint(XInfo &xinfo){
	XDrawRectangle(xinfo.display, xinfo.window, xinfo.gc, 
		x*xinfo.wRatio, y*xinfo.hRatio, width*xinfo.wRatio, height*xinfo.hRatio);

}


