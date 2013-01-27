/*
 * Commands to compile and run
 *
 * g++ -o game main.cpp -L/usr/X11R6/lib -lX11 -lstdc++
 * ./game
 * 
 */

#include <iostream>
#include <list>
#include <cstdlib>
#include <sys/time.h>
#include <X11/Xlib.h>
#include <X11/Xutil.h>
#include <math.h>
#include <stdio.h>

#include "Displayable.h"
using namespace std;


const int Border = 5;
const int BufferSize = 10;
const int FPS = 30;

void error( string str ) {
  cerr << str << endl;
  exit(0);
}

unsigned long now() {
	timeval tv;
	gettimeofday(&tv, NULL);
	return tv.tv_sec * 1000000 + tv.tv_usec;
}

void repaint( list<Displayable *> dList, XInfo &xinfo) {
    list<Displayable *>::const_iterator begin = dList.begin();
   	list<Displayable *>::const_iterator end = dList.end();

    //XClearWindow( xinfo.display, xinfo.window ); move to event loop
    while( begin != end ) {  
		Displayable *d = *begin;
    	d->paint(xinfo);
        begin++;
    }
    //XFlush( xinfo.display ); same here
}

void eventloop(XInfo &xinfo){   
	XEvent event;
    KeySym key;
	char text[BufferSize];
	list<Displayable *> message;  
        
	bool isMessage = true;
	message.push_back(new Text(xinfo.width/2, xinfo.height/2, "Yue Huang(619)"));
	message.push_back(new Text(xinfo.width/2, xinfo.height/2+20, "space:bomb"));
	message.push_back(new Text(xinfo.width/2, xinfo.height/2+40, "g:god mode"));
	message.push_back(new Text(xinfo.width/2, xinfo.height/2+60, "s:start/restart "));

	Scene * scene = new Scene(xinfo);

	unsigned long lastRepaint = 0;
    while( true ) {  
		if(XPending(xinfo.display) > 0){

			XNextEvent( xinfo.display, &event );
			XConfigureEvent xce;
	
			switch( event.type ) {
			//Window resize
				case ConfigureNotify:
					xce = event.xconfigure;
					if(xce.width != xinfo.width || xce.height != xinfo.height){
						xinfo.setRatio(xce.height,xce.width);
					}
					break;
				case KeyPress:
					if (XLookupString( (XKeyEvent *)&event, text, BufferSize, &key, 0 )==1) { 
						if(text[0] == 'q' ){
							error( "Terminated normally." );
							XCloseDisplay(xinfo.display);
						}else if(text[0] == 'f' ||text[0] == 'F'){
							isMessage = !isMessage;
						}
					}
					scene->getInut(key,text[0]);
					break;

				default:
					break;
			}
		}

		unsigned long end = now();
		if (end - lastRepaint > 1000000/FPS) {
			XClearWindow( xinfo.display, xinfo.window );
			if(isMessage){
				repaint(message, xinfo);
			}
			scene->paint(xinfo);
			lastRepaint = now();
			XFlush( xinfo.display );
		}
		if (XPending(xinfo.display) == 0) {
			usleep(1000000/FPS - (end - lastRepaint));
		}
		
	}
}

void initX(int argc, char *argv[], XInfo &xinfo){   
	
	XSizeHints hints;
    unsigned long background, foreground;
    int screen;

    xinfo.display = XOpenDisplay( "" );
    if ( !xinfo.display ){ 
		error( "Can't open display." );
    }
	//Setup
    screen = DefaultScreen( xinfo.display );
    background = WhitePixel( xinfo.display, screen );
    foreground = BlackPixel( xinfo.display, screen );
	
	//height and width
	xinfo.width = 400;
	xinfo.height = 300;
	xinfo.wRatio = 1.0;
	xinfo.hRatio = 1.0;

    hints.x = 0;
    hints.y = 0;
    hints.width = 400;
    hints.height = 300;
    hints.flags = PPosition | PSize;
    xinfo.window = XCreateSimpleWindow( xinfo.display, DefaultRootWindow( xinfo.display ),
                                hints.x, hints.y, hints.width, hints.height,
                                Border, foreground, background );
    XSetStandardProperties( xinfo.display, xinfo.window, "Hello1", "Hello2", None,
                                argv, argc, &hints );

    xinfo.gc = XCreateGC (xinfo.display, xinfo.window, 0, 0 );

	//Double buffer
	/*int depth = DefaultDepth(xinfo.display, DefaultScreen(xinfo.display));
	xinfo.pixmap = XCreatePixmap(xinfo.display, xinfo.window, hints.width, hints.height, depth);*/

    XSetBackground( xinfo.display, xinfo.gc, background );
    XSetForeground( xinfo.display, xinfo.gc, foreground );
	
    XSelectInput( xinfo.display, xinfo.window,
                ButtonPressMask | KeyPressMask | ExposureMask | StructureNotifyMask);
    XMapRaised( xinfo.display, xinfo.window );
	
}



int main ( int argc, char *argv[] ) {   XInfo xinfo;

   initX(argc, argv, xinfo);
   eventloop(xinfo);
}

//XFreePixmap(xinfo.display, xinfo.pixmap);
//int depth = DefaultDepth(xinfo.display, DefaultScreen(xinfo.display));
//xinfo.pixmap = XCreatePixmap(xinfo.display, xinfo.window, width, height, depth);
//Create the window;  initialize X.
//Flip buffer
//XCopyArea(xinfo.display, xinfo.pixmap, xinfo.window, xinfo.gc, 
//0, 0, xinfo.width, xinfo.height, 10, 10);
//XFillRectangle(xinfo.display, xinfo.window, xinfo.gc[0], width-80, height-80, 80, 80);
