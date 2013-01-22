#include "Displayable.h"
#include <cstdlib> 

//Text
void Text::paint(XInfo &xinfo) {  
	XDrawImageString( xinfo.display, xinfo.window, xinfo.gc, 
		this->x, this->y, this->s.c_str(), this->s.length());
//XDrawText
}

///////////////////////////////////////////////////////////////////      
//Plane
Plane::Plane(XInfo &xinfo){
	planeHint.x = xinfo.width/2;
    planeHint.y = xinfo.height/2;
    planeHint.width = 30;
    planeHint.height = 40;
    planeHint.flags = PPosition | PSize;

	bombHint.x = xinfo.width/2;
    bombHint.y = xinfo.height/2;
    bombHint.width = 20;
    bombHint.height = 10;
    bombHint.flags = PPosition | PSize;

	isBombing = false;
}

void Plane::paint(XInfo &xinfo){
	static int t = 0;

	XDrawRectangle(xinfo.display, xinfo.window, xinfo.gc, 
		planeHint.x*xinfo.wRatio,  (xinfo.height- planeHint.y)*xinfo.hRatio, 
		planeHint.width*xinfo.wRatio, planeHint.height*xinfo.hRatio);

	if(isBombing){
		
		XDrawRectangle(xinfo.display, xinfo.window, xinfo.gc, 
		(bombHint.x - t)*xinfo.wRatio,  (xinfo.height-(bombHint.y-t))*xinfo.hRatio, 
		bombHint.width*xinfo.wRatio, bombHint.height*xinfo.hRatio);
		t += 1;
	}
	//TODO: collision detection
	//check for collision && out of drawing area
	if(bombHint.x - t < 0 || bombHint.y-t < 0){
		isBombing = false;
		t = 0;
	}
}

void Plane::movePlane(KeySym key){
	switch(key){
     	case XK_Left: planeHint.x-=2;break;
     	case XK_Up: planeHint.y+=2;break;
      	case XK_Right: planeHint.x+=2;break;
      	case XK_Down: planeHint.y-=2;break;	
		case XK_space:
			if(!isBombing){ 
				isBombing= true; 
				bombHint.x = planeHint.x+ bombHint.width;
    			bombHint.y = planeHint.y- bombHint.height;
			}break; //Drop bomb
		default:
			break;
      }
}

/////////////////////////////////////////////////////////////////////
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



