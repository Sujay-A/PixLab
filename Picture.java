
import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.util.List; // resolves problem with java.awt.List and java.util.List

/**
 * A class that represents a picture.  This class inherits from 
 * SimplePicture and allows the student to add functionality to
 * the Picture class.  
 * 
 * @author Barbara Ericson ericson@cc.gatech.edu originally 
 * but all the methods are by Sujay Adhinarayanan (Period 5)
 * since 2/14/2025
 */
public class Picture extends SimplePicture 
{
  ///////////////////// constructors //////////////////////////////////
  
  /**
   * Constructor that takes no arguments 
   */
  public Picture ()
  {
    /* not needed but use it to show students the implicit call to super()
     * child constructors always call a parent constructor 
     */
    super();  
  }
  
  /**
   * Constructor that takes a file name and creates the picture 
   * @param fileName the name of the file to create the picture from
   */
  public Picture(String fileName)
  {
    // let the parent class handle this fileName
    super(fileName);
  }
  
  /**
   * Constructor that takes the width and height
   * @param height the height of the desired picture
   * @param width the width of the desired picture
   */
  public Picture(int height, int width)
  {
    // let the parent class handle this width and height
    super(width,height);
  }
  
  /**
   * Constructor that takes a picture and creates a 
   * copy of that picture
   * @param copyPicture the picture to copy
   */
  public Picture(Picture copyPicture)
  {
    // let the parent class do the copy
    super(copyPicture);
  }
  
  /**
   * Constructor that takes a buffered image
   * @param image the buffered image to use
   */
  public Picture(BufferedImage image)
  {
    super(image);
  }
  
  ////////////////////// methods ///////////////////////////////////////
  
  /**
   * Method to return a string with information about this picture.
   * @return a string with information about the picture such as fileName,
   * height and width.
   */
  public String toString()
  {
    String output = "Picture, filename " + getFileName() + 
      " height " + getHeight() 
      + " width " + getWidth();
    return output;
    
  }
  
