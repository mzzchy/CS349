CS349 Assignment 3

Enhancement: Add scale and rotation transformation with playback functionality

Draw Mode - click or drag to draw

Erase Mode - drag to erase object 

Select Mode - Use lasso to create a rectangle selected area. 
	To draw a lasso, simply hold down mouse button and draw around the selected area, if you release the mouse button, the lasso will
	automatically closed the route to the starting point. 
	I choose to do this because I find it pretty hard for the mouse to move on to the exact starting point. The actual calculation is to the exact region of lasso.
	After an area is selected, if the user move the mouse in the area, the cursor will change to indicate the area can be animate. 
	Otherwise, if clicking on outside the area, either cancel the current lasso area or recreat a new one.
	To select an new area, just redraw outside the original selected area or click outside the selected area.
	The lasso is indicated by yellow.
	
Creat animation -
	When move mouse in the lasso area, the cursor will change to indicate the area can be animated.
	Press and Drag Left mouse button - Drag object from one point to another point
	Press and Drag Middle mouse button - Rotate object based on lasso center, drag right to rotate clockwise, drag left to rotate counter clockwise.
	Press and Drag Right mouse button - Scale object based on lasso center, drag right/down to increase scale, drag left/up to decrease scale. There is also a scale limit.
	Please only press one button at a time.
	Lasso will also reacte to the change to indicate we have perform valid animation option.
	I choose to use Lasso center since it is easier for calculation and also if is hard to figure out the center of a multipe objects.
	The center is calculated based on the outer bound of the lasso.	
	
	Weird effect maybe occur in rotation, I did set the center to be the lasso area center, but it doesn't work in the way I expected
	
Insert static animation -
	Click on the thumb on slider, press ctrl and drag to right. Note: Only work if there is existing animation already created	
	
Play button - play the animation from current time frame

Pause button - pause animation at current time frame