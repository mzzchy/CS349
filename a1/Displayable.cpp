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
	//Here should change to hit box
	planeHint.x = xinfo.width/2;
    planeHint.y = xinfo.height/2;
    planeHint.width = 30;
    planeHint.height = 20;
    planeHint.flags = PPosition | PSize;

	bombHint.x = xinfo.width/2;
    bombHint.y = xinfo.height/2;
    bombHint.width = 10;
    bombHint.height = 10;
    bombHint.flags = PPosition | PSize;

	t = 0;
	isGod = false;
	alive = false;
}

void Plane::setGod(){
	isGod = !isGod;
}

bool Plane::isAlive(){
	return alive;
}

void Plane::drawPlane(XInfo &xinfo){
	
	//Body
	XFillArc(xinfo.display, xinfo.window, xinfo.gc,
		planeHint.x*xinfo.wRatio,  (xinfo.height- planeHint.y)*xinfo.hRatio, 
		30*xinfo.wRatio, 15*xinfo.hRatio, 0, 360*64);

	//Tail
	XFillArc(xinfo.display, xinfo.window, xinfo.gc,
		(planeHint.x-10 )*xinfo.wRatio,  (xinfo.height-(planeHint.y+5))*xinfo.hRatio, 
		5*xinfo.wRatio, 20*xinfo.hRatio, 0, 360*64); 
	
	XDrawLine(xinfo.display, xinfo.window, xinfo.gc, 
			(planeHint.x+15 )*xinfo.wRatio,  (xinfo.height-(planeHint.y+0))*xinfo.hRatio, 
			(planeHint.x+25 )*xinfo.wRatio,  (xinfo.height-(planeHint.y+8))*xinfo.hRatio);

	//Top
	XFillArc(xinfo.display, xinfo.window, xinfo.gc,
		(planeHint.x+15 )*xinfo.wRatio,  (xinfo.height-(planeHint.y+10))*xinfo.hRatio, 
		25*xinfo.wRatio, 7*xinfo.hRatio, 0, 360*64);
	
	XDrawLine(xinfo.display, xinfo.window, xinfo.gc, 
			(planeHint.x-8 )*xinfo.wRatio,  (xinfo.height-(planeHint.y-5))*xinfo.hRatio, 
			(planeHint.x+5 )*xinfo.wRatio,  (xinfo.height-(planeHint.y-5))*xinfo.hRatio);
}

void Plane::addBomb(){
	if(bombArray.size()<MAX_BOMB_COUNT){
		XSizeHints aBombHint = planeHint;
		aBombHint.x = planeHint.x+ bombHint.width;
		aBombHint.y = planeHint.y- bombHint.height;
		bombArray.push_back(aBombHint);
	}
}

void Plane::drawBomb(XInfo &xinfo){
	list<XSizeHints>::iterator it = bombArray.begin();
	for( ;it!=bombArray.end();it++){
		XFillArc(xinfo.display, xinfo.window, xinfo.gc,
		(*it).x*xinfo.wRatio,  (xinfo.height-(*it).y)*xinfo.hRatio, 
		10*xinfo.wRatio, 10*xinfo.hRatio, 0, 360*64);
		(*it).x -= 1;
		(*it).y -= 1;
	}
}

void Plane::paint(XInfo &xinfo){
	drawPlane(xinfo);
	drawBomb(xinfo);
	t += 1;

	XSizeHints aplaneHint = planeHint;
	aplaneHint.x *= xinfo.wRatio; 
    aplaneHint.y = (xinfo.height- aplaneHint.y)*xinfo.hRatio; 
	aplaneHint.width *= xinfo.wRatio;
	aplaneHint.height *= xinfo.hRatio;
	//TODO: collision detection
   	//Check if plane hits anything
	if(!isGod&&isCollide(xinfo, aplaneHint)){
		alive = false;
	}

	//check for bomb collision && out of drawing area
	list<XSizeHints>::iterator it = bombArray.begin();
	while(it!=bombArray.end()){
		if((*it).x < 0 || (*it).y < 0){
			bombArray.erase(it++);
			continue;
		}else {
			XSizeHints abombHint = (*it);
			abombHint.x *= xinfo.wRatio;  
   	 		abombHint.y = (xinfo.height-abombHint.y)*xinfo.hRatio; 
			abombHint.width *= xinfo.wRatio;
			abombHint.height *= xinfo.hRatio;
			if(isCollide(xinfo, abombHint)){
				bombArray.erase(it++);
				continue;
			}
		}
		it++;
	}
	
}

