#include "Displayable.h"
//#include "Algebra.h"
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
	t = 0;
}

void Plane::paint(XInfo &xinfo){

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
	XSizeHints aplaneHint = planeHint;
	aplaneHint.x *= xinfo.wRatio; 
    aplaneHint.y = (xinfo.height- aplaneHint.y)*xinfo.hRatio; 
	aplaneHint.width *= xinfo.wRatio;
	aplaneHint.height *= xinfo.hRatio;
   	//Check if plane hits anything
	if(isCollide(xinfo, aplaneHint)){
	
	}else{

	}

	//Check if bomb hits anything
	XSizeHints abombHint = bombHint;
	abombHint.x = (abombHint.x - t)*xinfo.wRatio;  
    abombHint.y = (xinfo.height-(abombHint.y-t))*xinfo.hRatio; 
	abombHint.width *= xinfo.wRatio;
	abombHint.height *= xinfo.hRatio; 
	if(isCollide(xinfo, abombHint)){
		isBombing = false;
		t = 0;
	}
	if(bombHint.x - t < 0 || bombHint.y-t < 0){
		isBombing = false;
		t = 0;
	}
}

//TODO: add XInfo &xinfo, here
void Plane::movePlane( KeySym key){
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
			}
			break; //Drop bomb
		default:
			break;
      }
}

bool Plane::isCollide(XInfo &xinfo, XSizeHints hint){
	return s->isCollide(xinfo, hint);
}
/////////////////////////////////////////////////////////////////////
//Scene

Scene::Scene(XInfo &xinfo){
	for(int i = 0;i<SCENE_WIDTH ; i +=1){
		sceneTile.push_back(rand() %SCENE_HEIGHT);
	}
	//-1 for recycling
	size = min(xinfo.width/(SCENE_WIDTH-1), xinfo.height/SCENE_HEIGHT);
	t = 0;
}

void Scene::paint(XInfo &xinfo){
	//static extern int t = 0;
	list<int>::iterator it = sceneTile.begin();
	for(int i = 0;i<SCENE_WIDTH ; i +=1){
		XDrawRectangle(xinfo.display, xinfo.window, xinfo.gc, 
			(t+i*size)*xinfo.wRatio, (xinfo.height- (*it)*size)*xinfo.hRatio,
			 size*xinfo.wRatio,  (*it)*size*xinfo.hRatio);
		it++;
	}
	
	//Recycle
	t -= 1;
	if((t+size)*xinfo.wRatio<0){
		sceneTile.pop_front();
		sceneTile.push_back(rand() %SCENE_HEIGHT);
		t = 0;
	}
}

bool Scene::isCollide(XInfo &xinfo, XSizeHints hint){
	list<int>::iterator it = sceneTile.begin();
	for(int i = 0;i<SCENE_WIDTH-1 ; i +=1){
		XSizeHints aTile;
		aTile.x = (t+i*size)*xinfo.wRatio;
    	aTile.y = (xinfo.height- (*it)*size)*xinfo.hRatio; //xinfo.height- 
    	aTile.width = size*xinfo.wRatio;
    	aTile.height = (*it)*size*xinfo.hRatio;
    	aTile.flags = PPosition | PSize;
    	it++;
    	if(isHintCollide(hint, aTile)){
    		return true;
    	}
	}
	return false;
}

bool Scene::isHintCollide(XSizeHints a, XSizeHints b){
	if(a.x+ a.width < b.x || a.x > b.x + b.width ){ 
		//cout<<'w'<<endl;
		return false;
	}else if(a.y + a.height < b.y || a.y > b.y + b.height){
		//cout<<'h'<<endl;
		return false;
	}
	//cout<<"a x "<<a.x<<" y "<<a.y<<" w "<<a.width<<" h "<<a.height<<endl;
	//cout<<"b x "<<b.x<<" y "<<b.y<<" w "<<b.width<<" h "<<b.height<<endl;
	return true;
}


