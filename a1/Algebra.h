#include <X11/Xlib.h>
#include <X11/Xutil.h>
#include <iostream>

bool isCollide(XSizeHints a, XSizeHints b){
	if(a.x+ a.width < b.x || a.x > b.x + b.width ){ 
		return true;
	}else if(a.y + a.height < b.y || a.y < b.y + b.height){
		return true;
	}
	return false
}

