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

#include <X11/Xlib.h>
#include <X11/Xutil.h>

#include "Displayable.h"
using namespace std;


const int Border = 5;
const int BufferSize = 10;


void error( string str ) {
  cerr << str << endl;
  exit(0);
}


void repaint( list<Displayable *> dList, XInfo &xinfo) {
    list<Displayable *>::const_iterator begin = dList.begin();
   	list<Displayable *>::const_iterator end = dList.end();

    XClearWindow( xinfo.display, xinfo.window );
    while( begin != end ) {  
		Displayable *d = *begin;
    	d->paint(xinfo);
        begin++;
    }
    XFlush( xinfo.display );
}

void eventloop(XInfo &xinfo)
{   XEvent event;
    KeySym key;
    char text[BufferSize];
    list<Displayable *> dList;           // list of Displayables
	dList.push_front(new Plane(xinfo));
	//Prototype splash screen
	Text * name = new Text(xinfo.width/2, xinfo.height/2, "Yue Huang");
    while( true ) {   
		XNextEvent( xinfo.display, &event );
		XConfigureEvent xce;
        switch( event.type ) {
		//Window resize
		case ConfigureNotify:
			xce = event.xconfigure;
			if(xce.width != xinfo.width || xce.height != xinfo.height){
				//cout<<"resize"<<endl;
				xinfo.setRatio(xce.height,xce.height);
				repaint( dList, xinfo);
			}
			break;

     	// Repaint the window on expose events./
        case Expose:
		    if ( event.xexpose.count == 0 ){   
				repaint( dList, xinfo);
		    }
		    break;
      
		// Add an item where the mouse is clicked, and repaint.       
        /*case ButtonPress:
		    dList.push_front(new Text(event.xbutton.x, event.xbutton.y, "Urrp!"));
		    repaint( dList, xinfo );
		    break;*/
		
        case KeyPress:
		    int i = XLookupString( (XKeyEvent *)&event, text, BufferSize, &key, 0 );
		    if ( i == 1 && text[0] == 'q' ){   
				error( "Terminated normally." );
		        XCloseDisplay(xinfo.display);
		    }else if ( i == 1 && (text[0] == 'f' || text[0] == 'F')){
				//Draw help screen
				name->paint(xinfo);
			}
		    break;
		

        }
		
		
    }
}

/*
 * Create the window;  initialize X.
 */
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

    hints.x = 1;
    hints.y = 1;
    hints.width = 400;
    hints.height = 300;
    hints.flags = PPosition | PSize;
    xinfo.window = XCreateSimpleWindow( xinfo.display, DefaultRootWindow( xinfo.display ),
                                hints.x, hints.y, hints.width, hints.height,
                                Border, foreground, background );
    XSetStandardProperties( xinfo.display, xinfo.window, "Hello1", "Hello2", None,
                                argv, argc, &hints );

   /*
    * Get a graphics context and set the drawing colours.
    * Arguments to XCreateGC
    *           display - which uses this GC
    *           window - which uses this GC
    *           GCflags - flags which determine which parts of the GC are used
    *           GCdata - a struct that fills in GC data at initialization.
    */
    xinfo.gc = XCreateGC (xinfo.display, xinfo.window, 0, 0 );
    XSetBackground( xinfo.display, xinfo.gc, background );
    XSetForeground( xinfo.display, xinfo.gc, foreground );

   /*
    * Tell the window manager what input you want.
    */
    XSelectInput( xinfo.display, xinfo.window,
                ButtonPressMask | KeyPressMask | ExposureMask | StructureNotifyMask);

   /*
    * Put the window on the screen.
    */
    XMapRaised( xinfo.display, xinfo.window );
	//XMapWindow (xinfo.display, xinfo.window );
}



/*
 * Start executing here.
 *   First initialize window.
 *   Next loop responding to events.
 *   Exit forcing window manager to clean up - cheesy, but easy.
 */
int main ( int argc, char *argv[] ) {   XInfo xinfo;

   initX(argc, argv, xinfo);
   eventloop(xinfo);
}
