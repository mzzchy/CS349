#include "Displayable.h"
#include <cstdlib> 
#include <stdio.h>
////////////////////////////////////////////////////////////////
XSizeHints Displayable::makeHint(XSizeHints aHint, XInfo &xinfo){
	XSizeHints hint = aHint;
	hint.x *= xinfo.wRatio;  
   	hint.y = (xinfo.height-hint.y)*xinfo.hRatio; 
	hint.width *= xinfo.wRatio;
	hint.height *= xinfo.hRatio;
	hint.flags = PPosition | PSize;
	return hint;
}

XSizeHints Displayable::makeHint(int x, int y,int width, int height, XInfo &xinfo){
	XSizeHints hint;
	hint.x = x*xinfo.wRatio;  
   	hint.y = (xinfo.height-y)*xinfo.hRatio; 
	hint.width = width*xinfo.wRatio;
	hint.height = height*xinfo.hRatio;
	hint.flags = PPosition | PSize;
	return hint;
}

bool Displayable::isHintCollide(XSizeHints a, XSizeHints b){
	if(a.x+ a.width < b.x || a.x > b.x + b.width ){ 
		return false;
	}else if(a.y + a.height < b.y || a.y > b.y + b.height){
		return false;
	}
	return true;
}
/////////////////////////////////////////////////////////////////////////
//Text
void Text::paint(XInfo &xinfo) {  
	XDrawImageString( xinfo.display, xinfo.window, xinfo.gc, 
		this->x, this->y, this->s.c_str(), this->s.length());
//XDrawText
}

void Text::setText(string t){
	s = t;
}

///////////////////////////////////////////////////////////////////      
//Plane
Plane::Plane(XInfo &xinfo){
	//Here should change to hit box
	planeHint.x = xinfo.width/2;
    planeHint.y = xinfo.height/2;;
    planeHint.width = 30;
    planeHint.height = 20;
    planeHint.flags = PPosition | PSize;

	bombHint.x = xinfo.width/2;
    bombHint.y = xinfo.height/2;
    bombHint.width = 10;
    bombHint.height = 10;
    bombHint.flags = PPosition | PSize;

	t = 0;
	god = false;
	alive = false;
}

void Plane::setGod(){
	god = !god;
}

bool Plane::isGod(){
	return god;
}


bool Plane::isAlive(){
	return alive;
}

void Plane::setAlive(bool live){
	alive = live;
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
		(*it).x -= 2;
		(*it).y -= 2;
	}
}

XSizeHints Plane::getPlaneHint(XInfo &xinfo){
	return makeHint(planeHint,xinfo);
}

void Plane::paint(XInfo &xinfo){
	drawPlane(xinfo);
	drawBomb(xinfo);
	t += 3;

}

//TODO: add XInfo &xinfo, here
void Plane::movePlane( KeySym key){
	switch(key){
     	case XK_Left: planeHint.x-=4;break;
     	case XK_Up: planeHint.y+=4;break;
      	case XK_Right: planeHint.x+=4;break;
      	case XK_Down: planeHint.y-=4;break;	
		case XK_space: addBomb(); break; //Drop bomb
		default:
			break;
      }
}

void Plane::reset(){
	alive = true;
	god = false;
	bombArray.clear();
}

bool Plane::isCollide(XInfo &xinfo, XSizeHints hint){
	list<XSizeHints>::iterator it = bombArray.begin();
	int size = bombArray.size();
	while(it!=bombArray.end()){
		if((*it).x < 0 || (*it).y < 0){
			bombArray.erase(it++);
			continue;
		}else {
			XSizeHints abombHint = makeHint((*it),xinfo);
			if(isHintCollide(abombHint,hint)){
				bombArray.erase(it++);
				continue;
			}
		}
		it++;
	}
	return (size>bombArray.size());
}

/////////////////////////////////////////////////////////////////////
//Scene
//Now make enemys

Scene::Scene(XInfo &xinfo){
	size = min(xinfo.width/(SCENE_WIDTH-1), xinfo.height/SCENE_HEIGHT);
	//Tile
	for(int i = 0;i<SCENE_WIDTH ; i +=1){
		tileList.push_back(new Tile(size,i*size));
	}
	//Plane
	plane = new Plane(xinfo);
	//Lose Text	
	loseText = new Text(xinfo.width/2, xinfo.height/2-20, "You Lose");
	//Win Text
	winText = new Text(xinfo.width/2, xinfo.height/2-20, "You Win");
	//Score
	scoreText =  new Text(xinfo.width-80, 40, "Score:");
	tilePassed = 0;
	score = 0;
}

void Scene::paint(XInfo &xinfo){
	//Check collision
	updateCollide(xinfo);

	//Start paint
	list<Tile*>::iterator tileIt = tileList.begin();	
	for(;tileIt!=tileList.end() ; tileIt++){
		(*tileIt)->paint(xinfo);
	}
	
	//if plane is alive
	if(plane->isAlive()){
		plane->paint(xinfo);
	}else if(tilePassed != 0&&!plane->isAlive()){
		loseText->paint(xinfo);
	}
	if(tilePassed < TILE_MAX){
		//Add a new one
		tileIt = tileList.begin();
		if((*tileIt)->isTileOutside()){
			tileList.erase(tileIt);
			tileList.push_back(new Tile(size,SCENE_WIDTH*size));
			tilePassed += 1;
		}
	}else if(plane->isAlive()){
		winText->paint(xinfo);
	}	
	
	scoreText->paint(xinfo);
	
}

