#include "Displayable.h"
#include <cstdlib> 

//Text
void Text::paint(XInfo &xinfo) {  
	XDrawImageString( xinfo.display, xinfo.window, xinfo.gc, 
		this->x, this->y, this->s.c_str(), this->s.length() );
//XDrawText
}
      
//Plane
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

//Scene

Scene::Scene(XInfo &xinfo){
	for(int i = 0;i<SCENE_WIDTH ; i +=1){
		sceneTile.push_back(rand() %SCENE_HEIGHT);
	}
	//-1 for recycling
	size = min(xinfo.width/(SCENE_WIDTH-1), xinfo.height/SCENE_HEIGHT);
}

void Scene::paint(XInfo &xinfo){
	static int t = 0;
	list<int>::iterator it = sceneTile.begin();
	for(int i = 0;i<SCENE_WIDTH ; i +=1){
		XDrawRectangle(xinfo.display, xinfo.window, xinfo.gc, 
			(t+i*size)*xinfo.wRatio, (xinfo.height- (*it)*size)*xinfo.hRatio,
			 size*xinfo.wRatio,  (*it)*size*xinfo.hRatio);
		it++;
	}
	
	t -= 1;
	//Recycle
	if((t+size)*xinfo.wRatio<0){
		sceneTile.pop_front();
		sceneTile.push_back(rand() %SCENE_HEIGHT);
		t = 0;
	}
}