//TODO: add XInfo &xinfo, here
void Plane::movePlane( KeySym key){
	switch(key){
     	case XK_Left: planeHint.x-=3;break;
     	case XK_Up: planeHint.y+=3;break;
      	case XK_Right: planeHint.x+=3;break;
      	case XK_Down: planeHint.y-=3;break;	
		case XK_space: addBomb(); break; //Drop bomb
		default:
			break;
      }
}

void Plane::reset(){
	alive = true;
	isGod = false;
}

bool Plane::isCollide(XInfo &xinfo, XSizeHints hint){
	return s->isCollide(xinfo, hint);
}
/////////////////////////////////////////////////////////////////////
//Scene
//Now make enemys

Scene::Scene(XInfo &xinfo){
	for(int i = 0;i<SCENE_WIDTH ; i +=1){
		sceneTile.push_back(rand() %3);
	}
	//-1 for recycling
	size = min(xinfo.width/(SCENE_WIDTH-1), xinfo.height/SCENE_HEIGHT);
	t = 0;
}

void Scene::paint(XInfo &xinfo){
	//Print enemy
	list<Enemy*>::iterator enemyIt = enemyList.begin();	
	for(;enemyIt!=enemyList.end() ; enemyIt++){
		(*enemyIt)->paint(xinfo);
	}

	//Print tile
	list<int>::iterator it = sceneTile.begin();
	for(int i = 0;i<SCENE_WIDTH ; i +=1){
		XDrawRectangle(xinfo.display, xinfo.window, xinfo.gc, 
			(t+i*size)*xinfo.wRatio, (xinfo.height- (*it)*size)*xinfo.hRatio,
			 size*xinfo.wRatio,  (*it)*size*xinfo.hRatio);
		it++;
	}
	
	
	//Recycle
	t -= 2;
	if((t+size)*xinfo.wRatio<0){
		sceneTile.pop_front();
		int y = rand() %SCENE_HEIGHT;
		sceneTile.push_back(y);
		
		if(rand()%4 == 0){
			//add a new enemy to the list
			Enemy * aEnemy  = new Enemy(xinfo, SCENE_WIDTH*size, y*size);
			enemyList.push_back(aEnemy);
		}
		t = 0;
	}
}

void Scene::reset(){
	sceneTile.clear();
	for(int i = 0;i<SCENE_WIDTH ; i +=1){
		sceneTile.push_back(rand() %3);
	}
}

bool Scene::isCollide(XInfo &xinfo, XSizeHints hint){
	//Scene tile
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
		return false;
	}else if(a.y + a.height < b.y || a.y > b.y + b.height){
		return false;
	}
	return true;
}

////////////////////////////////////////////////////////////////
//Enemy

Enemy::Enemy(XInfo &xinfo, int x, int y){
	
    enemyHint.width = 20;
    enemyHint.height = 10;
	enemyHint.x = x;
    enemyHint.y = y+enemyHint.height ;
    enemyHint.flags = PPosition | PSize;

	bulletHint.width = 5;
    bulletHint.height = 10;
	bulletHint.x = x;
    bulletHint.y = y-enemyHint.height;
    bulletHint.flags = PPosition | PSize;

	x_t = 0;
	y_t = 0;
}

void Enemy::paint(XInfo &xinfo){
	//TODO: add update to bullet and enemy position
	//Enemy
	XFillRectangle(xinfo.display, xinfo.window, xinfo.gc, 
			(enemyHint.x-x_t)*xinfo.wRatio, (xinfo.height- enemyHint.y)*xinfo.hRatio,
			enemyHint.width*xinfo.wRatio,  enemyHint.height*xinfo.hRatio);
	
	//bullet
	XFillArc(xinfo.display, xinfo.window, xinfo.gc,
		(bulletHint.x-x_t)*xinfo.wRatio,  (xinfo.height - (bulletHint.y+y_t))*xinfo.hRatio, 
		bulletHint.width*xinfo.wRatio, bulletHint.height*xinfo.hRatio, 0, 360*64);
	
	x_t += 2;
	y_t += 2;
	//enemyHint.x -= 1;
	if(bulletHint.y+y_t> xinfo.height){
		y_t = 0;
	}

}

