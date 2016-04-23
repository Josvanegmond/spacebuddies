//this goes in the render method
checkMoonFound(angleX, angleZ);

//and this is another private method in the FindMoonScreen class
private void checkMoonFound(float angleX, float angleZ) {
  float angleX2;
  
  if(angleZ < 90) {
    angleX2 = -angleX;
  } else {
    angleX2 = angleX;
  }
  
  if(azimuth - moonvector.y < 10 && angleX2 - moonvector.x < 10) {
    //start a thread with a delay of 1 second
      //if the value is still true, call navigation function
      nextFrame();
  }
}

private boolean hasNavigated = false;
private void nextFrame() {
  
  if(!hasNavigated) {
    hasNavigated = true;
    //function to go to next frame
  }
}