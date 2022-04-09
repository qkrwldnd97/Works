#include <stdio.h>
#include <stdlib.h>

#include "rpn.h"

#define MAXCOLS 80
#define MAXROWS 40

char plot[MAXROWS][MAXCOLS];

void clearPlot()
{
  for (int i = 0; i < MAXROWS; i++) {
    for (int j = 0; j < MAXCOLS; j++) {
      plot[i][j] = ' ';
}
}
}

void printPlot()
{
  for (int i = 0; i < MAXROWS; i++) {
    for (int j = 0; j < MAXCOLS; j++) {
      printf("%c", plot[i][j]);
}
    printf("\n");
}
}

void plotXY(int x, int y, char c) {
  if ( x <-MAXCOLS || x>=MAXCOLS || y < -MAXROWS || y >=MAXROWS) 
	return NULL;
  plot[y][x]=c;
}

void createPlot( char * funcFile, double minX, double maxX) {
  int nvals = MAXCOLS;
  double yy[MAXCOLS];

  clearPlot();
	double interX = (maxX - minX) / MAXCOLS;	  	
	for (int i = 0; i < MAXCOLS; i++){
		yy[i] = rpn_eval(funcFile, minX + (interX * i));
}
  	double maxY = yy[0];
  	double minY = yy[0];
	for (int i = 1; i < MAXCOLS; i++){
		if (yy[i] < minY){
			minY = yy[i];
}
		if (yy[i] > maxY){
			maxY = yy[i];
}}
	double interY = (maxY - minY) / MAXROWS;
	int z = 0;

	while (z < MAXCOLS){
		if (yy[z] == 0){
			break;
}
		z++;
}
	
	int linex = MAXROWS - 1 - (yy[z] - minY)/interY;

	for (int row = 0; row < MAXCOLS; row++){
		plotXY(row, linex, '_');
}
	for (int col = 0; col < MAXROWS; col++){
		plotXY(MAXCOLS/2, col, '|');
}
  for (int col = 0; col < MAXCOLS; col++){
		int x = (yy[col] - minY)/interY;
		plotXY(col, MAXROWS - x - 1, '*');
}

  printPlot();

}

int main(int argc, char ** argv)
{
  printf("RPN Plotter.\n");
  
  if (argc < 4) {
    printf("Usage: plot func-file xmin xmax\n");

	exit(1);
  }
	char *fileName = argv[1];
	double xmin = atoi(argv[2]);
	double xmax = atoi(argv[3]);

	createPlot (fileName, xmin, xmax);

}