void Scene::getInut(KeySym key,char ch){
	if (ch == 'g' || ch == 'G'){
		plane->setGod();
	}else if (ch == 's' ||ch == 'S'){
		reset();
		plane->setAlive(true);
		plane->reset();
	}
	plane->movePlane(key);
}

void Scene::reset(){
	plane->reset();
	tilePassed = 0;
	tileList.clear();
	for(int i = 0;i<SCENE_WIDTH ; i +=1){
		tileList.push_back(new Tile(size,i*size));
	}
	score = 0;
}


void Scene::updateCollide(XInfo &xinfo){
	//First check if plane hits anything
	list<Tile*>::iterator tileIt = tileList.begin();
	
	for(;tileIt!=tileList.end() ; tileIt++){
		if(!plane->isGod()&&(*tileIt)->isCollide(xinfo,plane->getPlaneHint(xinfo))){
			plane->setAlive(false);
			return ;
		}
	}	
	
	//if the bomb of plane hit anything
	tileIt = tileList.begin();	
	for(;tileIt!=tileList.end() ; tileIt++){
		if((*tileIt)->isEnemy()){
			if(plane->isCollide(xinfo, (*tileIt)->getEnemeyHint(xinfo))){
				(*tileIt)->setEnemy(false);
				score += 10;
				char buffer[50];
				int n = sprintf(buffer,"score: %d",score);
				scoreText->setText(buffer);
			}
		}else{
			bool x = plane->isCollide(xinfo, (*tileIt)->getTileHint(xinfo));
			//Do nothing
		}
	}

}




////////////////////////////////////////////////////////////////
//Tile

Tile::Tile(int size, int x){
	tileHint.width = size;	
    tileHint.height = (rand() %SCENE_HEIGHT)*size;
	tileHint.x = x;
    tileHint.y = tileHint.height;
    tileHint.flags = PPosition | PSize;

	if(rand()%4 == 0){
		hasEnemy = true;
	}else{
		hasEnemy = false;
	}

    enemyHint.width = tileHint.width*0.8;
    enemyHint.height = size*0.3;
	enemyHint.x = tileHint.x;
    enemyHint.y = tileHint.y+enemyHint.height ;
    enemyHint.flags = PPosition | PSize;

	bulletHint.width = 5;
    bulletHint.height = 10;
	bulletHint.x = x;
    bulletHint.y = enemyHint.y-enemyHint.height;
    bulletHint.flags = PPosition | PSize;

	x_t = 0;
	y_t = 0;
	
}

void Tile::setEnemy(bool e){
	hasEnemy = e;
}

bool Tile::isEnemy(){
	return hasEnemy;
}
XSizeHints Tile::getEnemeyHint(XInfo &xinfo){
	return makeHint(enemyHint.x-x_t, enemyHint.y, enemyHint.width, enemyHint.height,xinfo);
}

XSizeHints Tile::getTileHint(XInfo &xinfo){
	return makeHint(tileHint.x-x_t, tileHint.y, tileHint.width, tileHint.height,xinfo);
}

void Tile::paint(XInfo &xinfo){
	//Tile
	XDrawRectangle(xinfo.display, xinfo.window, xinfo.gc, 
			(tileHint.x-x_t)*xinfo.wRatio, (xinfo.height- tileHint.y)*xinfo.hRatio,
			tileHint.width*xinfo.wRatio,  tileHint.height*xinfo.hRatio);
	
	if(hasEnemy){
		//Enemy
		XFillRectangle(xinfo.display, xinfo.window, xinfo.gc, 
				(enemyHint.x-x_t)*xinfo.wRatio, (xinfo.height- enemyHint.y)*xinfo.hRatio,
				enemyHint.width*xinfo.wRatio,  enemyHint.height*xinfo.hRatio);
	
		//bullet
		XFillArc(xinfo.display, xinfo.window, xinfo.gc,
			(bulletHint.x-x_t)*xinfo.wRatio,  (xinfo.height - (bulletHint.y+y_t))*xinfo.hRatio, 
			bulletHint.width*xinfo.wRatio, bulletHint.height*xinfo.hRatio, 0, 360*64);
	}
	//Scroll
	x_t += 2;
	y_t += 2;

	//Bullet out of screen
	if(bulletHint.y+y_t> xinfo.height){
		y_t = 0;
	}
}

bool Tile::isCollide(XInfo &xinfo, XSizeHints hint){
	//tile
	if(isHintCollide(makeHint(tileHint.x-x_t, tileHint.y, 
					tileHint.width, tileHint.height,xinfo),hint)){
		return true;
	}	

	if(hasEnemy){
		if(isHintCollide(makeHint(enemyHint.x-x_t, enemyHint.y, 
							enemyHint.width, enemyHint.height,xinfo),hint)){
			return true;
		}
		if(isHintCollide(makeHint(bulletHint.x-x_t, bulletHint.y+y_t, 
					bulletHint.width, bulletHint.height,xinfo),hint)){
			return true;
		}
	}
	return false;
}

bool Tile::isTileOutside(){
	return (tileHint.x+tileHint.width-x_t <= 0);
}

