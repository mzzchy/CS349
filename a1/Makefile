CXX= g++
CXXFLAGS = -Wall -O -g
XLIB = -L/usr/X11R6/lib -lX11 -lstdc++
OBJS = main.o Displayable.o
EXEC = game

$(EXEC): $(OBJS)
	$(CXX) $(CXXFLAGS) $(OBJS) $(XLIB) -o $(EXEC)

clean:
	rm *.o *.d

Displayable.o: Displayable.h Displayable.cpp
main.o : main.cpp
