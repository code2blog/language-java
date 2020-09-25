package com.wordpress.code2blog;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class App 
{
    Point point = new Point();

    public static void main( String[] args )  throws Exception 
    {
      new App().moveMouseToKeepScreenActive();
    }

    public void moveMouseToKeepScreenActive() throws Exception {
      Robot r = new Robot();
      while (true)
      {
        PointerInfo a = MouseInfo.getPointerInfo();
        Point b = a.getLocation();
        int xOrig = (int)b.getX();
        int yOrig = (int)b.getY();
  
        if (!(positionChanged(xOrig, yOrig))) {
          r.mouseMove(720, 360);
          Thread.sleep(500L);
          r.mouseMove(xOrig, yOrig);
          System.out.println("mouse moved : " + new Date());
        }
        Thread.sleep(TimeUnit.SECONDS.toMillis(60L));
      }
    }
    
    private boolean positionChanged(int xOrig, int yOrig)
    {
      boolean changed = false;
      if (this.point.x != xOrig)
        changed = true;
      if (this.point.y != yOrig)
        changed = true;
      this.point.setLocation(xOrig, yOrig);
      return changed;
    }
  
  }