  /** Method to set the blue to 0 */
  public void zeroBlue()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setBlue(0);
      }
    }
  }
  
  /** This method sets the each pixel's rbg values to 255 minus its current 
   * value. It does this using pixel methods and a series of for loops. It 
   * has no parameters or returns.
   */
  public void negate() {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
		pixelObj.setRed(255-pixelObj.getRed());
		pixelObj.setGreen(255-pixelObj.getGreen());
        pixelObj.setBlue(255-pixelObj.getBlue());
        
      }
    }
  }
  
  /** This method averages the rgb values for a pixel, and sets it to that 
   * amount, it uses pixel methods and series of for loops. It has no parameters 
   * or returns.
   */
  public void grayscale() {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
		int average = (pixelObj.getGreen() + pixelObj.getRed() + pixelObj.getBlue())/3;
		pixelObj.setGreen(average);
		pixelObj.setRed(average);
		pixelObj.setBlue(average);
      }
    }
  }
  
  /** Method that mirrors the picture around a 
    * vertical mirror in the center of the picture
    * from left to right */
  public void mirrorVertical()
  {
    Pixel[][] pixels = this.getPixels2D();
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int width = pixels[0].length;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; col < width / 2; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][width - 1 - col];
        rightPixel.setColor(leftPixel.getColor());
      }
    } 
  }
  
  /** Mirror just part of a picture of a temple */
  public void mirrorTemple()
  {
    int mirrorPoint = 276;
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int count = 0;
    Pixel[][] pixels = this.getPixels2D();
    
    // loop through the rows
    for (int row = 27; row < 97; row++)
    {
      // loop from 13 to just before the mirror point
      for (int col = 13; col < mirrorPoint; col++)
      {
        
        leftPixel = pixels[row][col];      
        rightPixel = pixels[row]                       
                         [mirrorPoint - col + mirrorPoint];
        rightPixel.setColor(leftPixel.getColor());
      }
    }
  }
  
  /** copy from the passed fromPic to the
    * specified startRow and startCol in the
    * current picture
    * @param fromPic the picture to copy from
    * @param startRow the start row to copy to
    * @param startCol the start col to copy to
    */
  public void copy(Picture fromPic, 
                 int startRow, int startCol)
  {
    Pixel fromPixel = null;
    Pixel toPixel = null;
    Pixel[][] toPixels = this.getPixels2D();
    Pixel[][] fromPixels = fromPic.getPixels2D();
    for (int fromRow = 0, toRow = startRow; 
         fromRow < fromPixels.length &&
         toRow < toPixels.length; 
         fromRow++, toRow++)
    {
      for (int fromCol = 0, toCol = startCol; 
           fromCol < fromPixels[0].length &&
           toCol < toPixels[0].length;  
           fromCol++, toCol++)
      {
        fromPixel = fromPixels[fromRow][fromCol];
        toPixel = toPixels[toRow][toCol];
        toPixel.setColor(fromPixel.getColor());
      }
    }   
  }

  /** Method to create a collage of several pictures */
  public void createCollage()
  {
    Picture flower1 = new Picture("flower1.jpg");
    Picture flower2 = new Picture("flower2.jpg");
    this.copy(flower1,0,0);
    this.copy(flower2,100,0);
    this.copy(flower1,200,0);
    Picture flowerNoBlue = new Picture(flower2);
    flowerNoBlue.zeroBlue();
    this.copy(flowerNoBlue,300,0);
    this.copy(flower1,400,0);
    this.copy(flower2,500,0);
    this.mirrorVertical();
    this.write("collage.jpg");
  }
  
  
  /** Method to show large changes in color 
    * @param edgeDist the distance for finding edges
    */
  public void edgeDetection(int edgeDist)
  {
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    Pixel[][] pixels = this.getPixels2D();
    Color rightColor = null;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; 
           col < pixels[0].length-1; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][col+1];
        rightColor = rightPixel.getColor();
        if (leftPixel.colorDistance(rightColor) > 
            edgeDist)
          leftPixel.setColor(Color.BLACK);
        else
          leftPixel.setColor(Color.WHITE);
      }
    }
  }
  
  /** This sets all other rgb values to zero.
   * It has no parameters or returns.
   */
	public void keepOnlyBlue() {
		Pixel[][] pixels = this.getPixels2D();
		for (Pixel[] rowArray : pixels)
		{
			for (Pixel pixelObj : rowArray)
			{
				pixelObj.setRed(0);
				pixelObj.setGreen(0);
			}
		}
	}
	
	/** Using a series of embedded for-loops, this method creates 
	 * blocks of the 2D array to average each rgb value, and sets 
	 * that entire block of pixels to each of the average rgb values. 
	 * It also uses checks to prevent it from going out of bounds.
	 * @param int size - the dimension of the square of pixels to be 
	 * 			average in the 2D array
	 * @return none
	 */
	public void pixelate(int size) {
		Pixel[][] pixels = this.getPixels2D();
		for(int row = 0; row < pixels.length; row+=size) {
			for(int col = 0; col < pixels[0].length; col+=size) {
				int sumGreen = 0;
				int sumRed = 0;
				int sumBlue = 0;
				int count = 0;
				for(int r = row; r < row+size && r < pixels.length; r++) {
					for(int c = col; c < col+size && c < pixels.length; c++) {
						sumGreen += pixels[r][c].getGreen();
						sumRed += pixels[r][c].getRed();
						sumBlue += pixels[r][c].getBlue();
						count++;
					}
				}
				int avgRed = 0;
				int avgGreen= 0;
				int avgBlue = 0;
				if(count > 0) {
					avgRed = sumRed/count;
					avgGreen = sumGreen/count;
					avgBlue = sumBlue/count;
				}
				for(int r = row; r < row+size && r < pixels.length; r++) {
					for(int c = col; c < col+size && c < pixels.length; c++) {
						pixels[r][c].setGreen(avgGreen);
						pixels[r][c].setRed(avgRed);
						pixels[r][c].setBlue(avgBlue);
					}
				}
			}
		}
	}
	
	/** This method uses a series of embedded for loops in order to take squares 
	 * of the given size (remainder are their own squares as well) in order
	 * average each of the rgb values out, and put them in that central pixel.
	 * At the same, it uses if-statements often to ensure that variables 
	 * are within bounds. 
	 * 
	 * @param int size, the dimensions of the square to take around a central pixel
	 * @return a Picture which is the result of the modification to the original picture.
	 * 
	 */
	public Picture blur(int size) {
		Pixel[][] pixels = this.getPixels2D();
		Picture result = new Picture(pixels.length, pixels[0].length);
		Pixel[][] resultPixels = result.getPixels2D();
		for(int row = 0; row < pixels.length; row++) {
			for(int col = 0; col < pixels[0].length; col++) {
				int sumGreen = 0;
				int sumRed = 0;
				int sumBlue = 0;
				int count = 0;
				for(int r = row-size; r <= row+size; r++) {
					for(int c = col-size; c < col+size; c++) {
						if(r >= 0 && c >= 0 
							&& r < pixels.length && c < pixels[0].length) {
							sumGreen += pixels[r][c].getGreen();
							sumRed += pixels[r][c].getRed();
							sumBlue += pixels[r][c].getBlue();
							count++;
						}
					}
				}
				if(count > 0)
					resultPixels[row][col].setColor(new Color(sumRed/count,
						sumGreen/count, sumBlue/count));	
			}
		}	
		return result;
	}
	
	
	/**This method uses a series of embedded for loops in order to take squares 
	 * of the given size (remainder are their own squares as well) in order
	 * average each of the rgb values out, and then it puts them in an enhancing 
	 * formula for each respective rgb value. At the same, it uses if-statements 
	 * often to ensure that variables are within bounds. 
	 * 
	 * @param int size, the dimensions of the square to take around a central pixel
	 * @return a Picture which is the result of the modification to the original picture.
	 */
	public Picture enhance(int size) {		
		 Pixel[][] pixels = this.getPixels2D();
		 Picture result = new Picture(pixels.length, pixels[0].length);
		 Pixel[][] resultPixels = result.getPixels2D();
		 for(int row = 0; row < pixels.length; row++) {
			for(int col = 0; col < pixels[0].length; col++) {
				int sumGreen = 0;
				int sumRed = 0;
				int sumBlue = 0;
				int count = 0;
				for(int r = row-size; r <= row+size; r++) {
					for(int c = col-size; c < col+size; c++) {
						if(r >= 0 && c >= 0 
							&& r < pixels.length && c < pixels[0].length) {
							sumGreen += pixels[r][c].getGreen();
							sumRed += pixels[r][c].getRed();
							sumBlue += pixels[r][c].getBlue();
							count++;
						}
					}
				}
				if(count > 0) {
					int avgRed = sumRed/count;
					int avgGreen = sumGreen/count;
					int avgBlue = sumBlue/count;	
					int newRed = Math.min(255, Math.max(0, 2*pixels[row][col].getRed() - avgRed));
					int newGreen = Math.min(255, Math.max(0, 2*pixels[row][col].getGreen() - avgGreen));
					int newBlue = Math.min(255, Math.max(0, 2*pixels[row][col].getBlue() - avgBlue));
					resultPixels[row][col].setColor(new Color(newRed, newGreen, newBlue));
				}
			}
		}	
		return result;
	}
  
  /** This method uses a series of embedded for loops to swap each pixel
   * (rgb) values into a new position using the given wrapping new Column 
   * formula.
   * 
   * @param
   *  @return a Picture which is the result of the modification to the original picture.
   */
	public Picture swapLeftRight() {
	  Pixel[][] pixels = this.getPixels2D();
	  int width = pixels[0].length;
	  int height = pixels.length;
	  Picture result = new Picture(height, width);
	  Pixel[][] resultingPixels = result.getPixels2D();
	  for(int row = 0; row < height; row++) {
		  for(int col = 0; col < width; col++) {
			  int newColumn	=(col+(width/2))%width;
			  if(newColumn >= 0 && newColumn <= width)
				resultingPixels[row][newColumn].setColor(pixels[row][col].getColor());
		  }
	  }
	  return result;
	}
  
	/** This method uses a group of embedded for loops to order to affectively 
	 * split the image intl horizontal components and and shift it to resemble 
	 * the tilted nature of stairs.
	 * the image into that resembling a tilt of a stair.
	 * 
	 * @param int shiftCount number of of pixels to shift right
	 * @param int steps number of steps that image is split into
	 * @return a Picture which is the result of the modification to the original picture.
	 * 
	*/
	public Picture stairStep(int shiftCount, int steps) {
	  Pixel[][] pixels = this.getPixels2D();
	  int width = pixels[0].length;
	  int height = pixels.length;
	  Picture result = new Picture(height, width);
	  Pixel[][] resultingPixels = result.getPixels2D();  
	  for(int row = 0; row < height; row++) {
		  int shift = (row/(height/steps))*shiftCount;
		  for(int col = 0; col < width; col++) {
			  int newCol = (col+shift)%width;
			  resultingPixels[row][newCol].setColor(pixels[row][col].getColor());
		  }
	  }
	  return result;
	}
	
	/** This method provides a liquifying affect on an image by using 
	 * parts of a bell curve.
	 * 
	 * @param int maxHeight  "A" value in formula of Gaussian curve (max height of curve)
	 * @return a Picture which is the result of the modification to the original picture.
	 */
	public Picture liquify(int maxHeight) {
	  Pixel[][] pixels = this.getPixels2D();
	  int width = pixels[0].length;
	  int height = pixels.length;
	  Picture result = new Picture(height, width);
	  Pixel[][] resultingPixels = result.getPixels2D();  
	  int bellWidth = width/5;
	  for(int row = 0; row < height; row++){
		  double exponent = Math.pow(row-height/2.0, 2)/(2.0*Math.pow(bellWidth, 2));
		  int shift = (int)(maxHeight * Math.exp(-exponent));
		  
		  for(int col = 0; col < width; col++) {
			  int newCol = (col+shift)%width;
			  resultingPixels[row][newCol].setColor(pixels[row][col].getColor());
		  }
	  }
	  
	  return result;
	}
	
	/** This method makes images look like a wave using a series of for loops
	 * which use a mathematical sine curve to achieve this.
	 * 
	 * @param int amplitude   amplitude of sine wave (wave nature of image)
	 * @return a Picture which is the result of the modification to the original picture.
	 */
	public Picture wavy(int amplitude) {
	  Pixel[][] pixels = this.getPixels2D();
	  int width = pixels[0].length;
	  int height = pixels.length;
	  Picture result = new Picture(height, width);
	  Pixel[][] resultingPixels = result.getPixels2D();
	  
	  double frequency = 2.0 * Math.PI/height;
	  
	  for(int row = 0; row < height; row++) {
		  int shift = (int)(amplitude * Math.sin(frequency*row));
		  
		  for(int col = 0; col< width; col++) {
			  int newCol = (col + shift + width)%width;
			  resultingPixels[row][newCol].setColor(pixels[row][col].getColor());
		  }	  
	  }
	  
	  return result;
	}
	
  /* Main method for testing - each class in Java can have a main 
   * method 
   */
  public static void main(String[] args) 
  {
    Picture beach = new Picture("images/beach.jpg");
    beach.explore();
    beach.zeroBlue();
    beach.explore();
  }
  
} // this } is the end of class Picture, put all new methods before this
